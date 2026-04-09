package elements;

import java.util.Objects;
import java.util.Optional;

import exceptions.IllegalSubnetException;
import utils.IPv4SubnetUtils;
import utils.IPv4Utils;

public class IPv4Subnet implements Subnet {
	private short first;
	private short second;
	private short third;
	private short fourth;
	
	private short slashNotation;
	
	private IPv4Subnet(short first, short second, short third, short fourth) {
		super();
		this.first = first;
		this.second = second;
		this.third = third;
		this.fourth = fourth;
		this.slashNotation = IPv4SubnetUtils.fromSubnetToSlash(this.toString());
	}

	private IPv4Subnet(short slashnotation) {
		super();
		this.slashNotation = slashnotation;
		short[] mask = IPv4SubnetUtils.fromSlashToSubnet(slashnotation);
		this.first = mask[0];
		this.second = mask[1];
		this.third = mask[2];
		this.fourth = mask[3];
	}

	public short getFirst() {
		return first;
	}

	public short getSecond() {
		return second;
	}

	public short getThird() {
		return third;
	}

	public short getFourth() {
		return fourth;
	}

	public short getSlashNotation() {
		return slashNotation;
	}
	
	@Override
	public String toString() {
		return this.first + "." + this.second + "." + this.third + "." + this.fourth;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(first, fourth, second, slashNotation, third);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof IPv4Subnet))
			return false;
		IPv4Subnet other = (IPv4Subnet) obj;
		return first == other.first && fourth == other.fourth && second == other.second
				&& slashNotation == other.slashNotation && third == other.third;
	}

	public static IPv4Subnet fromSlashNotation(short slashNotation) throws IllegalSubnetException {
		if(slashNotation < 0 || slashNotation > 32)
			throw new IllegalSubnetException("slash notation must be from 0 to 32: /"+slashNotation);
		return new IPv4Subnet(slashNotation);
	}
	
	public static IPv4Subnet fromShorts(short first, short second, short third, short fourth) throws IllegalSubnetException {
		Optional<String> res = IPv4SubnetUtils.validateSubnetMask(new short[] {first,second,third,fourth});
		if(res.isPresent())
			throw new IllegalSubnetException(res.get());
		return new IPv4Subnet(first, second, third, fourth);
	}
	
	public static IPv4Subnet fromString(String subnet) throws NumberFormatException, IllegalSubnetException {
		if(subnet.charAt(0) == '/') 
			return IPv4Subnet.fromSlashNotation(Short.parseShort(subnet.replace("/", "")));
		
		String[] numbers = subnet.split("\\.");
		if(numbers.length != 4)
			throw new IllegalSubnetException("parsing: " + subnet);
		short first, second, third, fourth;
		first = Short.parseShort(numbers[0]);
		second = Short.parseShort(numbers[1]);
		third = Short.parseShort(numbers[2]);
		fourth = Short.parseShort(numbers[3]);	
		return IPv4Subnet.fromShorts(first, second, third, fourth);
			
	}

	@Override
	public boolean matches(IP ip, IP network) {
	    if (!(ip instanceof IPv4 v4) || !(network instanceof IPv4 n4)) return false;

	    int mask = IPv4SubnetUtils.toInt(this);

	    return (IPv4Utils.ipToInt(v4) & mask)
	        == (IPv4Utils.ipToInt(n4) & mask);
	}
	


}
