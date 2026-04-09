package utils;

import java.math.BigInteger;

public class IPv6Utils {

    public static BigInteger prefixToMask(int prefix) {

        return BigInteger.ONE.shiftLeft(128)
                .subtract(BigInteger.ONE)
                .shiftRight(prefix)
                .not()
                .and(BigInteger.ONE.shiftLeft(128).subtract(BigInteger.ONE));
    }

    public static boolean inNetwork(BigInteger ip, BigInteger net, BigInteger mask) {
        return ip.and(mask).equals(net.and(mask));
    }
}