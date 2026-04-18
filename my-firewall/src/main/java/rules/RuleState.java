package rules;

public class RuleState {
	
	private long hitcount;
	private long firstSeen;
	private long lastSeen;
	
	public RuleState(long hitcount, long firstSeen, long lastSeen) {
		this.hitcount = hitcount;
		this.firstSeen = firstSeen;
		this.lastSeen = lastSeen;
	}
	
	public RuleState() {
		this(0,0,0);
	}

	public long getHitcount() {
		return hitcount;
	}

	public void setHitcount(long hitcount) {
		this.hitcount = hitcount;
	}

	public long getFirstSeen() {
		return firstSeen;
	}

	public void setFirstSeen(long firstSeen) {
		this.firstSeen = firstSeen;
	}

	public long getLastSeen() {
		return lastSeen;
	}

	public void setLastSeen(long lastSeen) {
		this.lastSeen = lastSeen;
	}
	
	public void increaseHitcount() {
		this.hitcount++;
	}
}
