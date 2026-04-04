package firewall;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import elements.IP;
import elements.MyPacket;
import rules.Rule;

public class FirewallEngine {
	
	List<Rule> rules;
	
	public FirewallEngine() {
		this.rules = new ArrayList<Rule>();
	}
	public FirewallEngine(ArrayList<Rule> rules) {
		this.rules = rules;
	}
	public FirewallEngine(Rule[] rules) {
		this.rules = Arrays.asList(rules);
	}
	
	
	public void addRule(Rule rule) {
		this.rules.add(rule);
	}
	
	public void check(MyPacket packet) {
		
		rules.stream().forEach(rule -> {
			
			// Destination
			boolean isNetworkDestinationRule = rule.getDestination().ip().isEmpty() && rule.getDestination().network().isPresent();
			IP packetDest = packet.destinationAddress().get();
			
			// Source
			boolean isNetworkSourceRule = rule.getSource().ip().isEmpty() && rule.getSource().network().isPresent();
			IP packetSource = packet.sourceAddress().get();
			
			if((((isNetworkDestinationRule && rule.getDestination().network().get().contains(packetDest)) ||
					(!isNetworkDestinationRule && rule.getDestination().ip().get().equals(packetDest))) ||
					((isNetworkSourceRule && rule.getDestination().network().get().contains(packetSource)) ||
					(!isNetworkSourceRule && rule.getDestination().ip().get().equals(packetSource)))) && 
					packet.protocol().get() == rule.getProtocol())
				rule.getFunction().apply(packet);
		});
	}
	
}
