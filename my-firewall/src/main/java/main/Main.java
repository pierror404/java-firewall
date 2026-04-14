package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import elements.Direction;
import elements.IPv4;
import elements.Layer;
import elements.NetworkLayerProtocol;
import exceptions.IllegalIPv4Exception;
import exceptions.IllegalSubnetException;
import firewall.NativeBridge;
import rules.Action;
import rules.Endpoint;
import rules.Rule;

public class Main {

	public static void main(String[] args) {
        System.out.println("Firewall Java avviato");
        
        List<Rule> rules = new ArrayList<>();
        try {
			rules.add(
					
					Rule.createDenyLoggingRule(Layer.NETWORK, NetworkLayerProtocol.ICMP, Action.LOG,
							new Endpoint(Optional.of(IPv4.fromString("192.168.56.1")), Optional.empty(), Optional.empty()), 
							new Endpoint(Optional.empty(), Optional.empty(), Optional.empty()), 
							"C:\\Users\\pierl\\dev\\PROVA.txt", Direction.OUT)
					);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalSubnetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalIPv4Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        new NativeBridge(rules).startFirewall();
	}

}
