package algorithms;

import java.util.ArrayList;

import constrains.alldisjoint;
import constrains.equals_to;
import constrains.restric;
import types.asig;
import types.sudokuState;

public class search {

	ArrayList<sudokuState> openList;
	int lastIndex = -1;

	public ArrayList<asig> searchState(sudokuState initial) {

		openList = new ArrayList<>();

		openList.add(initial);
		sudokuState actual = initial;

		while (!openList.isEmpty()) {
			actual = openList.remove(openList.size() - 1);
			if (actual.valueAsig != 0) {
				if (lastIndex >= actual.index) { // Backtracking, si la celda a tratar es una anterior a la que tocaria porque se ha 
					int max = actual.index + (lastIndex - actual.index);
					actual.invertedForwardCheck(); //restaura el forward checking anteriormente aplicado para que los dominios sean consistebtes
					for (int i = actual.index; i < max; i++) {
						actual.assignments.get(i).value = -1; 
						actual.invertedForwardCheck();
					}
				}

				lastIndex = actual.index;
				actual.assignments.get(actual.index - 1).value = actual.valueAsig; //asignamos valor a la celda
				actual.forwardChecking(actual.assignments.get(actual.index - 1)); //hacemos forward checking para reducir futuros valores inconsistentes
				actual.reorderAssignments(actual.index); //reordenamos las celdas restantes para que sean las que menos valores tienen en el dominio
			}
			//System.out.println(actual);
			if (isGoal(actual)) {
				return actual.assignments;
			} else {
				ArrayList<sudokuState> successors = getSuccessors(actual); //sacas todos los estados de sudokus posibles de la siguiente celda a tratar
				//anadirConPrioridad(openList, successors, actual);
				openList.addAll(successors); //añades los sucesores a la lista
			}
		}
		return null;
	}

	public boolean isGoal(sudokuState actual) {
		return actual.numAssignments == 81;
	}

	public ArrayList<sudokuState> getSuccessors(sudokuState actual) {

		ArrayList<sudokuState> successors = new ArrayList<sudokuState>();

		int index = actual.index;
		asig asig = actual.assignments.get(index);
		for (int valor : asig.v.domain) {
			actual.assignments.get(index).value = valor;
			if (satisfyConstrains(actual, actual.assignments.get(index))) { //se añade a la lista de sucesores si cumple las restricciones
				sudokuState sucesor = new sudokuState(actual);
				sucesor.valueAsig = valor;
				sucesor.index = index + 1; //avanzas en la lista 
				sucesor.changeAsigPointer(); //añades uno mas al numero de asignaciones
				successors.add(sucesor);
			}
		}
		
		actual.assignments.get(index).value = -1;
		return successors;
	}

	
	private boolean satisfyConstrains(sudokuState sucesor, asig asig) { 
		for (restric r : sucesor.constrainsOriginal) {
			equals_to i = (equals_to) r;
			if (i.op1.equals(asig)) {
				if (!i.are_Satisfied()) {
					return false;
				} else {
					sucesor.constrainsOriginal.remove(i);
					break;
				}
			}
		}
		for (restric r : sucesor.constrainsAllDisjoint) {
			alldisjoint a = (alldisjoint) r;
			if (a.rowNumber == asig.v.row || a.columnNumber == asig.v.column || a.number3x3 == asig.v.threeXthree) { 
				if (!a.are_Satisfied()) 
					return false;
			}
		}
		return true;
	}

	/*private void anadirConPrioridad(ArrayList<estado_S> openList, ArrayList<estado_S> successors, estado_S actual) {
		if(!successors.isEmpty())
			for(int valorPrioridad : actual.prioridadNumerosDominio) {
				for(estado_S sucesor : successors) {
					if(sucesor.valorAsig == valorPrioridad) {
						openList.add(sucesor);
						successors.remove(sucesor);
						break;
					}
				}
			}
	}*/

}
