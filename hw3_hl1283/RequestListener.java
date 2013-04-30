package hw3_hl1283;

import java.util.HashMap;

import hw2_hl1283.PayOut.AsianCallPayOut;
import hw2_hl1283.PayOut.CallPayOut;
import hw2_hl1283.PayOut.PayOut;
import hw2_hl1283.StockPath.BrownianStockPath;
import hw2_hl1283.StockPath.StockPath;
import hw2_hl1283.util.Option;

import javax.jms.DeliveryMode;
import javax.jms.MapMessage;
import javax.jms.MessageListener;
import javax.jms.Message;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.joda.time.DateTime;
import org.joda.time.Period;


/**
 * The RequestListener implements <code>MessageListener</code> to 
 * casts the message to a MapMessage and make a path and calculate its payout.
 */
public class RequestListener implements MessageListener {
	/** The session belongs to Client */
	private Session session;
	/** The HashMap to store the path generators */
	private HashMap<String,StockPath> generatorsMap = new HashMap<String,StockPath>();
	/** The name of return topic */
	private String returnTopicName;
	/** The working option */
	private Option option;
	/**
	 * Construct the Listener with given session.
	 * @param session
	 */
    public RequestListener(Session session){
    	this.setSession(session);
    }
    /**
     * Casts the message to a MapMessage and make a path and calculate its payout.
     *
     * @param message     the incoming message
     */
    public void onMessage(Message message) {
    	MapMessage mapMessage = null;
        try {
        	if (message instanceof MapMessage) {
                mapMessage = (MapMessage) message;
                

                //Get option info from the message
                option = this.assembleOptionFromMessage(mapMessage);
                returnTopicName = mapMessage.getString("returnTopicName");
                
                //Make a path and find the Payout
                double payout = makePathFindPayout();
                
                // Create the destination (Topic or Queue)
                Topic returnTopic = session.createTopic(returnTopicName);
                
                
                //Create a producer to reply the payout
                MessageProducer producer = session.createProducer(returnTopic);
                producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

                // Create a messages
                TextMessage returnMessage = session.createTextMessage(Double.toString(payout));
                producer.send(returnMessage);
                producer.close();
            } else if(message instanceof TextMessage) {
            	if(((TextMessage) message).getText().equals("ending")){
            		System.out.println("Received: ending message");
            		
            	} else {
            		System.out.println("Received: error message");
            		System.exit(1);
            	}
            }
        } catch (JMSException e) {
            System.err.println("JMSException in onMessage(): " + e.toString());
        } catch (Throwable t) {
            System.err.println("Exception in onMessage():" + t.getMessage());
        }
    }
    
    /**
     * A function transalte the message into <code>Option</code> Object
     * @param message the <code>MapMessage</code> used to assemble <code>Option</code> 
     * @return The assembled <code>Option</code> 
     * @throws JMSException 
     * @see Option
     */
    public Option assembleOptionFromMessage(MapMessage message) throws JMSException{
    		Option option = new Option();
	    	option.setInterestRate(message.getDouble("interestRate"));
	    	option.setVolatility(message.getDouble("volatility"));
	    	option.setStrickPrice(message.getDouble("strickPrice" ));
			option.setPeriod(Period.days(message.getInt("period")));
			option.setInitialPrice(message.getDouble("initialPrice" ));
			option.setName(message.getString("optionName"));
			option.setInitialTime(new DateTime(message.getString("initialTime")));
			option.setPayOutType(message.getString("payOutType"));
			return option;	
    }
    
    /**
     * Make a path and find the Payout
     * @return the payout
     */
    public double makePathFindPayout() {
    	//Generate or fetch a path
        StockPath pathes = null;
        if (generatorsMap.containsKey(returnTopicName)){
        	pathes = generatorsMap.get(returnTopicName);
        }else{
        	pathes = new BrownianStockPath(option);
        	generatorsMap.put(returnTopicName, pathes); 
        }
        
        if(generatorsMap.size()>200)
        	generatorsMap = new HashMap<String,StockPath>();
        
        // Get Payout
        PayOut payOutFunction = null;
        if(option.getPayOutType().equals("AsianCall"))
        	payOutFunction = new AsianCallPayOut(option);
        if (option.getPayOutType().equals("EuropeCall")) 
        	payOutFunction = new CallPayOut(option);
        
        return payOutFunction.getPayout(pathes);
        
    }
    
	public Session getSession() {
		return session;
	}
	public void setSession(Session session) {
		this.session = session;
	}

}
