package utils;

import rules.ITriggeringRule;

public class RuleUtils {
	public static ITriggeringRule getLogFunction(String filename) {
		ITriggeringRule logfunction = (packet) -> {
			// TODO: implement logging function
			System.out.println(filename);
		};
		return logfunction;
	}
	
	public static ITriggeringRule getDefaultDenyFunction() {
		ITriggeringRule function = (packet) -> {
			// TODO: drop the packet
		};
		return function;
	}
	
	public static ITriggeringRule getDefaultAllowFunction() {
		ITriggeringRule function = (packet) -> {
			// TODO: let the packet pass
		};
		return function;
	}
}
