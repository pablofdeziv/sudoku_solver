package constrains;

import java.util.ArrayList;

import types.asig;

public class alldisjoint implements restric {
	
	ArrayList<asig> assignments;
	public int rowNumber;
	public int columnNumber;
	public int number3x3;

	public alldisjoint(ArrayList<asig> assignments, int tTuplaPunteo, int tColumna, int t3x3) {
		this.assignments = assignments;
		this.rowNumber = tTuplaPunteo;
		this.columnNumber = tColumna;
		this.number3x3 = t3x3;
	}



	@Override
	public boolean are_Satisfied() {
		for(asig asig1 : assignments) {
			if(asig1.value != -1) {
				for(asig asig2: assignments) {
					if(asig1.value == asig2.value && !asig1.equals(asig2)) {
						return false;
					}
				}
			}
		}
		return true;
	}

}
