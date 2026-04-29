package firewall;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;

import elements.MyPacket;
import rules.Action;
import rules.Rule;

public class FirewallEngine {
	

    private final List<Rule> rules;
    private final Action defaultPolicy;

    public FirewallEngine(List<Rule> rules) {
        this(rules, Action.DENY);
    }
    
    public FirewallEngine(List<Rule> rules, Action defaultPolicy) {
    		this.rules = new CopyOnWriteArrayList<>(rules);
    		this.defaultPolicy = defaultPolicy;
    }

    public void addRule(Rule rule) {
        rules.add(rule);
    }
	
    public boolean evaluate(MyPacket packet) {
        List<CompletableFuture<Boolean>> futures = rules.stream()
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
