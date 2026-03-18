package elements;

public enum TransportLayerProtocol implements IProtocol{
	UDP("UDP"),
	TCP("TCP"), 	
	QUIC("QUIC"), 	
	SCTP	("SCTP");
	
	private String name;
	private static final int LEVEL = 4;
	
	TransportLayerProtocol(String name){
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getLevel() {
		return LEVEL;
	}
	
	@Override
	public String toString() {
		return name + "(" + LEVEL + ")";
	}

}
