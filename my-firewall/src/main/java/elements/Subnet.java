package elements;

public interface Subnet {
	boolean matches(IP ip, IP network);
}
