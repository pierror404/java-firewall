package rules;

import elements.Layer;

public abstract class AbstractRule implements IRule {
	
	private Layer layer;

	@Override
	public Layer getLayer() {
		return layer;
	}

}
