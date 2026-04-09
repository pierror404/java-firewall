package utils;

import java.util.Optional;

import elements.IPv4Subnet;

public class IPv4SubnetUtils {
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
	
	public static Optional<String> validateSubnetMask(short[] parts) {
	    if (parts.length != 4) {
	    		String mes = "";
	    		for(int i = 0; i < parts.length-1; i++) {
	    			mes += parts[i] + ".";
	    		}
	    		mes += parts[parts.length-1];
	    		return Optional.of("parsing: " + mes);
	    }
	    int mask = 0;
	    for (short part : parts) {
	        if (part < 0 || part > 255) return Optional.of("every part must be >=0 and <=255: "+parts[0]+"."+parts[1]+"."+parts[2]+"."+parts[3]);
	        mask = (mask << 8) | part;
	    }

	    boolean zeroFound = false;
	    for (int i = 31; i >= 0; i--) {
	        boolean bit = (mask & (1 << i)) != 0;
	        if (bit) {
	            if (zeroFound) return Optional.of("found a bit set to 1 after one set to 0: "+parts[0]+"."+parts[1]+"."+parts[2]+"."+parts[3]);
	        } else {
	            zeroFound = true;
	        }
	    }
	    return Optional.empty();
	}
	
    public static int toInt(IPv4Subnet mask) {
        return ((mask.getFirst() & 0xFF) << 24) |
               ((mask.getSecond() & 0xFF) << 16) |
               ((mask.getThird() & 0xFF) << 8) |
               (mask.getFourth() & 0xFF);
    }
}
