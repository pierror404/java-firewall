package utils;

import rules.ITriggeringRule;

public class RuleUtils {
	public static ITriggeringRule getLogFunction(String filename) {
		ITriggeringRule logfunction = () -> {
			// TODO: implement logging function
			System.out.println(filename);
		};
		return logfunction;
	}
	
	public static ITriggeringRule getDefaultDenyFunction() {
		ITriggeringRule function = () -> {
			// TODO: drop the packet
		};
		return function;
	}
	
	public static ITriggeringRule getDefaultAllowFunction() {
		ITriggeringRule function = () -> {
			// TODO: let the packet pass
		};
		return function;
	}
}
