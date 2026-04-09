package elements;

import java.math.BigInteger;

public class IPv6Subnet implements Subnet {

    private final BigInteger mask;

    public IPv6Subnet(int prefixLength) {
        if (prefixLength < 0 || prefixLength > 128)
            throw new IllegalArgumentException();

        this.mask = createMask(prefixLength);
    }

    private BigInteger createMask(int prefix) {
        return BigInteger.ONE.shiftLeft(128).subtract(BigInteger.ONE)
                .shiftRight(128 - prefix)
                .shiftLeft(128 - prefix);
    }

    public int getPrefixLength() {
        return mask.bitCount(); // opzionale o memorizzalo direttamente
    }

	@Override
	public boolean matches(IP ip, IP network) {
	    if (!(ip instanceof IPv6 v6) || !(network instanceof IPv6 n6))
	        return false;

	    return v6.toBigInteger().and(mask)
	        .equals(n6.toBigInteger().and(mask));
	}
}