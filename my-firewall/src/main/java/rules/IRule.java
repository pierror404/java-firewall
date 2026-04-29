package rules;

import elements.MyPacket;

public interface IRule {
	public Action trigger(MyPacket packet);
}
