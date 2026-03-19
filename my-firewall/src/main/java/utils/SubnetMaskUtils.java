package utils;

public class SubnetMaskUtils {
	public static short[] fromSlashToSubnet(short slashNotation) {
        int mask = 0xffffffff << (32 - slashNotation);

        short first = (short) ((mask >>> 24) & 0xff);
        short second = (short) ((mask >>> 16) & 0xff);
        short third = (short) ((mask >>> 8) & 0xff);
        short fourth = (short) (mask & 0xff);
        
        return new short[] {first,second,third,fourth};
	}
	
	public static short fromSubnetToSlash(String subnet) {
	    String[] parts = subnet.split("\\.");
	    int mask = 0;

	    for (String part : parts) {
	        int value = Integer.parseInt(part);
	        mask = (mask << 8) | value;
	    }

	    boolean zeroFound = false;
	    short prefix = 0;

	    for (int i = 31; i >= 0; i--) {
	        boolean bit = (mask & (1 << i)) != 0;

	        if (bit) {
	            if (zeroFound) {
	                throw new IllegalArgumentException("invalid subnet: " + subnet);
	            }
	            prefix++;
	        } else {
	            zeroFound = true;
	        }
	    }

	    return prefix;
	}
}
