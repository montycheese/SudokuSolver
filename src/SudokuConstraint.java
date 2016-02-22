
public class SudokuConstraint extends Constraint{
	public static SudokuConstraint _instance = null;
	
	public boolean check(Variable v, Variable[][] board){
		//check row
		int row = v.getRow();
		for(int i=0; i<board[row].length;i++){
			if(v != board[row][i] && board[row][i].getValue() == v.getValue()){
				return false;
			}
		}
		//check col
		int col = v.getCol();
		for(int i=0; i < board.length;i++){
			if(v != board[i][col] && board[i][col].getValue() == v.getValue()){
				return false;
			}
		}
		//check box
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
				if(v != board[row][col] && board[row][col].getValue() == v.getValue() ){
					return false;
				}
			}
		}
		
		return true;
	}
	
	//only need one instance of this class
	public static SudokuConstraint getInstance(){
		if(_instance == null) {
			_instance = new SudokuConstraint();
		}
		return _instance;
	}

}
