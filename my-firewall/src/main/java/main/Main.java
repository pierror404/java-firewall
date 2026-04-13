package main;

import java.util.ArrayList;
import java.util.List;

import firewall.NativeBridge;
//import sniffer.PacketSniffer;
import rules.Rule;

public class Main {

	public static void main(String[] args) {
        System.out.println("Firewall Java avviato");
        
        List<Rule> rules = new ArrayList<>();
        
        new NativeBridge(rules).startFirewall();

        /*PacketSniffer sniffer =
                new PacketSniffer();

        try {
			sniffer.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}

}
