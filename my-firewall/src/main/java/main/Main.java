package main;

import java.util.ArrayList;
import java.util.List;

import firewall.NativeBridge;
import rules.Rule;

public class Main {

	public static void main(String[] args) {
        System.out.println("Firewall Java avviato");
        
        List<Rule> rules = new ArrayList<>();
        
        new NativeBridge(rules).startFirewall();
	}

}
