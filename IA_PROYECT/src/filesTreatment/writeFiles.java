package filesTreatment;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import types.asig;

public class writeFiles {

	String fileName;
	// ArrayList<asig> AsigSol;

	public writeFiles(String fileName) {
		this.fileName = fileName;
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.fileName, false))) {

		} catch (IOException e) {
			System.err.println("Error writting file: " + e.getMessage());
		}
	}

	public void writeFile(ArrayList<asig> AsigSol) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.fileName, true))) {
			for (int row = 0; row < 9; row++) {
				for (int column = 0; column < 9; column++) {
					for (asig element : AsigSol) {
						if (element.v.row == row && element.v.column == column) {
							writer.write(String.valueOf(element.value));
							break;
						}
					}
				}
			}
			writer.newLine(); // Escribir una nueva línea después de cada sudoku
		} catch (IOException e) {
			System.err.println("Error writting file: " + e.getMessage());
		}
	}
}
