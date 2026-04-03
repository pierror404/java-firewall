package rules;

import elements.MyPacket;

@FunctionalInterface
public interface ITriggeringRule {
	public void apply(MyPacket packet);
}
