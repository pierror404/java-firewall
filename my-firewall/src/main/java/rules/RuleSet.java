package rules;

import java.util.ArrayList;
import java.util.List;

public class RuleSet {
	private List<Rule> rules;
	public RuleSet() {
		this.rules = new ArrayList<>();
	}
	public RuleSet(Rule[] rules) {
		this.rules = List.of(rules);
	}
	public RuleSet(List<Rule> rules) {
		this.rules = rules;
	}
	
	public void add(Rule r) {
		this.rules.add(r);
	}
	
	public List<Rule> getRules(){
		return rules;
	}
}
