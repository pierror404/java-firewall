package rules;

import elements.Layer;

import java.util.Optional;

import elements.ApplicationLayerProtocol;
import elements.Direction;
import elements.IProtocol;
import elements.MyPacket;
import elements.NetworkLayerProtocol;
import elements.TransportLayerProtocol;

public abstract class Rule implements IRule {
	
	protected Layer layer;
	protected IProtocol protocol;
	protected Endpoint source;
	protected Endpoint destination;
	protected Direction direction;
	protected Action action;
	
	public Rule(Layer layer, IProtocol protocol, Endpoint source, Endpoint destination, Direction direction, Action action) {
		super();
		this.layer = layer;
		this.protocol = protocol;
		this.source = source;
		this.destination = destination;
		this.direction = direction;
		this.action = action;
	}
	
	public Rule(Layer layer, IProtocol protocol, Endpoint source, Endpoint destination, Direction direction) {
		super();
		this.layer = layer;
		this.protocol = protocol;
		this.source = source;
		this.destination = destination;
		this.direction = direction;
		this.action = null;
	}
	
	public void setAction(Action action) {
		this.action = action;
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
	
	public Action getAction() {
		return action;
	}

	public Layer getLayer() {
		return layer;
	}
	
	/*
	 * evaluate returns true if a packet must be dropped and false if not
	 * */
	
	public final Optional<Action> evaluate(MyPacket packet) {
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
			return Optional.of(this.trigger(packet));
		}
		
		return Optional.empty();
	}

	@Override
	public Action trigger(MyPacket packet) {
		return this.action;
	}

}
