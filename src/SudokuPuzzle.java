import java.util.Arrays;

public class SudokuPuzzle extends ConstraintSatProblem {
	private Variable[][] board;
	private SudokuConstraint constraint;
	protected Variable[] variables;
	
	public SudokuPuzzle(int[][] encoding){
		this.board = new Variable[encoding.length][encoding.length];
		this.variables = Variable.values();
		this.constraint = SudokuConstraint.getInstance();
		setInitialState(encoding);
	}
	
	public boolean checkConstraint(Variable v){
		return this.constraint.check(v, this.board);
	}
	public SudokuConstraint getConstraint(){
		return this.constraint;
	}
	
	public Variable[][] getBoard(){
		return this.board;
	}
	/**
	 * Check whether a variable is already contained within an array, return true if a match is found, false if none or 
	 * a null is found
	 * @param variables
	 * @param variable
	 * @return
	 */
	private boolean containsDuplicate(Variable[] variables, Variable variable){
		for(Variable v: variables){
			if (v == null) return false;
			if(v == variable) return true;	
		}
		return false;
	}
	
	/**
	 * Returns all neighbors of a variable, neighbors include all variables on the same row, col, and box
	 * @param v
	 * @return
	 */
	public Variable[] getNeighbors(Variable v){
		if(v == null) return null;
		Variable[] neighbors = new Variable[20]; //20 total neighbors on sudoku board
		int count = 0;
		//System.out.println("var row: " + v.getRow() + "col: "+v.getCol());
		int row = v.getRow();
		for(int i=0; i<board[row].length;i++){
			if(v != board[row][i]){
				neighbors[count] = board[row][i];
				count++;
				//System.out.println("adding neighbor: " + neighbors[count-1].getValue());
			}
		}
		int col = v.getCol();
		for(int i=0; i < board.length;i++){
			if(v !=board[i][col] && !containsDuplicate(neighbors, board[i][col])){
				//add check to make sure not already in neighbors array, there may be overlap
				neighbors[count] = board[i][col];
				//System.out.println("adding neighbor: " + neighbors[count].getValue());
				count++;
			}
		}
		int boxNum = v.getBoxNumber();
		int i,j;
		if(boxNum < 3)
			i=0;
		else if(boxNum > 5)
			i=6;
		else
			i=3;
		if (boxNum == 0 || boxNum == 3 || boxNum == 6)
			j = 0;
		else if(boxNum == 1 || boxNum == 4 || boxNum == 7)
			j=3;
		else
			j=6;
		
		for(row = i;row<i+3;row++){
			for(col = j;col<j+3;col++){
				if( v!= board[row][col] && !containsDuplicate(neighbors, board[row][col])){
					neighbors[count] = board[row][col];
					//System.out.println("adding neighbor: " + neighbors[count].getValue());
					count++;
				}
			}
		}
		
		return neighbors;
		
	}
	/**
	 *  Returns all neighbors of a variable, neighbors include all variables on the same row, col, and box
	 * 
	 * @param v Our variable that we are getting the neighbors for
	 * @param ignore, A variable that we should ignore, i.e. we have already checked its arc consistency with v
	 * @return neighbors, all neighbors of our variable v, except the ignore. Should be 19.
	 */
	public Variable[] getNeighbors(Variable v, Variable ignore){
		if(v == null) return null;
		Variable[] neighbors = new Variable[20]; //20 total neighbors on sudoku board
		int count = 0;
		int row = v.getRow();
		for(int i=0; i<board[row].length;i++){
			if(v != board[row][i] && ignore != board[row][i]){
				neighbors[count] = board[row][i];
				count++;
			}
		}
		int col = v.getCol();
		for(int i=0; i < board.length;i++){
			if(v != board[i][col] && ignore != board[i][col] && !containsDuplicate(neighbors, board[i][col])){
				//add check to make sure not already in neighbors array, there may be overlap
				neighbors[count] = board[i][col];
				count++;
			}
		}
		int boxNum = v.getBoxNumber();
		int i,j;
		if(boxNum < 3)
			i=0;
		else if(boxNum > 5)
			i=6;
		else
			i=3;
		if (boxNum == 0 || boxNum == 3 || boxNum == 6)
			j = 0;
		else if(boxNum == 1 || boxNum == 4 || boxNum == 7)
			j=3;
		else
			j=6;
		
		for(row = i;row<i+3;row++){
			for(col = j;col<j+3;col++){
				if(v != board[row][col] && ignore != board[row][col] && !containsDuplicate(neighbors, board[row][col])){
					neighbors[count] = board[row][col];
					count++;
				}
			}
		}
		
		return neighbors;
		
	}
	
	private void setInitialState(int[][] encoding){
		int count = 0;
		Variable[] boardVariables = this.variables;
		for(int i=0;i< encoding.length; i++){
			for(int j=0; j< encoding[i].length; j++,count++){
				this.board[i][j] = boardVariables[count];
				this.board[i][j].setValue(encoding[i][j]);
				//make the starting numbers immutable
				if(encoding[i][j] != 0){
					this.board[i][j].setImmutable();
				}
			}
		}
	}
	
	public void printBoard(){
		int count2= 1;
		for(int i=0;i< this.board.length; i++,count2++){
			int count=1;
			for(int j=0; j< this.board[i].length; j++,count++){
				System.out.printf("%d",board[i][j].getValue());
				if(count % 3==0 && count < 9){
					System.out.printf(" | ");
				}
			}
			System.out.println("");
			if(count2 % 3 ==0 && count2 < 9){
				System.out.println("---   ---   ---");
			}
		}
	}
	
	public void printDomains(){
		for(int i=0;i< this.board.length; i++){
			int count=1;
			for(int j=0; j< this.board[i].length; j++){
				System.out.printf("row:%d, col:%d -> ", i,j);
				System.out.println(Arrays.toString(board[i][j].getDomain()));
				//System.out.println("");
			}
			System.out.println("");
		
		}
	}
	
	
	
	

}
