package exceptions;

@SuppressWarnings("serial")
public class IllegalIPv4Exception extends Exception{
	public IllegalIPv4Exception(String msg) {
		super("Illegal IPv4 " + msg);
	}
}
