package filesTreatment;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import algorithms.*;
import constrains.equals_to;
import constrains.restric;
import types.*;

public class lectureFiles {

	File f;
	Scanner sc;
	variable[][] grid = new variable[9][9];
	ArrayList<restric> constrains;
	ArrayList<asig> assignments;
	ac3 ac3;
	search search;
	writeFiles e;

	public lectureFiles(String file) throws FileNotFoundException {
		f = new File(file);
		sc = new Scanner(f);
		e = new writeFiles("sudokusSolution.txt");
		int counter = 0;
		double totalTime = 0;
		while (sc.hasNextLine()) {
			constrains = new ArrayList<>();
			assignments = new ArrayList<>();
			String line = sc.nextLine();
			for (int i = 0; i < 81; i++) {
				int row = i / 9;
				int column = i % 9;
				Character value = line.charAt(i);
				grid[row][column] = new variable(row, column);
				assignments.add(new asig(grid[row][column])); //crea las asignaciones todas a -1
				if (value != '.') {
					// Si el valor es diferente de ., asignarlo a la variable
					int num = Character.getNumericValue(line.charAt(i));
					constrains.add(new equals_to(assignments.get(i), num));
				}
			}
			
			Long timeIni = System.currentTimeMillis();
			ac3 = new ac3(constrains, grid);
			ac3.algorithmAC3();
			
			/*for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    System.out.println(grid[i][j].dominio);
                }
            }*/
			
			search = new search();
			sudokuState initialState = new sudokuState(grid, constrains, assignments);
			ArrayList<asig> AsigSol = search.searchState(initialState); 
			Long timeFin = System.currentTimeMillis();
			System.out.println("Execution Time: " + (timeFin - timeIni)/1000.00 + " seconds");
			totalTime += (timeFin - timeIni)/1000.00;
            counter++;
            System.out.println("Completed Sudokus: " + counter);
			/*for(asig asig : AsigSol) {
				System.out.println(asig.valor); //VER POR PANTALLA EL SUDOKU
			}*/
			
			//System.out.println(AsigSol.size());
			e.writeFile(AsigSol);
		}

		/*for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				System.out.println(grid[i][j].dominio);
			}
		}*/
		int minutes = (int) totalTime/60;
		int seconds = (int) (totalTime - minutes*60);
        System.out.println("Total time: " + minutes + " minutes" + seconds + " seconds");
        System.out.println("Average time per Sudoku: " + totalTime / counter + " seconds");


		sc.close();
	}
}
