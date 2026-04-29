package rules;

import elements.MyPacket;

public interface ITriggeringRule {
	public Action trigger(MyPacket packet);
}
