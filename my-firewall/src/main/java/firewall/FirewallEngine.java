package firewall;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;

import elements.MyPacket;
import rules.Rule;

public class FirewallEngine {
	

    private final List<Rule> rules;

    public FirewallEngine(List<Rule> rules) {
        this.rules = new CopyOnWriteArrayList<>();
    }

    public void addRule(Rule rule) {
        rules.add(rule);
    }
	
    public boolean evaluate(MyPacket packet) {
    		/*
    		 * Must implement:
    		 * 		- check every rule multi-threaded
    		 * 		- result collection
    		 * 		- return the and of the results
    		 * 
    		 * */
        List<CompletableFuture<Boolean>> futures = rules.stream()
                .map(rule -> CompletableFuture.supplyAsync(() -> {/*TODO: evaluate rule*/return false;}))
                .toList();

            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

            return futures.stream()
                .map(CompletableFuture::join)
                .anyMatch(Boolean::booleanValue);
    }
	
}
