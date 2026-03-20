package test.elements;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import elements.IPv4;
import elements.Network;
import elements.SubnetMask;
import exceptions.IllegalIPv4Exception;
import exceptions.IllegalSubnetException;

public class NetworkTest {

    @Test
    void testFromIPandSubnet() throws Exception {
        IPv4 ip = IPv4.fromString("192.168.1.0");
        SubnetMask subnet = SubnetMask.fromString("/24");

        Network net = Network.fromIPandSubnet(ip, subnet);

        assertEquals(ip, net.getIp());
        assertEquals(subnet, net.getSubnet());
    }

    @Test
    void testFromShortsIPandSubnet() throws Exception {
        SubnetMask subnet = SubnetMask.fromString("/24");

        Network net = Network.fromShortsIPandSubnet(
                (short)192, (short)168, (short)1, (short)0,
                subnet
        );

        assertEquals("192.168.1.0/24", net.toString());
    }

    @Test
    void testFromShortsIPandShortsSubnet() throws Exception {
        Network net = Network.fromShortsIPandShortsSubnet(
                (short)10, (short)0, (short)0, (short)0,
                (short)255, (short)0, (short)0, (short)0
        );

        assertEquals("10.0.0.0/8", net.toString());
    }

    @Test
    void testFromIPandShortsSubnet() throws Exception {
        IPv4 ip = IPv4.fromString("172.16.0.0");

        Network net = Network.fromIPandShortsSubnet(
                ip,
                (short)255, (short)240, (short)0, (short)0
        );

        assertEquals("172.16.0.0/12", net.toString());
    }

    @Test
    void testFromStringValid() throws Exception {
        Network net = Network.fromString("192.168.1.0/24");

        assertEquals("192.168.1.0/24", net.toString());
    }

    @Test
    void testFromStringInvalidFormat() {
        assertThrows(IllegalArgumentException.class, () -> {
            Network.fromString("192.168.1.0-24");
        });
    }

    @Test
    void testContainsTrue() throws Exception {
        Network net = Network.fromString("192.168.1.0/24");

        IPv4 inside = IPv4.fromString("192.168.1.100");

        assertTrue(net.contains(inside));
    }

    @Test
    void testContainsFalse() throws Exception {
        Network net = Network.fromString("192.168.1.0/24");

        IPv4 outside = IPv4.fromString("192.168.2.1");

        assertFalse(net.contains(outside));
    }

    @Test
    void testContainsSameNetworkAddress() throws Exception {
        Network net = Network.fromString("10.0.0.0/8");

        IPv4 same = IPv4.fromString("10.0.0.0");

        assertTrue(net.contains(same));
    }

    @Test
    void testEqualsAndHashCode() throws Exception {
        Network net1 = Network.fromString("192.168.1.0/24");
        Network net2 = Network.fromString("192.168.1.0/24");

        assertEquals(net1, net2);
        assertEquals(net1.hashCode(), net2.hashCode());
    }

    @Test
    void testNotEquals() throws Exception {
        Network net1 = Network.fromString("192.168.1.0/24");
        Network net2 = Network.fromString("192.168.2.0/24");

        assertNotEquals(net1, net2);
    }

    @Test
    void testToString() throws Exception {
        Network net = Network.fromString("8.8.8.0/24");

        assertEquals("8.8.8.0/24", net.toString());
    }

    @Test
    void testInvalidIPv4InFactory() {
        assertThrows(IllegalIPv4Exception.class, () -> {
            Network.fromShortsIPandSubnet(
                    (short)300, (short)0, (short)0, (short)1,
                    SubnetMask.fromString("/24")
            );
        });
    }

    @Test
    void testInvalidSubnetInFactory() {
        assertThrows(IllegalSubnetException.class, () -> {
            Network.fromIPandShortsSubnet(
                    IPv4.fromString("192.168.1.0"),
                    (short)255, (short)0, (short)255, (short)0 // subnet non valida
            );
        });
    }
}
