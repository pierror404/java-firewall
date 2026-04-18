package rules;

import elements.Layer;
import utils.RuleUtils;
import elements.ApplicationLayerProtocol;
import elements.Direction;
import elements.IProtocol;
import elements.MyPacket;
import elements.NetworkLayerProtocol;
import elements.TransportLayerProtocol;

public class Rule implements IRule {
	
	private Layer layer;
	private IProtocol protocol;
	private Endpoint source;
	private Endpoint destination;
	private ITriggeringRule function;
	private Direction direction;
	
	protected Rule(Layer layer, IProtocol protocol, Endpoint source, Endpoint destination, ITriggeringRule function, Direction direction) {
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
	 * evaluate returns true if a packet must be dropped and false if not
	 * */
	
	public boolean evaluate(MyPacket packet) {
		boolean matches = false;
		boolean dest = false;
		boolean src = false;
		boolean proto = false;
		
		/* Matches destination */
		dest = (this.destination.network().isPresent() && this.destination.network().get().contains(packet.destinationAddress().get())) ||
				(this.destination.ip().isPresent() && this.destination.ip().get().equals(packet.destinationAddress().get()));
		
		/* Matches source */
		
		src = (this.source.network().isPresent() && this.source.network().get().contains(packet.sourceAddress().get())) ||
				(this.source.ip().isPresent() && this.source.ip().get().equals(packet.sourceAddress().get()));
		
		/* Matches protocol */
		
		proto = (this.protocol instanceof ApplicationLayerProtocol && packet.applicationProtocol().isPresent() && this.protocol == packet.applicationProtocol().get()) ||
				(this.protocol instanceof TransportLayerProtocol && packet.transportProtocol().isPresent() && this.protocol == packet.transportProtocol().get()) ||
				(this.protocol instanceof NetworkLayerProtocol && packet.networkProtocol().isPresent() && this.protocol == packet.networkProtocol().get());
		
		
		if(this.direction == Direction.IN) {
			matches = dest;
		} else if(this.direction == Direction.OUT) {
			matches = src;
		} else {
			matches = src || dest;
		}
		
		matches &= proto;
		
		/* Trigger function */
		if(matches) {
			matches = function.apply(packet);
		}
		
		return matches;
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
	
	public static Rule createDenyLoggingRule(Layer layer, IProtocol protocol, Action action, Endpoint source, Endpoint destination, String filename, Direction direction) {
		return new Rule(layer, protocol, source, destination, RuleUtils.getLogDenyFunction(filename), direction);
	}
	
	public static Rule createAllowLoggingRule(Layer layer, IProtocol protocol, Action action, Endpoint source, Endpoint destination, String filename, Direction direction) {
		return new Rule(layer, protocol, source, destination, RuleUtils.getLogAllowFunction(filename), direction);
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
