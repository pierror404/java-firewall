package utils;

import elements.IPv4;

public class IPv4Utils {
	
	public static int ipToInt(IPv4 ip) {
	    short[] parts = ip.getIpAsArray();
	    int result = 0;
	    for (short part : parts) {
	        result = (result << 8) | part;
	    }
	    return result;
	}
	
    public static boolean inNetwork(int ip, int network, int mask) {
        return (ip & mask) == (network & mask);
    }
	
	
}
