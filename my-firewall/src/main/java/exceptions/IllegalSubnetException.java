package exceptions;

@SuppressWarnings("serial")
public class IllegalSubnetException extends Exception {
	
	public IllegalSubnetException(String msg) {
		super("Illegal subnet " + msg);
	}
	
}
