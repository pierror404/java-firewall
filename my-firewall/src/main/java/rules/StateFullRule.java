package rules;

import elements.Direction;
import elements.IProtocol;
import elements.Layer;
import utils.RuleUtils;

public class StateFullRule extends Rule {
	
	private int count;
	
	private StateFullRule(Layer layer, IProtocol protocol, Endpoint source, Endpoint destination, ITriggeringRule function, Direction direction) {
		super(layer, protocol, source, destination, function, direction);
		this.count = 0;
		
	}
	
	public int getCount() {
		return count;
	}
	
	public static Rule createDenyLoggingStatefullRule(Layer layer, IProtocol protocol, Action action, Endpoint source, Endpoint destination, String filename, Direction direction) {
		return new StateFullRule(layer, protocol, source, destination, RuleUtils.getLogDenyFunction(filename), direction);
	}
	
	public static Rule createAllowLoggingStatefullRule(Layer layer, IProtocol protocol, Action action, Endpoint source, Endpoint destination, String filename, Direction direction) {
		return new StateFullRule(layer, protocol, source, destination, RuleUtils.getLogAllowFunction(filename), direction);
	}
	
	public static Rule createTriggerStatefullRule(Layer layer, IProtocol protocol, Action action, Endpoint source, Endpoint destination, ITriggeringRule function, Direction direction) {
		return new StateFullRule(layer, protocol, source, destination, function, direction);
	}
	
	public static Rule createDefaultDenyStatefullRule(Layer layer, IProtocol protocol, Endpoint source, Endpoint destination, Direction direction) {
		return new StateFullRule(layer, protocol, source, destination, RuleUtils.getDefaultDenyFunction(), direction);
	}
	
	public static Rule createDefaultAllowStatefullRule(Layer layer, IProtocol protocol, Endpoint source, Endpoint destination, Direction direction) {
		return new StateFullRule(layer, protocol, source, destination, RuleUtils.getDefaultAllowFunction(), direction);
	}
}
