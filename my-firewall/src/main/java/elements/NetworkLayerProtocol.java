package elements;

public enum NetworkLayerProtocol implements IProtocol{
	IPv4("IPv4"),		
	IPv6("IPv6"),
	ARP("ARP"),		
	ICMP("ICMP"),
	NAT("NAT"),		
	RIP("RIP"), 		
	OSPF("OSPF"),	  	
	IPSEC("IPSEC"), 	
	IGMP("IGMP");

	private String name;
	private static final Layer LEVEL = Layer.NETWORK;
	
	NetworkLayerProtocol(String name){
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
