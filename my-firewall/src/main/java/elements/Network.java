package elements;

import java.util.Objects;

import exceptions.IllegalIPv4Exception;
import exceptions.IllegalSubnetException;
import utils.IPv4Utils;

public class Network {
	
	private IP ip;
	private SubnetMask subnet;
	
	private Network(IP ip, SubnetMask subnet) {
		super();
		this.ip = ip;
		this.subnet = subnet;
	}
	
	public IP getIp() {
		return ip;
	}

	public SubnetMask getSubnet() {
		return subnet;
	}
	
	public boolean contains(IP ip) {
		if(ip instanceof IPv4 && this.ip instanceof IPv4) {
			int mask = 0xffffffff << (32 - this.subnet.getSlashNotation());
			
			int net = IPv4Utils.ipToInt((IPv4) this.ip);
			int address = IPv4Utils.ipToInt((IPv4) ip);
			
			return (net & mask) == (address & mask);
		}
		
		/* TODO: implement method contains for IPv6 */
		return false;
		
	}
	
	@Override
	public String toString() {
		return ip.toString() + "/" + subnet.getSlashNotation();
	}

	@Override
	public int hashCode() {
		return Objects.hash(ip, subnet);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Network))
			return false;
		Network other = (Network) obj;
		return ip.equals(other.ip) && subnet.equals(other.subnet);
	}

	public static Network fromIPandSubnet(IPv4 ip, SubnetMask subnet) {
		return new Network(ip, subnet);
	}
	
	public static Network fromShortsIPandSubnet(short first, short second, short third, short fourth, SubnetMask subnet) throws IllegalIPv4Exception {
		return new Network(IPv4.fromShorts(first, second, third, fourth), subnet);
	}
	
	public static Network fromShortsIPandShortsSubnet(short ipFirst, short ipSecond, short ipThird, short ipFourth, short subnetFirst, short subnetSecond, short subnetThird, short subnetFourth) throws IllegalIPv4Exception, IllegalSubnetException {
		return new Network(IPv4.fromShorts(ipFirst, ipSecond, ipThird, ipFourth), SubnetMask.fromShorts(subnetFirst, subnetSecond, subnetThird, subnetFourth));
	}
	
	public static Network fromIPandShortsSubnet(IP ip, short first, short second, short third, short fourth) throws IllegalSubnetException {
		return new Network(ip, SubnetMask.fromShorts(first, second, third, fourth));
	}
	
	public static Network fromString(String ipAndSubnet) throws NumberFormatException, IllegalSubnetException, IllegalIPv4Exception {
		String[] divided = ipAndSubnet.split("/");
		if(divided.length != 2)
			throw new IllegalArgumentException("illegal network parsing: must use networkIP/subnetmask");
		return new Network(IPv4.fromString(divided[0]), SubnetMask.fromString("/"+divided[1]));
	}
}
