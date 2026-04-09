package elements;

import java.util.Optional;

public record MyPacket(Optional<IP> sourceAddress, 
		Optional<NetPort> sourcePort, 
		Optional<IP> destinationAddress, 
		Optional<NetPort> destinationPort,
		Optional<IProtocol> networkProtocol,
		Optional<IProtocol> transportProtocol,
		Optional<IProtocol> applicationProtocol,
	    Optional<byte[]> rawIpHeader,
	    Optional<byte[]> rawTransportHeader,
	    Optional<byte[]> payload) {

	@Override
	public String toString() {
		return "MyPacket [sourceAddress=" + sourceAddress + ", sourcePort=" + sourcePort + ", destinationAddress="
				+ destinationAddress + ", destinationPort=" + destinationPort + ", networkProtocol=" + networkProtocol
				+ ", transportProtocol=" + transportProtocol + ", applicationProtocol=" + applicationProtocol + "]";
	}

	
	
}
