package rules;

import elements.MyPacket;

@FunctionalInterface
public interface ITriggeringRule {
	public boolean apply(MyPacket packet, Rule rule);
}
