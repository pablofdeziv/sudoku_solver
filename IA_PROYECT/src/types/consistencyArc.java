package types;

public class consistencyArc {

	public variable varDistinguished;
	public variable varNotDistinguished;
	
	public consistencyArc(variable varDistinguished, variable varNotDistinguished) {
		this.varDistinguished = varDistinguished;
		this.varNotDistinguished = varNotDistinguished;
	}
	
	public boolean checkConsistency(){
		if(varNotDistinguished.domain.size() == 1) {
			int value = varNotDistinguished.domain.get(0);
			for(int x : varDistinguished.domain) {
				if(value == x) {
					varDistinguished.domain.remove((Object)value);
					return false;
				}
			}
			return true;
		}
		else
			return true;
		
		
	}
	

	
	
}
