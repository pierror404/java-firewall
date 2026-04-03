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
	
	public boolean allowConnection(MyPacket packet) {
		
		rules.stream().forEach(rule -> {
			boolean isNetworkRule = rule.getDestination().ip().isEmpty() && rule.getDestination().network().isPresent();
			
			IP packetDest = packet.destinationAddress().get();
			
			/* TODO: add method that returns true if a rule is applicable to a packet at the Rule class */
			
			if((isNetworkRule && rule.getDestination().network().get().contains(packetDest)) ||
					(!isNetworkRule && rule.getDestination().ip().get().equals(packetDest)))
				rule.getFunction().apply(packet);				
		});
		
		
		return true;
	}
	
}
