package main;

import java.util.ArrayList;
import java.util.List;

import firewall.NativeBridge;
import rules.Rule;

public class Main {
	public static void main(String[] args) {
        System.out.println("Firewall Java avviato");
        
        List<Rule> rules = new ArrayList<>();
        /*
         * 
         * TO CREATE A RULE YOU MUST SPECIFY IT IN A FILE IN THIS PACKAGE
         * IT MUST EXTEND Rule ABSTRACT CLASS
         * IF YOU WANT A CUSTOM STATE YOU CAN INCAPSULATE IT IN YOUR CLASS IMPLEMENTATION
         * IF YOU WANT TO HAVE A CUSTOM BEHAVIOUR FOR YOUR RULE YOU CAN OVERRIDE trigger METHOD IN YOUR CLASS
         * 
         * */
        new NativeBridge(rules).startFirewall();
	}

}
