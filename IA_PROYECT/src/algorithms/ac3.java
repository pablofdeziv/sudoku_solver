package algorithms;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import constrains.equals_to;
import constrains.restric;
import types.consistencyArc;
import types.variable;

public class ac3 {

	ArrayList<restric> constrains;
	HashSet<consistencyArc> ArcsSet;
	HashSet<consistencyArc> copyArcsSet;
	variable[][] variables;
	boolean cambio;

	public ac3(ArrayList<restric> constrains, variable[][] variables) {
		this.constrains = constrains;
		this.variables = variables;
		arcsInitialization();
	}

	public void algorithmAC3() {
		
		domainsReduction(); //aplicar ac3 para que los dominios predefinidos se reduzcan
							//es decir asigna los numeros que ya estaban
		Iterator<consistencyArc> Iterator = ArcsSet.iterator(); //creamos iterador
		while (Iterator.hasNext()) { ///recorremos los arcos
			//System.out.println(variables[0][2].dominio);
			consistencyArc arc = Iterator.next();
			ArcsSet.remove(arc);
			if (!arc.checkConsistency()) { //comprobamos la consistencia de los arcoss tras hacer los cambios y eliminamos los numeros no validos en los dominios
				addExtended(arc.varDistinguished);
			}
			Iterator = ArcsSet.iterator();
			//System.out.println("aqqqq");
			//System.out.println(ArcsSet.size());
		}
		
	}
	
	public void addExtended(variable vDist) { // encarga de reinsertar ciertos arcos (de tipo consistencyArc) en el conjunto ArcsSet cuando la consistencia de un arco se rompe.
		for(consistencyArc arc : copyArcsSet) {
			if(arc.varNotDistinguished.equals(vDist)) {
				ArcsSet.add(arc); //cuando se modifica un dominio, se reevaluan todos os arcos en la que esa variable no sea distinguida
			}
		}
	}

	private void arcsInitialization() { //relaciona todas las celdas en sus 3x3, filas y columnas
		
		ArcsSet = new HashSet<consistencyArc>();

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				check3x3(variables[i][j]);
				checkRows(variables[i][j]);
				checkColumns(variables[i][j]);
			}
		}
		copyArcsSet = new HashSet<consistencyArc>(ArcsSet);
	}

	public void check3x3(variable v) {
		int initialRow = (v.row / 3) * 3; // fila inicial del cuadrado
		int initialColumn = (v.column / 3) * 3; // Columna inicial del cuadrado

		for (int i = initialRow; i < initialRow + 3; i++) {
			for (int j = initialColumn; j < initialColumn + 3; j++) {
				if (i != v.row || j != v.column) //que no sea ni su misma fila ni su misma columna, es decir, no se comprueba a si mismo
					ArcsSet.add(new consistencyArc(v, variables[i][j])); //revisa
			}
		}
	}

	public void checkRows(variable v) {
		for (int i = 0; i < 9; i++) {
			if (i != v.column)
				ArcsSet.add(new consistencyArc(v, variables[v.row][i]));
		}
	}

	public void checkColumns(variable v) {
		for (int i = 0; i < 9; i++) {
			if (i != v.row)
				ArcsSet.add(new consistencyArc(v, variables[i][v.column]));
		}
	}

	private void domainsReduction() {
		for (restric r : constrains) { //recorremos las restriccionas previas
			equals_to i = (equals_to) r;
			variable v = i.op1.v;
			int value = i.value;
			while (!v.domain.isEmpty()) {
				v.domain.remove(0);
			}
			v.domain.add(value); //añado el value de dominio restringido
		}
	}

}
