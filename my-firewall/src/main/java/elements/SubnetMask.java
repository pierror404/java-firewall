package elements;

import java.util.Objects;
import java.util.Optional;

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
			throw new IllegalSubnetException("slash notation must be from 0 to 32: /"+slashNotation);
		return new SubnetMask(slashNotation);
	}
	
	public static SubnetMask fromShorts(short first, short second, short third, short fourth) throws IllegalSubnetException {
		Optional<String> res = SubnetMaskUtils.validateSubnetMask(new short[] {first,second,third,fourth});
		if(res.isPresent())
			throw new IllegalSubnetException(res.get());
		return new SubnetMask(first, second, third, fourth);
	}
	
	public static SubnetMask fromString(String subnet) throws NumberFormatException, IllegalSubnetException {
		if(subnet.charAt(0) == '/') 
			return SubnetMask.fromSlashNotation(Short.parseShort(subnet.replace("/", "")));
		
		String[] numbers = subnet.split("\\.");
		if(numbers.length != 4)
			throw new IllegalSubnetException("parsing: " + subnet);
		short first, second, third, fourth;
		first = Short.parseShort(numbers[0]);
		second = Short.parseShort(numbers[1]);
		third = Short.parseShort(numbers[2]);
		fourth = Short.parseShort(numbers[3]);	
		return SubnetMask.fromShorts(first, second, third, fourth);
			
	}
	


}
