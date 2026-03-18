package elements;

import java.util.Objects;

import exceptions.IllegalPortException;

public class NetPort {
	int port;

	public NetPort(int port) {
		super();
		this.port = port;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(port);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof NetPort))
			return false;
		NetPort other = (NetPort) obj;
		return port == other.port;
	}

	@Override
	public String toString() {
		return ":"+port;
	}
	
	public NetPort fromInt(int port) throws IllegalPortException {
		if(port < 0 || port > 65535) 
			throw new IllegalPortException("Port specified is illegal: " + port + ". Must be > 0 and < 65535");
		return new NetPort(port);
	}
}
