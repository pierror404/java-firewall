package elements;

import java.util.Optional;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.Packet.Header;

public record MyPacket(Optional<IP> sourceAddress, 
		Optional<NetPort> sourcePort, 
		Optional<IP> destinationAddress, 
		Optional<NetPort> destinationPort,
		Optional<IProtocol> protocol,
		Optional<Header> header,
		Optional<Packet> payload) {

	@Override
	public String toString() {
		return "Packet: [Source Addr=" + sourceAddress + ", Source Port=" + sourcePort + ", Dest Addr="
				+ destinationAddress + ", Dest Port=" + destinationPort + ", Protocol=" + protocol + "]";
	}
	
}
