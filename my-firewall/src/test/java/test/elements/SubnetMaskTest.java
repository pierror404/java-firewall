package test.elements;


import org.junit.jupiter.api.Test;

import elements.IPv4Subnet;
import exceptions.IllegalSubnetException;

import static org.junit.jupiter.api.Assertions.*;

public class SubnetMaskTest {
	
	@Test
	void testFromSlashNotation24() throws IllegalSubnetException {
		assertDoesNotThrow(() -> IPv4Subnet.fromSlashNotation((short)24));
		assertEquals("255.255.255.0", IPv4Subnet.fromSlashNotation((short)24).toString());
	}
	
	@Test
	void testFromSlashNotation30() throws IllegalSubnetException {
		assertDoesNotThrow(() -> IPv4Subnet.fromSlashNotation((short)30));
		assertEquals("255.255.255.252", IPv4Subnet.fromSlashNotation((short)30).toString());
	}
	
	@Test
	void testFromSlashNotation8() throws IllegalSubnetException {
		assertDoesNotThrow(() -> IPv4Subnet.fromSlashNotation((short)8));
		assertEquals("255.0.0.0", IPv4Subnet.fromSlashNotation((short)8).toString());
	}
	
	@Test
	void testFromSlashNotationInvalid() {
		assertThrowsExactly(IllegalSubnetException.class, () -> IPv4Subnet.fromSlashNotation((short)33));
	}
	
	@Test 
	void testFromShorts24() throws IllegalSubnetException {
		assertDoesNotThrow(() -> IPv4Subnet.fromShorts((short)255, (short)255, (short)255, (short)0));
		assertEquals("255.255.255.0", IPv4Subnet.fromShorts((short)255, (short)255, (short)255, (short)0).toString());
		assertEquals((short)24, IPv4Subnet.fromShorts((short)255, (short)255, (short)255, (short)0).getSlashNotation());
	}
	
	@Test 
	void testFromShorts30() throws IllegalSubnetException {
		assertDoesNotThrow(() -> IPv4Subnet.fromShorts((short)255, (short)255, (short)255, (short)252));
		assertEquals("255.255.255.252", IPv4Subnet.fromShorts((short)255, (short)255, (short)255, (short)252).toString());
		assertEquals((short)30, IPv4Subnet.fromShorts((short)255, (short)255, (short)255, (short)252).getSlashNotation());
	}
	
	@Test 
	void testFromShorts8() throws IllegalSubnetException {
		assertDoesNotThrow(() -> IPv4Subnet.fromShorts((short)255, (short)0, (short)0, (short)0));
		assertEquals("255.0.0.0", IPv4Subnet.fromShorts((short)255, (short)0, (short)0, (short)0).toString());
		assertEquals((short)8, IPv4Subnet.fromShorts((short)255, (short)0, (short)0, (short)0).getSlashNotation());
	}
	
	@Test 
	void testFromShortsInvalidGreater255() {
		assertThrowsExactly(IllegalSubnetException.class, () -> IPv4Subnet.fromShorts((short)255, (short)255, (short)255, (short)256));
		assertThrowsExactly(IllegalSubnetException.class, () -> IPv4Subnet.fromShorts((short)255, (short)255, (short)256, (short)0));
		assertThrowsExactly(IllegalSubnetException.class, () -> IPv4Subnet.fromShorts((short)255, (short)256, (short)255, (short)0));
		assertThrowsExactly(IllegalSubnetException.class, () -> IPv4Subnet.fromShorts((short)256, (short)255, (short)255, (short)0));
	}
	
	@Test 
	void testFromShortsInvalidFound1After0() {
		assertThrowsExactly(IllegalSubnetException.class, () -> IPv4Subnet.fromShorts((short)255, (short)0, (short)255, (short)0));
	}
	
	@Test
	void testFromStringSlash24() throws NumberFormatException, IllegalSubnetException {
		assertDoesNotThrow(() -> IPv4Subnet.fromString("/24"));
		assertEquals("255.255.255.0", IPv4Subnet.fromString("/24").toString());
		assertEquals((short)24, IPv4Subnet.fromString("/24").getSlashNotation());
	}
	
	@Test
	void testFromStringSlash30() throws NumberFormatException, IllegalSubnetException {
		assertDoesNotThrow(() -> IPv4Subnet.fromString("/30"));
		assertEquals("255.255.255.252", IPv4Subnet.fromString("/30").toString());
		assertEquals((short)30, IPv4Subnet.fromString("/30").getSlashNotation());
	}
	
	@Test
	void testFromStringSlash8() throws NumberFormatException, IllegalSubnetException {
		assertDoesNotThrow(() -> IPv4Subnet.fromString("/8"));
		assertEquals("255.0.0.0", IPv4Subnet.fromString("/8").toString());
		assertEquals((short)8, IPv4Subnet.fromString("/8").getSlashNotation());
	}
	
	@Test
	void testFromStringInvalidSlash33() throws NumberFormatException, IllegalSubnetException {
		assertThrowsExactly(IllegalSubnetException.class, () -> IPv4Subnet.fromString("/33"));
	}
	
	@Test
	void testFromString24() throws NumberFormatException, IllegalSubnetException {
		assertDoesNotThrow(() -> IPv4Subnet.fromString("255.255.255.0"));
		assertEquals("255.255.255.0", IPv4Subnet.fromString("255.255.255.0").toString());
		assertEquals((short)24, IPv4Subnet.fromString("255.255.255.0").getSlashNotation());
	}
	
	@Test
	void testFromString30() throws NumberFormatException, IllegalSubnetException {
		assertDoesNotThrow(() -> IPv4Subnet.fromString("255.255.255.252"));
		assertEquals("255.255.255.252", IPv4Subnet.fromString("255.255.255.252").toString());
		assertEquals((short)30, IPv4Subnet.fromString("255.255.255.252").getSlashNotation());
	}
	
	@Test
	void testFromString8() throws NumberFormatException, IllegalSubnetException {
		assertDoesNotThrow(() -> IPv4Subnet.fromString("255.0.0.0"));
		assertEquals("255.0.0.0", IPv4Subnet.fromString("255.0.0.0").toString());
		assertEquals((short)8, IPv4Subnet.fromString("255.0.0.0").getSlashNotation());
	}
	
	@Test
	void testFromStringInvalidGreater255() {
		assertThrowsExactly(IllegalSubnetException.class, () -> IPv4Subnet.fromString("255.255.255.256"));
		assertThrowsExactly(IllegalSubnetException.class, () -> IPv4Subnet.fromString("255.255.256.0"));
		assertThrowsExactly(IllegalSubnetException.class, () -> IPv4Subnet.fromString("255.256.255.0"));
		assertThrowsExactly(IllegalSubnetException.class, () -> IPv4Subnet.fromString("255.256.255.0"));
	}
	
	@Test
	void testFromStringInvalidFound1After0() {
		assertThrowsExactly(IllegalSubnetException.class, () -> IPv4Subnet.fromString("255.0.255.0"));
	}
	
	@Test
	void testEquals() throws NumberFormatException, IllegalSubnetException {
		assertEquals(IPv4Subnet.fromString("255.255.255.0"), IPv4Subnet.fromString("/24"));
		assertEquals(IPv4Subnet.fromString("255.255.255.0"), IPv4Subnet.fromShorts((short)255, (short)255, (short)255, (short)0));
		assertEquals(IPv4Subnet.fromString("/24"), IPv4Subnet.fromShorts((short)255, (short)255, (short)255, (short)0));
		assertEquals(IPv4Subnet.fromSlashNotation((short)24), IPv4Subnet.fromString("/24"));
		assertEquals(IPv4Subnet.fromString("255.255.255.0"), IPv4Subnet.fromSlashNotation((short)24));
		assertEquals(IPv4Subnet.fromSlashNotation((short)24), IPv4Subnet.fromShorts((short)255, (short)255, (short)255, (short)0));
	}
}
