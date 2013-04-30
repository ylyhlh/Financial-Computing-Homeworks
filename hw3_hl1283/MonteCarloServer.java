package hw3_hl1283;

import hw2_hl1283.util.Option;
import hw2_hl1283.util.StatCollector;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.commons.math3.distribution.NormalDistribution;

/**
 * The Server class for Monte Carlo simulation. <code>Option</code> and accuracy requests needed.
 * Each server only make one simulation.
 * @author liuhao
 *
 */
public class MonteCarloServer implements Runnable {
	/** The number of requests server makes each step */
	private int batchSize = 100;
	/** The Option to simulate */
	private Option option;
	/** The connectionFactory belongs to server */
	private ConnectionFactory connectionFactory;
	/** The session belongs to server */
	private Session session;
	/** The connection belongs to server */
	private Connection connection;
	/** The producer belongs to server */
	private MessageProducer producer;
	/** The consumer belongs to server */
	private MessageConsumer consumer;
	/** The request message belongs to server */
	private MapMessage message;
	/** <code>StatCollector</code> to collect all payouts and compute the mean and standard deviation. */
	private StatCollector stat = new StatCollector();
	/** Possibility tolerance of the simulation. */
	private double accuPoss = 0.96;
	/** Absolute error tolerance of the simulation. */
	private double accuRate = 0.01;
	/** Actually used tolerance bound */
	private double bound = - new NormalDistribution().inverseCumulativeProbability( (1 - accuPoss) / 2 ) ;

	/**
	 * Construct the server with given Option and default accuracy tolerances
	 * @param option <code>Option</code> to simulate
	 */
	public MonteCarloServer(Option option){
		this.option = option;
		bound = - new NormalDistribution().inverseCumulativeProbability( (1 - accuPoss) / 2 ) ;
	}
	
	/**
	 * Construct the server with given Option and accuracy tolerances
	 * @param option <code>Option</code> to simulate
	 * @param accuPoss Possibility tolerance of the simulation
	 * @param accuRate Absolute error tolerance of the simulation
	 */
	public MonteCarloServer(Option option, double accuPoss, double accuRate){
		this.option = option;
		this.accuPoss = accuPoss;
		this.accuRate = accuRate;
		bound = - new NormalDistribution().inverseCumulativeProbability( (1 - accuPoss) / 2 ) ;
		try {
			
		}
		catch (Exception e) {
			System.out.println("Caught: " + e);
	        e.printStackTrace();
	        }
        
	}

	
    public void run() {
        try {
        	//Make connection queues and request message
        	makeConnection();
        	
            // Begin the simulation
        	simulation();
         	
            // Clean up
            session.close();
            connection.close();
            System.out.println("ending thread");
        }
        catch (Exception e) {
            System.out.println("Caught: " + e);
            e.printStackTrace();
        }
    }
    
    /**
     * Assemble Message with the information from <code>Option</code>
     * @return assembled message
     */
    public MapMessage assembleMessageFromOption(){
    	try {
			MapMessage message = session.createMapMessage();
			message.setDouble("interestRate", option.getInterestRate());
			message.setDouble("volatility", option.getVolatility());
			message.setDouble("strickPrice", option.getStrickPrice());
			message.setInt("period", option.getPeriod().getDays());
			message.setDouble("initialPrice", option.getInitialPrice());
			message.setString("optionName", option.getName());
			message.setString("initialTime", option.getInitialTime().toString());
			message.setString("payOutType", option.getPayOutType());
			//DateTime test = new DateTime(tmp);
			return message;
		} catch (JMSException e) {
			e.printStackTrace();
		}
    	return null;
    	
    }
    
    /**
     * Make connection queues and request message
     * @throws JMSException
     */
	public void makeConnection() throws JMSException{
		// Create a ConnectionFactory
        connectionFactory = new ActiveMQConnectionFactory("vm://localhost");

        // Create a Connection
        connection = connectionFactory.createConnection();
        connection.start();

        // Create a Session
        this.session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE) ;

        // Create the destination (Topic or Queue)
        Destination destination = session.createQueue("simulation request");
        String returnTopicName = "Simulation result for server on thread  " + Thread.currentThread().getId() + " for Option " + option.getName();
        Topic returnTopic = session.createTopic(returnTopicName);
        System.out.println(returnTopicName);
        // Create a MessageProducer from the Session to the Topic or Queue
        producer = session.createProducer(destination);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        consumer = session.createConsumer(returnTopic);
   	 	
        // Create a messages
        message = this.assembleMessageFromOption();
        message.setString("returnTopicName", returnTopicName);
       
	}
	
	/**
	 * Doing the simulation
	 * @throws JMSException
	 */
    public double simulation() throws JMSException {
    	int num = 0;
        while (true) {
        	//Send the requests
            for (int i = 0; i< this.batchSize; i++)
         		producer.send(message);
            
            //get the returned payout
         	for (int i = 0; i< this.batchSize; i++){
	            TextMessage returnMessage= (TextMessage) consumer.receive();
	            double payOut = Double.parseDouble( returnMessage.getText() );
	            stat.add( payOut ); 
	            num++;
         	}
         	
         	//Check the termination condition
         	double estEnding = bound * stat.getStdVar()/accuRate; estEnding *= estEnding; 
         	/*System.out.println( option.getName() + ": "
         			+ num+"-th simulation with error: "
         			+bound * stat.getStdVar()/ Math.sqrt(num)
         			+"   with mean: "+ stat.getMean()
         			+"   with standard deviation " + stat.getStdVar()
         			+"   estEnding " + estEnding);*/
         	if (num % 10000==0)
         		System.out.println( num );
         	//System.out.print(ProgressBar.showBarByPoint(num, estEnding, 100)+"\r");
         	if (bound * stat.getStdVar()/ Math.sqrt(num) < accuRate /* stat.getMean() */&& num >20) {
				System.out.println(num+" times simulations to converge!");
				
				break;
				
			}
        }
        producer.close();
        consumer.close();
        return stat.getMean();
    }
}
