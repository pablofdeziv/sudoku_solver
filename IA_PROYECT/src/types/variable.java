package types;

import java.util.ArrayList;

public class variable {
	
	public ArrayList<Integer> domain = new ArrayList<Integer>();
	public int column;
	public int row;
	public int threeXthree;
	
	public variable(int row, int column) {
		this.row = row;
		this.column = column;
		for(int i=1; i<=9; i++) {
			domain.add(i);
		}
		inicialize3x3();
	}
	
	public variable(variable original) {
		this.row = original.row;
		this.column = original.column;
		this.domain = original.domain;
		this.threeXthree = original.threeXthree;
	}

	private void inicialize3x3() {
		if(row < 3 && column < 3)
			threeXthree = 0;
		else if(row < 3 && column < 6)
			threeXthree = 1;
		else if(row < 3 && column < 9)
			threeXthree = 2;
		else if(row < 6 && column < 3)
			threeXthree = 3;
		else if(row < 6 && column < 6)
			threeXthree = 4;
		else if(row < 6 && column < 9)
			threeXthree = 5;
		else if(row < 9 && column < 3)
			threeXthree = 6;
		else if(row < 9 && column < 6)
			threeXthree = 7;
		else if(row < 9 && column < 9)
			threeXthree = 8;
	}

	/*public variable(int id, int valor) {
		this.id = Integer.toString(id);
		domain = new ArrayList<Integer>();
		domain.add(valor);
	}*/
	
	
	
}
