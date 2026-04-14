package elements;

import java.util.Objects;

import exceptions.IllegalIPv4Exception;
import exceptions.IllegalSubnetException;

public class Network {

    private IP ip;
    private Subnet subnet;

    private Network(IP ip, Subnet subnet) {
        this.ip = ip;
        this.subnet = subnet;
    }

    public IP getIp() {
        return ip;
    }

    public Subnet getSubnet() {
        return subnet;
    }

    public boolean contains(IP ip) {
        return subnet.matches(ip, this.ip);
    }

    @Override
    public String toString() {
        return ip.toString() + "/" + (subnet instanceof IPv4Subnet ? ((IPv4Subnet) subnet).getSlashNotation() : subnet.toString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(ip, subnet);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Network)) return false;
        Network other = (Network) obj;
        return ip.equals(other.ip) && subnet.equals(other.subnet);
    }

    public static Network fromIPandSubnet(IPv4 ip, IPv4Subnet subnet) {
        return new Network(ip, subnet);
    }

    public static Network fromIPandSubnet(IPv6 ip, IPv6Subnet subnet) {
        return new Network(ip, subnet);
    }

    public static Network fromShortsIPandSubnet(short f, short s, short t, short fo, IPv4Subnet subnet)
            throws IllegalIPv4Exception {
        return new Network(IPv4.fromShorts(f, s, t, fo), subnet);
    }

    public static Network fromShortsIPandShortsSubnet(
            short ipF, short ipS, short ipT, short ipFo,
            short mF, short mS, short mT, short mFo)
            throws IllegalIPv4Exception, IllegalSubnetException {

        return new Network(
                IPv4.fromShorts(ipF, ipS, ipT, ipFo),
                IPv4Subnet.fromShorts(mF, mS, mT, mFo)
        );
    }

    public static Network fromIPandShortsSubnet(IP ip,
            short f, short s, short t, short fo)
            throws IllegalSubnetException {

        if (ip instanceof IPv4) {
            return new Network(ip, IPv4Subnet.fromShorts(f, s, t, fo));
        }

        throw new IllegalSubnetException("IPv6 must use prefix-based subnet");
    }

    public static Network fromString(String ipAndSubnet)
            throws IllegalSubnetException, IllegalIPv4Exception {

        String[] divided = ipAndSubnet.split("/");

        if (divided.length != 2) {
            throw new IllegalArgumentException(
                "illegal network parsing: must use IP/subnet"
            );
        }

        String ipPart = divided[0];
        String subnetPart = divided[1];
        // IPv4 case
        if (ipPart.contains(".")) {
            return new Network(
                IPv4.fromString(ipPart),
                IPv4Subnet.fromSlashNotation(Short.valueOf(subnetPart))
            );
        }
        // IPv6 case (prefix notation)
        return new Network(
            IPv6.fromString(ipPart),
            new IPv6Subnet(Integer.parseInt(subnetPart))
        );
    }
}