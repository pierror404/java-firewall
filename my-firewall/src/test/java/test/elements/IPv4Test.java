package test.elements;

import org.junit.jupiter.api.Test;

import elements.IPv4;
import exceptions.IllegalIPv4Exception;

import static org.junit.jupiter.api.Assertions.*;

public class IPv4Test {
	@Test
    void testFromShortsValid() throws IllegalIPv4Exception {
        IPv4 ip = IPv4.fromShorts((short)192, (short)168, (short)1, (short)1);
        assertEquals(192, ip.getFirst());
        assertEquals(168, ip.getSecond());
        assertEquals(1, ip.getThird());
        assertEquals(1, ip.getFourth());
    }

    @Test
    void testFromShortsInvalid() {
        assertThrows(IllegalIPv4Exception.class, () -> {
            IPv4.fromShorts((short)256, (short)168, (short)1, (short)1);
        });
    }

    @Test
    void testFromStringValid() throws Exception {
        IPv4 ip = IPv4.fromString("10.0.0.1");
        assertEquals(10, ip.getFirst());
        assertEquals(0, ip.getSecond());
        assertEquals(0, ip.getThird());
        assertEquals(1, ip.getFourth());
    }

    @Test
    void testFromStringInvalidFormat() {
        assertThrows(IllegalIPv4Exception.class, () -> {
            IPv4.fromString("192.168.1"); // manca un ottetto
        });
    }

    @Test
    void testFromStringInvalidNumber() {
        assertThrows(NumberFormatException.class, () -> {
            IPv4.fromString("192.abc.1.1");
        });
    }

    @Test
    void testFromStringOutOfRange() {
        assertThrows(IllegalIPv4Exception.class, () -> {
            IPv4.fromString("192.168.1.300");
        });
    }

    @Test
    void testToString() throws Exception {
        IPv4 ip = IPv4.fromString("127.0.0.1");
        assertEquals("127.0.0.1", ip.toString());
    }

    @Test
    void testEqualsAndHashCode() throws Exception {
        IPv4 ip1 = IPv4.fromString("8.8.8.8");
        IPv4 ip2 = IPv4.fromShorts((short)8, (short)8, (short)8, (short)8);

        assertEquals(ip1, ip2);
        assertEquals(ip1.hashCode(), ip2.hashCode());
    }

    @Test
    void testNotEquals() throws Exception {
        IPv4 ip1 = IPv4.fromString("8.8.8.8");
        IPv4 ip2 = IPv4.fromString("8.8.4.4");

        assertNotEquals(ip1, ip2);
    }

    @Test
    void testGetIpAsArray() throws Exception {
        IPv4 ip = IPv4.fromString("1.2.3.4");
        short[] arr = ip.getIpAsArray();

        assertArrayEquals(new short[]{1,2,3,4}, arr);
    }
}
