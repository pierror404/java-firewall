package elements;

import java.util.Objects;

import exceptions.IllegalSubnetException;
import utils.SubnetMaskUtils;

public class SubnetMask {
	private short first;
	private short second;
	private short third;
	private short fourth;
	
	private short slashNotation;
	
	private SubnetMask(short first, short second, short third, short fourth) {
		super();
		this.first = first;
		this.second = second;
		this.third = third;
		this.fourth = fourth;
		this.slashNotation = SubnetMaskUtils.fromSubnetToSlash(this.toString());
	}

	private SubnetMask(short slashnotation) {
		super();
		this.slashNotation = slashnotation;
		short[] mask = SubnetMaskUtils.fromSlashToSubnet(slashnotation);
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
		if (!(obj instanceof SubnetMask))
			return false;
		SubnetMask other = (SubnetMask) obj;
		return first == other.first && fourth == other.fourth && second == other.second
				&& slashNotation == other.slashNotation && third == other.third;
	}

	public static SubnetMask fromSlashNotation(short slashNotation) throws IllegalSubnetException {
		if(slashNotation < 0 || slashNotation > 32)
			throw new IllegalSubnetException("illegal slash notation subnet: /"+slashNotation+". Must be from 0 to 32");
		return new SubnetMask(slashNotation);
	}
	
	public static SubnetMask fromShorts(short first, short second, short third, short fourth) throws IllegalSubnetException {
		if(first < 0 || first > 255 || 
				second < 0 || second > 255 ||
				third < 0 || third > 255 ||
				fourth < 0 || third > 255)
			throw new IllegalSubnetException("illegal subnet: "+first+"."+second+"."+third+"."+fourth);
		return new SubnetMask(first, second, third, fourth);
	}
	
	public static SubnetMask fromString(String subnet, boolean slashNotation) throws NumberFormatException, IllegalSubnetException {
		if(slashNotation) 
			return SubnetMask.fromSlashNotation(Short.parseShort(subnet));
		
		String[] numbers = subnet.split(".");
		if(numbers.length != 4)
			throw new IllegalSubnetException("illegal subnet parsing: " + subnet);
		short first, second, third, fourth;
		first = Short.parseShort(numbers[0]);
		second = Short.parseShort(numbers[1]);
		third = Short.parseShort(numbers[2]);
		fourth = Short.parseShort(numbers[3]);	
		return SubnetMask.fromShorts(first, second, third, fourth);
			
	}

}
