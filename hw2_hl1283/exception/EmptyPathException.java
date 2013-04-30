package hw2_hl1283.exception;

public class EmptyPathException extends Exception {
	
	 /**
	 * The exception thrown when <code>PayOut</code> meets a empty <code>SotckPath</code>.
	 */
	private static final long serialVersionUID = 1L;

	public EmptyPathException(String mess)
	    {
	        super(mess);
	    }
}
