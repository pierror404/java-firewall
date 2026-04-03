package elements;

import java.util.Objects;

import exceptions.IllegalIPv4Exception;
import exceptions.IllegalSubnetException;

public class IPv4 implements IP{
	
	private short first;
	private short second;
	private short third;
	private short fourth;
	
	private IPv4(short first, short second, short third, short fourth) {
		super();
		this.first = first;
		this.second = second;
		this.third = third;
		this.fourth = fourth;
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
	
	public short[] getIpAsArray() {
		return new short[] {first, second, third, fourth};
	}

	@Override
	public int hashCode() {
		return Objects.hash(first, fourth, second, third);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof IPv4))
			return false;
		IPv4 other = (IPv4) obj;
		return first == other.first && fourth == other.fourth && second == other.second && third == other.third;
	}

	@Override
	public String toString() {
		return first + "." + second + "." + third + "." + fourth;
	}
	
	public static IPv4 fromString(String ip) throws NumberFormatException, IllegalSubnetException, IllegalIPv4Exception {
		String[] numbers;
		short first, second, third, fourth;
		IPv4 result;
		numbers = ip.split("\\.");
		if(numbers.length != 4)
			throw new IllegalIPv4Exception("parsing: " + ip);
		first = Short.parseShort(numbers[0]);
		second = Short.parseShort(numbers[1]);
		third = Short.parseShort(numbers[2]);
		fourth = Short.parseShort(numbers[3]);	
		if(first < 0 || first > 255 || 
				second < 0 || second > 255 ||
				third < 0 || third > 255 ||
				fourth < 0 || fourth > 255)
			throw new IllegalIPv4Exception("every number must be in a range from 0 to 255: "+first+"."+second+"."+third+"."+fourth);
		result = new IPv4(first, second, third, fourth);
		return result;
	}

	public static IPv4 fromShorts(short first, short second, short third, short fourth) throws IllegalIPv4Exception {
		if(first < 0 || first > 255 || 
				second < 0 || second > 255 ||
				third < 0 || third > 255 ||
				fourth < 0 || third > 255)
			throw new IllegalIPv4Exception("every number must be in a range from 0 to 255: "+first+"."+second+"."+third+"."+fourth);
		IPv4 result = new IPv4(first, second, third, fourth);
		return result;
	}

	@Override
	public IP getIp() {
		return this;
	}
}
