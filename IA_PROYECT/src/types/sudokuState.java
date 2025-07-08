package types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import constrains.alldisjoint;
import constrains.restric;

public class sudokuState {

	public variable[][] variables;
	public ArrayList<restric> constrainsAllDisjoint = new ArrayList<restric>();
	public ArrayList<restric> constrainsOriginal;
	public ArrayList<asig> assignments;
	public ArrayList<Integer> domainNumbersPriority = new ArrayList<Integer>();
	public int index;
	public int numAssignments;
	public int valueAsig;
	public int numberLeftOG;
	private ArrayList<forwardCheckState> forwardCheckList = new ArrayList<forwardCheckState>();

	public sudokuState(variable[][] variables, ArrayList<restric> constrains, ArrayList<asig> assignments) {
		this.variables = variables;
		this.constrainsOriginal = constrains;
		this.numberLeftOG = constrainsOriginal.size();
		this.assignments = assignments;
		Collections.sort(assignments);
		this.index = 0;
		this.numAssignments = 0;
		this.valueAsig = 0;
		initialDomainsPriorities(variables); //establece una lista de prioridades para los valores en los dominios de las variables del Sudoku, priorizando los valores mas frecuentes en todos los dominios. 
		iniAllDisjoint();
	}

	public sudokuState(sudokuState original) {
		this.variables = original.variables;
		this.assignments = original.assignments;
		this.numAssignments = original.numAssignments;
		this.constrainsAllDisjoint = original.constrainsAllDisjoint;
		this.constrainsOriginal = original.constrainsOriginal;
		this.forwardCheckList = original.forwardCheckList;
	}

	public void forwardChecking(asig asig) {
		
		int row = asig.v.row;
		int column = asig.v.column;
		int value = asig.value;
		
		//creamos array para recoger las variables que tocamos a la hora de hacer FC
		ArrayList<variable> affectedVariables = new ArrayList<variable>();

		// FC filas y columnas que tengan que ver con la celda a la que le acabamos de asignar un numero
		for (int x = 0; x < 9; x++) {
			if (this.variables[row][x].domain.remove((Object) value))
				affectedVariables.add(variables[row][x]);
			if (this.variables[x][column].domain.remove((Object) value))
				affectedVariables.add(variables[x][column]);

		}
		// FC 3x3
		int initialRow = (row / 3) * 3;
		int initialColumn = (column / 3) * 3;

		for (int f = initialRow; f < initialRow + 3; f++) {
			for (int c = initialColumn; c < initialColumn + 3; c++) {
				if (this.variables[f][c].domain.remove((Object) value))
					affectedVariables.add(variables[f][c]);

			}
		}

		forwardCheckList.add(new forwardCheckState(value, affectedVariables));
	}
	
	public void invertedForwardCheck() {
		forwardCheckState last = forwardCheckList.remove(forwardCheckList.size() - 1);
		for (variable var : last.affectedVariables) {
			var.domain.add(last.value);
		}
	}
	

	/*private void iniIgual_Que() {

		for (restric r : constrainsOriginal) {
			igual_que i = (igual_que) r;
			variable var = i.op1.v;
			for (asig asig : assignments) {
				if (asig.v.equals(var)) {
					i.op1 = asig;
				}
			}
		}
	}*/

	private void initialDomainsPriorities(variable[][] variables) {
		for (int row = 0; row < 9; row++) {
			for (int column = 0; column < 9; column++) {
				for (int value : variables[row][column].domain) {
					domainNumbersPriority.add(value);
				}
			}
		}
		
		HashMap<Integer, Integer> counting = new HashMap<>();

        // Recorremos lista y hacemos un countingp de los valuees que aparecen
        for (Integer value : domainNumbersPriority) {
            counting.put(value, counting.getOrDefault(value, 0) + 1);
        }

        // Ordenamos el mapa de counting por el value del counting
        TreeMap<Integer, ArrayList<Integer>> orderedCounting = new TreeMap<>();

        // Iteramos sobre el mapa de counting y llenamos el TreeMap de counting ordenado
        for (Map.Entry<Integer, Integer> entry : counting.entrySet()) {
            int value = entry.getKey();
            int quantity = entry.getValue();
            if (!orderedCounting.containsKey(quantity)) {
                orderedCounting.put(quantity, new ArrayList<>());
            }
            orderedCounting.get(quantity).add(value);
        }
        
        domainNumbersPriority.clear();
        
        // iteramos por el counting ordenado y lo colocamos en la lista de proridades (menor a mayor)
        for (Map.Entry<Integer, ArrayList<Integer>> entry : orderedCounting.entrySet()) {
        	for(int value : entry.getValue())
        		domainNumbersPriority.add(value);
        }
		
        Collections.reverse(domainNumbersPriority);
		
	}

	public void iniAllDisjoint() {
		
		ArrayList<asig> row0 = new ArrayList<asig>();
		ArrayList<asig> row1 = new ArrayList<asig>();
		ArrayList<asig> row2 = new ArrayList<asig>();
		ArrayList<asig> row3 = new ArrayList<asig>();
		ArrayList<asig> row4 = new ArrayList<asig>();
		ArrayList<asig> row5 = new ArrayList<asig>();
		ArrayList<asig> row6 = new ArrayList<asig>();
		ArrayList<asig> row7 = new ArrayList<asig>();
		ArrayList<asig> row8 = new ArrayList<asig>();
		
		ArrayList<asig> column0 = new ArrayList<asig>();
		ArrayList<asig> column1 = new ArrayList<asig>();
		ArrayList<asig> column2 = new ArrayList<asig>();
		ArrayList<asig> column3 = new ArrayList<asig>();
		ArrayList<asig> column4 = new ArrayList<asig>();
		ArrayList<asig> column5 = new ArrayList<asig>();
		ArrayList<asig> column6 = new ArrayList<asig>();
		ArrayList<asig> column7 = new ArrayList<asig>();
		ArrayList<asig> column8 = new ArrayList<asig>();
		
		ArrayList<asig> Threex3_0 = new ArrayList<asig>();
		ArrayList<asig> Threex3_1 = new ArrayList<asig>();
		ArrayList<asig> Threex3_2 = new ArrayList<asig>();
		ArrayList<asig> Threex3_3 = new ArrayList<asig>();
		ArrayList<asig> Threex3_4 = new ArrayList<asig>();
		ArrayList<asig> Threex3_5 = new ArrayList<asig>();
		ArrayList<asig> Threex3_6 = new ArrayList<asig>();
		ArrayList<asig> Threex3_7 = new ArrayList<asig>();
		ArrayList<asig> Threex3_8 = new ArrayList<asig>();

		for (int i = 0; i < 81; i++) {

			int row = assignments.get(i).v.row;
			int column = assignments.get(i).v.column;
			int threeXthree = assignments.get(i).v.threeXthree;

			switch (row) {
			case 0:
				row0.add(assignments.get(i));
				break;
			case 1:
				row1.add(assignments.get(i));
				break;
			case 2:
				row2.add(assignments.get(i));
				break;
			case 3:
				row3.add(assignments.get(i));
				break;
			case 4:
				row4.add(assignments.get(i));
				break;
			case 5:
				row5.add(assignments.get(i));
				break;
			case 6:
				row6.add(assignments.get(i));
				break;
			case 7:
				row7.add(assignments.get(i));
				break;
			case 8:
				row8.add(assignments.get(i));
				break;
			}
			switch (column) {
			case 0:
				column0.add(assignments.get(i));
				break;
			case 1:
				column1.add(assignments.get(i));
				break;
			case 2:
				column2.add(assignments.get(i));
				break;
			case 3:
				column3.add(assignments.get(i));
				break;
			case 4:
				column4.add(assignments.get(i));
				break;
			case 5:
				column5.add(assignments.get(i));
				break;
			case 6:
				column6.add(assignments.get(i));
				break;
			case 7:
				column7.add(assignments.get(i));
				break;
			case 8:
				column8.add(assignments.get(i));
				break;
			}
			switch (threeXthree) {
			case 0:
				Threex3_0.add(assignments.get(i));
				break;
			case 1:
				Threex3_1.add(assignments.get(i));
				break;
			case 2:
				Threex3_2.add(assignments.get(i));
				break;
			case 3:
				Threex3_3.add(assignments.get(i));
				break;
			case 4:
				Threex3_4.add(assignments.get(i));
				break;
			case 5:
				Threex3_5.add(assignments.get(i));
				break;
			case 6:
				Threex3_6.add(assignments.get(i));
				break;
			case 7:
				Threex3_7.add(assignments.get(i));
				break;
			case 8:
				Threex3_8.add(assignments.get(i));
				break;
			}
		}

		alldisjoint row_0 = new alldisjoint(row0, 0, -1, -1);
		constrainsAllDisjoint.add(row_0);
		alldisjoint row_1 = new alldisjoint(row1, 1, -1, -1);
		constrainsAllDisjoint.add(row_1);
		alldisjoint row_2 = new alldisjoint(row2, 2, -1, -1);
		constrainsAllDisjoint.add(row_2);
		alldisjoint row_3 = new alldisjoint(row3, 3, -1, -1);
		constrainsAllDisjoint.add(row_3);
		alldisjoint row_4 = new alldisjoint(row4, 4, -1, -1);
		constrainsAllDisjoint.add(row_4);
		alldisjoint row_5 = new alldisjoint(row5, 5, -1, -1);
		constrainsAllDisjoint.add(row_5);
		alldisjoint row_6 = new alldisjoint(row6, 6, -1, -1);
		constrainsAllDisjoint.add(row_6);
		alldisjoint row_7 = new alldisjoint(row7, 7, -1, -1);
		constrainsAllDisjoint.add(row_7);
		alldisjoint row_8 = new alldisjoint(row8, 8, -1, -1);
		constrainsAllDisjoint.add(row_8);

		alldisjoint column_0 = new alldisjoint(column0, -1, 0, -1);
		constrainsAllDisjoint.add(column_0);
		alldisjoint column_1 = new alldisjoint(column1, -1, 1, -1);
		constrainsAllDisjoint.add(column_1);
		alldisjoint column_2 = new alldisjoint(column2, -1, 2, -1);
		constrainsAllDisjoint.add(column_2);
		alldisjoint column_3 = new alldisjoint(column3, -1, 3, -1);
		constrainsAllDisjoint.add(column_3);
		alldisjoint column_4 = new alldisjoint(column4, -1, 4, -1);
		constrainsAllDisjoint.add(column_4);
		alldisjoint column_5 = new alldisjoint(column5, -1, 5, -1);
		constrainsAllDisjoint.add(column_5);
		alldisjoint column_6 = new alldisjoint(column6, -1, 6, -1);
		constrainsAllDisjoint.add(column_6);
		alldisjoint column_7 = new alldisjoint(column7, -1, 7, -1);
		constrainsAllDisjoint.add(column_7);
		alldisjoint column_8 = new alldisjoint(column8, -1, 8, -1);
		constrainsAllDisjoint.add(column_8);

		alldisjoint Threex30 = new alldisjoint(Threex3_0, -1, -1, 0);
		constrainsAllDisjoint.add(Threex30);
		alldisjoint Threex31 = new alldisjoint(Threex3_1, -1, -1, 1);
		constrainsAllDisjoint.add(Threex31);
		alldisjoint Threex32 = new alldisjoint(Threex3_2, -1, -1, 2);
		constrainsAllDisjoint.add(Threex32);
		alldisjoint Threex33 = new alldisjoint(Threex3_3, -1, -1, 3);
		constrainsAllDisjoint.add(Threex33);
		alldisjoint Threex34 = new alldisjoint(Threex3_4, -1, -1, 4);
		constrainsAllDisjoint.add(Threex34);
		alldisjoint Threex35 = new alldisjoint(Threex3_5, -1, -1, 5);
		constrainsAllDisjoint.add(Threex35);
		alldisjoint Threex36 = new alldisjoint(Threex3_6, -1, -1, 6);
		constrainsAllDisjoint.add(Threex36);
		alldisjoint Threex37 = new alldisjoint(Threex3_7, -1, -1, 7);
		constrainsAllDisjoint.add(Threex37);
		alldisjoint Threex38 = new alldisjoint(Threex3_8, -1, -1, 8);
		constrainsAllDisjoint.add(Threex38);
	}

	public void changeAsigPointer() {
		this.numAssignments++;
	}
	
	public void reorderAssignments(int index) {
		
		ArrayList<asig> subList = new ArrayList<asig>(assignments.subList(index, assignments.size()));
		Collections.sort(subList);
		
		for(int i=0; i<subList.size(); i++) {
			assignments.set(index + i, subList.get(i));
			
		}
	}
	
	public String toString() {
		String sudoku = "";
		for(asig asig : assignments) {
        	sudoku += asig.value;
        }
		return sudoku;
	}

	
	//clase unica para el FC

	private class forwardCheckState {
		
		int value;
		ArrayList<variable> affectedVariables = new ArrayList<variable>();

		public forwardCheckState(int x, ArrayList<variable> affectedVariables) {
			this.value = x;
			this.affectedVariables = affectedVariables;
		}
		
	}

}
