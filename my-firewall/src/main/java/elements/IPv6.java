package elements;

import java.math.BigInteger;

public class IPv6 implements IP {

    private final short[] parts;
    private final BigInteger value;

    private IPv6(short[] parts) {
        this.parts = parts;
        this.value = toBigInteger(parts);
    }

    public static IPv6 fromString(String ip) {

        String[] blocks = ip.split(":");

        if (blocks.length != 8) {
            throw new IllegalArgumentException("Invalid IPv6: " + ip);
        }

        short[] parts = new short[8];

        for (int i = 0; i < 8; i++) {
            int v = Integer.parseInt(blocks[i], 16);

            if (v < 0 || v > 0xFFFF) {
                throw new IllegalArgumentException("Invalid block: " + blocks[i]);
            }

            parts[i] = (short) v;
        }

        return new IPv6(parts);
    }

    public BigInteger toBigInteger() {
        return value;
    }

    private static BigInteger toBigInteger(short[] parts) {
        BigInteger res = BigInteger.ZERO;

        for (short p : parts) {
            res = res.shiftLeft(16).add(BigInteger.valueOf(p & 0xFFFF));
        }

        return res;
    }

    @Override
    public IP getIp() {
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof IPv6 other)) return false;
        return this.value.equals(other.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (short p : parts) {
            sb.append(Integer.toHexString(p & 0xFFFF)).append(":");
        }

        return sb.substring(0, sb.length() - 1);
    }
}