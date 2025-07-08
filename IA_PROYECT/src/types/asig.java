package types;

public class asig implements Comparable<asig>{
	
	public variable v;
	public int value;
	
	public asig(variable v) {
		this.v = v;
		this.value = -1;
	}

	public asig(asig asig) {
		this.v = asig.v;
		this.value = asig.value;
	}

	@Override
	public int compareTo(asig o) {
		return Integer.compare(this.v.domain.size(), o.v.domain.size());
	}
}
