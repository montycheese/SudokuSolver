import java.util.LinkedList;


public class SudokuSolver {
	private SudokuPuzzle puzzle;
	
	public SudokuSolver(SudokuPuzzle puzzle){
		this.puzzle = puzzle;
	}
	
	/**
	 * Applies AC3 algorithm to prune our variable's domain, then runs a backtracking search to find an assignment
	 * 
	 * @return true if a valid assignment is found, false otherwise
	 */
	public boolean solve(){
		applyAC3(this.puzzle);
		this.puzzle.printDomains();
		System.out.printf("\nBacktracking Search...\n\n");
		Variable[] solution = backTrackingSearch(this.puzzle);
		if(solution == null){
			return false;
		}
		else{
			System.out.println("Eureka!");
			return true;
		}
	}
	
	//returns a valid set of variables that satisfy the problem
	public Variable[] backTrackingSearch(SudokuPuzzle puzzle){
		return backTrack(puzzle.variables, puzzle);
	}
	
	//returns a valid set of variables that satisfy the problem
	public Variable[] backTrack(Variable[] assignments, SudokuPuzzle puzzle){
		if(assignments == null) return null;
		if(assignmentComplete(assignments)){
			return assignments;
		}
		Variable v = selectUnassignedVariable(puzzle);
		for(int i=0;i<v.getDomain().length;i++){
			int value = i+1; //offset index by 1
			if(v.checkIfAssignable(value) == true){
				//if constraint doesn't work we need to reset domain, so make copy for bookkeeping
				boolean[] oldDomain = copyDomain(v.getDomain());
				v.setValue(value);
				//if(applyAC3(puzzle) == true){
				if(puzzle.checkConstraint(v)){
					//System.out.printf("row %d, col %d set to %d!\n",v.getRow(),v.getCol(),v.getValue() );
					Variable[] newAssignments = backTrack(assignments, puzzle);
					if(newAssignments != null){
						return newAssignments;
					}
					else {
						//undo changes
						v.setDomain(oldDomain);
					}
				}
				else {
					//undo changes
					v.setDomain(oldDomain);
				}
			}
			//undo changes
			v.setValue(0);
		}
		return null;
	}
	
	/**
	 * Prunes the domains of a given problem's varibles
	 * @param problem
	 * @return true if all domains are valid, false if an empty domain is found.
	 */
	private boolean applyAC3(SudokuPuzzle problem){
		LinkedList<Variable[]> queue = new LinkedList<>();
		Variable[] variables = problem.variables;
		//add every arc to queue. meaning each variable and alll neighbors in rows, cols, etc, but arcs are symmetric
		Variable[] neighbors;
		for(int i=0; i< variables.length; i++){
			if(variables[i].isImmutable()) continue;
			neighbors = problem.getNeighbors(variables[i]);
			for(int j=0; j<neighbors.length;j++){
				queue.add(
						new Variable[] {
								variables[i],
								neighbors[j]
								}
						);
			}
		}
		
		//check arc consistency
		Variable[] arc;
		while(!queue.isEmpty()){
			arc = queue.pop();
			if(revise(problem, arc[0], arc[1]) == true){
				if (arc[0].getDomainSize() == 0){ 
					return false;
				}
				neighbors = problem.getNeighbors(arc[0], arc[1]);
				for(int i=0;i < neighbors.length;i++){
					if(neighbors[i] == null) break;
					//dont mess with the immutable variables
					if(neighbors[i].isImmutable()) continue;
					queue.add(new Variable[] {
							neighbors[i],
							arc[0]
							}
					);
				}
			}
		}
		return true;
	}
	
	//helper method for AC3
	private boolean revise(ConstraintSatProblem problem, Variable v1, Variable v2){
		boolean revised = false;
		boolean[] d1 = v1.getDomain();
		//boolean[] d2 = v2.getDomain();
		
		for(int i=0; i< d1.length; i++){
			//may have to check this this is a very lazy solution
			if(v2.getDomainSize() == 1){
				//hacky
				if(!v2.isAssigned()){
					break;
				}
			//System.out.println("row: " + v2.getRow() + " col: " + v2.getCol());
			v1.restrictDomain(v2.getValue());
			revised = true;
			break;
			//dont return void on restrict domain
			}
		}
		
		return revised;
	}
	
	/**
	 * Selects the first unassigned variable in the set of puzzle's variables
	 * helper method for BTS
	 * 
	 * @param puzzle
	 * @return Variable v or null if all are assigned
	 */
	private Variable selectUnassignedVariable(SudokuPuzzle puzzle){
		for(Variable v: puzzle.variables){
			if(!v.isAssigned()) return v;
		}
		//case should not occur after assignment complete is called
		System.out.println("Error here");
		return null;
	}
	
/**
 * 
 * @param assignments
 * @return true if all variables have been assigned a value
 */
	private boolean assignmentComplete(Variable[] assignments){
		for(Variable v: assignments){
			if(!v.isAssigned()){
				return false;
			}
		}
		return true;
	}
	
	private boolean[] copyDomain(boolean[] original){
		boolean[] copy = new boolean[original.length]; 
		if(original.length != copy.length) System.out.println("error in copy domain");
		for(int i=0;i<original.length;i++){
			copy[i] = original[i];
		}
		return copy;
	}

}
