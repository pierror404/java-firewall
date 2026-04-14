package firewall;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;

import elements.MyPacket;
import rules.Rule;

public class FirewallEngine {
	

    private final List<Rule> rules;

    public FirewallEngine(List<Rule> rules) {
        this.rules = new CopyOnWriteArrayList<>(rules);
    }

    public void addRule(Rule rule) {
        rules.add(rule);
    }
	
    public boolean evaluate(MyPacket packet) {
        List<CompletableFuture<Boolean>> futures = rules.stream()
                .map(rule -> CompletableFuture.supplyAsync(() -> { return rule.evaluate(packet); })).toList();

            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

            return futures.stream()
                .map(CompletableFuture::join)
                .anyMatch(Boolean::booleanValue);
    }
	
}
