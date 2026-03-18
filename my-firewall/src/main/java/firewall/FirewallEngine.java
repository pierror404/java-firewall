package firewall;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.pcap4j.core.*;
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
	
	public void allowConnection(String ip, String port, String protocol) {
		for(IRule r : rules) {
			
			boolean ipMatch = r.getIp().equals("*") || r.getIp().equals(ip);
			
		}
	}
	
}
