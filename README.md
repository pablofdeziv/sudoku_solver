# Sudoku Solver in Java🧠

This project solves sudoku puzzles using artificial intelligence techniques like constraint satisfaction (AC-3 algorithm) and backtracking search. Developed for an academic AI course.

## 🚀 Features

- Constraint Satisfaction Problem (CSP) model
- AC-3 (Arc Consistency) algorithm for constraint propagation
- Custom constraints:
  - AllDifferent (rows, columns, subgrids)
  - EqualsTo
- Input/output from plain text files
- Efficient solving of multiple sudokus in batch mode

## 🛠️ Technologies

- Java
- Eclipse IDE (project structure)
- Object-oriented design (custom constraint engine)

## 📂 File Structure

- `sudokus.txt` – input puzzles
- `sudokusSolution.txt` – solutions generated
- `src/` – source code (Java packages)
- `bin/` – compiled classes (if included)

## ⚙️ How to Run

1. Open the project in Eclipse or any Java IDE.
2. Compile all files in `/src`.
3. Run the main class
4. Check `sudokusSolution.txt` for the output.

> 📥 Note: Each line in `sudokus.txt` contains a single puzzle with 81 digits.
> Dots (`.`) represent empty cells, and the puzzle is read row by row.

## 📝 License

This project is released under the MIT License.
