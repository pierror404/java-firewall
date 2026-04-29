package rules;

import elements.MyPacket;

@FunctionalInterface
public interface ITriggeringRule {
	public Action apply(MyPacket packet, Rule rule);
}
