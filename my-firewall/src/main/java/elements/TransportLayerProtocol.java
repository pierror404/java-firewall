package elements;

public enum TransportLayerProtocol implements IProtocol{
	UDP("UDP"),
	TCP("TCP"), 	
	QUIC("QUIC"), 	
	SCTP	("SCTP");
	
	private String name;
	private static final Layer LEVEL = Layer.TRANSPORT;
	
	TransportLayerProtocol(String name){
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Layer getLevel() {
		return LEVEL;
	}
	
	@Override
	public String toString() {
		return name + "(" + LEVEL.getLayer() + ")";
	}

}
