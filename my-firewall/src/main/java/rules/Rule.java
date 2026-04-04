package rules;

import elements.Layer;
import utils.RuleUtils;
import elements.Direction;
import elements.IProtocol;

public class Rule implements IRule {
	
	private Layer layer;
	private IProtocol protocol;
	private Endpoint source;
	private Endpoint destination;
	private ITriggeringRule function;
	private Direction direction;
	
	private Rule(Layer layer, IProtocol protocol, Endpoint source, Endpoint destination, ITriggeringRule function, Direction direction) {
		super();
		this.layer = layer;
		this.protocol = protocol;
		this.source = source;
		this.destination = destination;
		this.function = function;
		this.direction = direction;
	}
	
	public ITriggeringRule getFunction() {
		return function;
	}

	public Endpoint getSource() {
		return source;
	}

	public Endpoint getDestination() {
		return destination;
	}
	
	public IProtocol getProtocol() {
		return protocol;
	}
	
	public Direction getDirection() {
		return direction;
	}

	@Override
	public Layer getLayer() {
		return layer;
	}
	
	/* 
	 * ALLOW and LOG rules must let the packet pass
	 * DENY and TRIGGER rules must drop the packet
	 * 
	 * Difference between ALLOW and LOG:
	 * 	- LOG just logs the content in a default way
	 * 	- ALLOW can either do nothing and let the packet pass, or execute a custom function and then let the packet pass
	 * 
	 * Difference between DENY and TRIGGER:
	 * 	- DENY just drops the packet
	 * 	- TRIGGER can either just drop the packet, or execute a custom function and then drop the packet
	 */
	
	public static Rule createLoggingRule(Layer layer, IProtocol protocol, Action action, Endpoint source, Endpoint destination, String filename, Direction direction) {
		return new Rule(layer, protocol, source, destination, RuleUtils.getLogFunction(filename), direction);
	}
	
	public static Rule createTriggerRule(Layer layer, IProtocol protocol, Action action, Endpoint source, Endpoint destination, ITriggeringRule function, Direction direction) {
		return new Rule(layer, protocol, source, destination, function, direction);
	}
	
	public static Rule createDefaultDenyRule(Layer layer, IProtocol protocol, Endpoint source, Endpoint destination, Direction direction) {
		return new Rule(layer, protocol, source, destination, RuleUtils.getDefaultDenyFunction(), direction);
	}
	
	public static Rule createDefaultAllowRule(Layer layer, IProtocol protocol, Endpoint source, Endpoint destination, Direction direction) {
		return new Rule(layer, protocol, source, destination, RuleUtils.getDefaultAllowFunction(), direction);
	}

}
