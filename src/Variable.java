
public enum Variable {
	A0 (0,0,0,0),
	A1 (0,1,0,0),
	A2 (0,2,0,0),
	A3 (0,3,1,0),
	A4 (0,4,1,0),
	A5 (0,5,1,0),
	A6 (0,6,2,0),
	A7 (0,7,2,0),
	A8 (0,8,2,0),
	B0 (1,0,0,0),
	B1 (1,1,0,0),
	B2 (1,2,0,0),
	B3 (1,3,1,0),
	B4 (1,4,1,0),
	B5 (1,5,1,0),
	B6 (1,6,2,0),
	B7 (1,7,2,0),
	B8 (1,8,2,0),
	C0 (2,0,0,0),
	C1 (2,1,0,0),
	C2 (2,2,0,0),
	C3 (2,3,1,0),
	C4 (2,4,1,0),
	C5 (2,5,1,0),
	C6 (2,6,2,0),
	C7 (2,7,2,0),
	C8 (2,8,2,0),
	D0 (3,0,3,0),
	D1 (3,1,3,0),
	D2 (3,2,3,0),
	D3 (3,3,4,0),
	D4 (3,4,4,0),
	D5 (3,5,4,0),
	D6 (3,6,5,0),
	D7 (3,7,5,0),
	D8 (3,8,5,0),
	E0 (4,0,3,0),
	E1 (4,1,3,0),
	E2 (4,2,3,0),
	E3 (4,3,4,0),
	E4 (4,4,4,0),
	E5 (4,5,4,0),
	E6 (4,6,5,0),
	E7 (4,7,5,0),
	E8 (4,8,5,0),
	F0 (5,0,3,0),
	F1 (5,1,3,0),
	F2 (5,2,3,0),
	F3 (5,3,4,0),
	F4 (5,4,4,0),
	F5 (5,5,4,0),
	F6 (5,6,5,0),
	F7 (5,7,5,0),
	F8 (5,8,5,0),
	G0 (6,0,6,0),
	G1 (6,1,6,0),
	G2 (6,2,6,0),
	G3 (6,3,7,0),
	G4 (6,4,7,0),
	G5 (6,5,7,0),
	G6 (6,6,8,0),
	G7 (6,7,8,0),
	G8 (6,8,8,0),
	H0 (7,0,6,0),
	H1 (7,1,6,0),
	H2 (7,2,6,0),
	H3 (7,3,7,0),
	H4 (7,4,7,0),
	H5 (7,5,7,0),
	H6 (7,6,8,0),
	H7 (7,7,8,0),
	H8 (7,8,8,0),
	I0 (8,0,6,0),
	I1 (8,1,6,0),
	I2 (8,2,6,0),
	I3 (8,3,7,0),
	I4 (8,4,7,0),
	I5 (8,5,7,0),
	I6 (8,6,8,0),
	I7 (8,7,8,0),
	I8 (8,8,8,0);
	
	private int row;
	private int col;
	private int value;
	private boolean assigned = false;
	//immutable values are the ones that are encoded on the board in the beginning.
	private boolean immutable = false;
	//box corresponds to which 3*3 square of the puzzle the var belongs to
	private int box;
	private boolean[] domain = new boolean[] {true,true,true,true,true,true,true,true,true};
	
	private Variable(int row, int col, int box, int defaultValue){
		this.row = row;
		this.col = col;
		this.box = box;
		this.value = defaultValue;
	}
	
	public boolean[] getDomain(){
		return this.domain;
	}
	public int getBoxNumber(){
		return this.box;
	}
	public int getRow(){
		return this.row;
	}
	public int getCol(){
		return this.col;
	}
	public boolean isAssigned(){
		return this.assigned;
	}
	public boolean isImmutable(){
		return this.immutable;
	}
	
	public boolean checkIfAssignable(int value){
		return this.domain[value-1];
	}
	
	public int getValue(){
		return this.value;
	}
	//returns num of available values in domain
	public int getDomainSize(){
		int count = 0;
		for(int i=0;i<this.domain.length; i++){
			if(this.domain[i] == true) count++;
		}
		return count;
	}
	
	public void setImmutable(){
		this.immutable = true;
	}
	
	/**
	 * 
	 * @param value value to be set on variable
	 * @return true or false depending on whether the value was set
	 */
	public boolean setValue(int value){
		if(value > 9 || value < 0) return false;
		else if(value == 0){
			this.value = value;
			//unrestrictDomain();
			this.assigned = false;
			return true;
		}
		if(!this.domain[value-1]){
			return false;
		}
		else{
			this.value = value;
			restrictDomain();
			this.assigned = true;
			return true;
		}
	}
	
	public void restrictDomain(){
		//remove all values from domain except the one that was assigned
		for(int i=0;i<this.domain.length;i++){
			domain[i] = false;
		}
		domain[this.value - 1] = true;
	}
	public void restrictDomain(int... values){
		for(int i=0;i<values.length;i++){
			this.domain[values[i]-1] = false;
		}
	}
	
	public void unrestrictDomain(){
		//add all values from domain
		for(int i=0;i<this.domain.length;i++){
			domain[i] = true;
		}
	}
	public void unrestrictDomain(int... values){
		for(int i=0;i<values.length;i++){
			this.domain[values[i]-1] = true;
		}
	}
	public void setDomain(boolean[] domain){
		for(int i=0;i<this.domain.length;i++){
			this.domain[i] = domain[i];
		}
	}
}
