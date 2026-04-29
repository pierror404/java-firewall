package firewall;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import elements.MyPacket;
import rules.Action;
import rules.Rule;
import rules.RuleSet;

public class FirewallEngine {
	

    private final RuleSet rules;
    private final Action defaultPolicy;

    public FirewallEngine(List<Rule> rules) {
        this(new RuleSet(rules), Action.DENY);
    }
    public FirewallEngine(RuleSet set) {
    		this(set, Action.DENY);
    }
    public FirewallEngine(RuleSet rules, Action defaultPolicy) {
    		this.rules = rules;
    		this.defaultPolicy = defaultPolicy;
    		this.rules.getRules().forEach(el -> {
    			if(el.getAction() == null)
    				el.setAction(defaultPolicy);
    		});
    }

    public void addRule(Rule rule) {
        rules.add(rule);
    }
	
    public boolean evaluate(MyPacket packet) {
        List<CompletableFuture<Boolean>> futures = rules.getRules().stream()
                .map(rule -> CompletableFuture.supplyAsync(() -> { 
                		Optional<Action> result = rule.evaluate(packet);
                		Action policy = defaultPolicy;
                		if(result.isPresent())
                			policy = result.get();
                		return (policy == Action.DENY); 
                	})).toList();

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        return futures.stream()
        		.map(CompletableFuture::join)
            .anyMatch(Boolean::booleanValue);
    }
	
}
