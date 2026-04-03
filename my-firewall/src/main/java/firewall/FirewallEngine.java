package firewall;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import elements.IProtocol;
import elements.IPv4;
import elements.NetPort;
import rules.IRule;

public class FirewallEngine {
	
	List<IRule> rules;
	
	public FirewallEngine() {
		this.rules = new ArrayList<IRule>();
	}
	public FirewallEngine(ArrayList<IRule> rules) {
		this.rules = rules;
	}
	public FirewallEngine(IRule[] rules) {
		this.rules = Arrays.asList(rules);
	}
	
	
	public void addRule(IRule rule) {
		this.rules.add(rule);
	}
	
	public void allowConnection(IPv4 ip, NetPort port, IProtocol protocol) {
		
	}
	
}
