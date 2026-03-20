package elements;

public enum Layer {
	NETWORK(3),
	TRANSPORT(4),
	APPLICATION(6);
	
	private int layer;
	
	Layer(int layer){
		this.layer = layer;
	}
	
	public int getLayer() {
		return layer;
	}
}
