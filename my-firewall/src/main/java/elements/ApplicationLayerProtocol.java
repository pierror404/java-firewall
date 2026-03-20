package elements;

public enum ApplicationLayerProtocol implements IProtocol{
	
	HTTP("HTTP"),
	HTTPS("HTTPS"),	
	SMTP("SMTP"),	
	POP3("POP3"),	
	IMAP("IMAP"),	
	FTP("FTP"),	
	TFTP("TFTP"),	
	SNMP("SNMP"),	
	TELNET("TELNET"),	
	SSH("SSH"),
	DNS("DNS"),		
	DHCP("DHCP");

	private String name;
	private static final Layer LEVEL = Layer.APPLICATION;
	
	ApplicationLayerProtocol(String name){
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
