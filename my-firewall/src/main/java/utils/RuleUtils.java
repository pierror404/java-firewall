package utils;

import logger.LogLevel;
import logger.Logger;
import rules.Action;
import rules.ITriggeringRule;

public class RuleUtils {
	public static ITriggeringRule getLogDenyFunction(String filename, LogLevel level, String message) {
		ITriggeringRule logfunction = (packet, _) -> {
			Logger.writeOnLogFile(packet, filename, level, message);
			return Action.DENY;
		};
		return logfunction;
	}
	
	public static ITriggeringRule getLogAllowFunction(String filename, LogLevel level, String message) {
		ITriggeringRule logfunction = (packet, _) -> {
			Logger.writeOnLogFile(packet, filename, level, message);
			return Action.ALLOW;
		};
		return logfunction;
	}
	
	public static ITriggeringRule getDefaultDenyFunction() {
		ITriggeringRule function = (_, _) -> {
			return Action.DENY;
		};
		return function;
	}
	
	public static ITriggeringRule getDefaultAllowFunction() {
		ITriggeringRule function = (_, _) -> {
			return Action.ALLOW;
		};
		return function;
	}
	
	public static boolean drop() {
		return true;
	}
	
	public static boolean allow() {
		return false;
	}
	
	public static ITriggeringRule dropAfterThreshold(int count) {
		ITriggeringRule function = (_, rule) -> {
			return (rule.getState().getHitcount() >= count) ? Action.DENY : Action.ALLOW;
		};
		return function;
	}
	
	public static ITriggeringRule acceptAfterThreshold(int count) {
		ITriggeringRule function = (_, rule) -> {
			return (rule.getState().getHitcount() < count) ? Action.DENY : Action.ALLOW;
		};
		return function;
	}
}
