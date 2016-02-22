
public class Driver {

	public static void main(String[] args){
		//Run this to solve all three encodings.
		int[][][] encodings = new int[][][] {
				SudokuEncoding.ENCODING_1,
				SudokuEncoding.ENCODING_2,
				SudokuEncoding.ENCODING_3
		};
		for(int i=0; i < encodings.length; i++){
			System.out.printf("\n**********Encoding %d***********\n\n", i+1);
			
			SudokuPuzzle sp = new SudokuPuzzle(encodings[i]);
			
			System.out.println("Original board");
			sp.printBoard();
			System.out.println("\nVariable domains after applying AC3 Algorithm");
			
			SudokuSolver solver = new SudokuSolver(sp);
			
			if(solver.solve()){
				sp.printBoard();
			}
			else{
				System.out.println("No solution found for encoding " + i+1);
			}
		}
		
		/*Uncomment this to run a custom encoding. the input must be an NxN 2D int array, replace INPUT with your NxN array.
		int[][] input = INPUT;
		System.out.printf("\n**********Encoding %d***********\n\n", i+1);
		SudokuPuzzle sp = new SudokuPuzzle(INPUT);
		System.out.println("Original board");
		sp.printBoard();
		System.out.println("\nVariable domains after applying AC3 Algorithm");
		SudokuSolver solver = new SudokuSolver(sp);
		if(solver.solve()){
			sp.printBoard();
		}
		else{
			System.out.println("No solution found for encoding " + i+1);
		}*/
	}
}
