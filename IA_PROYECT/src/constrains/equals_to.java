package constrains;

import types.asig;

public class equals_to implements restric{

	public asig op1;
	//asig op2;
	public int value;
	
	public equals_to(asig asig, int value) {
		this.op1 = asig;
		this.value = value;
	}
	
	@Override
	public boolean are_Satisfied() {
		if(op1.value == value)
			return true;
		else
			return false;
	}

}
