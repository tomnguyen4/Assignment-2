package assignment.birds;

public class DataKey {
	private String birdName;
	private int birdSize;

	// default constructor
	public DataKey() {
		this(null, 0);
	}
        
	public DataKey(String name, int size) {
		birdName = name;
		birdSize = size;
	}

	public String getBirdName() {
		return birdName;
	}

	public int getBirdSize() {
		return birdSize;
	}

	/**
	 * Returns 0 if this DataKey is equal to k, returns -1 if this DataKey is smaller
	 * than k, and it returns 1 otherwise. 
	 */
	public int compareTo(DataKey k) {
		if (this.getBirdSize() == k.getBirdSize()) {
			if (this.birdName.contains(k.getBirdName())) {
				return 0;
			} 
			else if (this.birdName.compareTo(k.getBirdName()) < 0) {
				return -1;
			}
		}
		else if(this.getBirdSize() < k.getBirdSize()){
			return -1;
		}
		return 1;
	}
}
