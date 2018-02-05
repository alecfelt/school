//Alec Felt  1430374  allfelt@ucsc.edu
//pa3  Matrix.java


public class Matrix{

	//private inner class Entry

	private class Entry{

		//fileds

		int column;
		double value;
		
		//private class constructor

		Entry(int c, double v){
			column = c;
			value = v;
		}

		//equals method that overrides built in method
		//compares column and value		

		public boolean equals(Entry E){
			if((E.column == column) && (E.value == value)){
				return true;
			}else{
				return false;
			}
		}

		//toString method that overrides built in method

		public String toString(){
			String S = "";
			String col = Integer.toString(column);
			String val = Double.toString(value);
			if(value != 0){
				S += ("(" + col + ", " + val + ") ");
			}
			return S; 
		}

		//getCol returns the column value of the Entry
		//helper function		

		int getCol() {
			return column;
		}

		//getval returns the value of the Entry
		//helper access function		

		double getVal() {
			return value;
		}

		//changeVal changes the value of that given entry
		//helper manipulation method		

		void changeVal(double val){
			value = val;
		}
	
	}

	//Constructor

	Matrix(int n){
		size = n;//sets both fields
		matrix = new List[n];
		for(int i = 0; i < n; i++){//loads up each matrix row with an empty list
			matrix[i] = new List();
		}
	}

	//Matrix fields

	List matrix[];
	int size;

//Helper Functions -------------------------------------------------------------------

	double dotProduct(List L1, List L2){
		
		double val = 0;//value to be returned

		if(L1.length() == 0 || L2.length() == 0){//length check
			;
		}else{
			int L1max = L1.length();//count variables
			int L2max = L2.length();
			int L1count = 0, L2count = 0;
			L1.moveFront();
			L2.moveFront();
			while(L2count < L2max && L1count < L1max){//iterates through rows
				double tempVal = 0;//tempVal
				Entry EL1 = ((Entry)L1.get());//Entry values
				Entry EL2 = ((Entry)L2.get());
				double L1val = EL1.getVal();
				double L2val = EL2.getVal();
				int L1col = EL1.getCol();//col values
				int L2col = EL2.getCol();
				
				if(L1col < L2col){//comparison of columns
					L1.moveNext();
					L1count++;
				}else if(L2col < L1col){//comparison of columns
					L2.moveNext();
					L2count++;
				}else{//column values are the same
					tempVal = (L1val * L2val);
					L1.moveNext();
					L2.moveNext();
					L1count++;
					L2count++;
				}		
				val += tempVal;//adds tempVal to val
			}
		}

		return val;

	}
				

//Access functions ------------------------------------------------------------

	//Retruns n, the number of rows and columns of the Matrix

	int getSize(){
		return size;
	}

	//Returns the number of NNZ entries in this Matrix

	int getNNZ(){
		int count = 0;
		for(int i = 0; i < size; i++){//goes through each row of the matrix and adds up the length of each List
			List row = matrix[i];
			if(row.length() > 0){
				row.moveFront();
				for(int j = 0; j < row.length(); j++){
					if(((Entry)row.get()).getVal() != 0){
						count++;
					}
					row.moveNext();
				}
			}
		}
		return count;//returns the value
	}

	//overrides Object's equals() method
	
	public boolean equals(Object x){
		if(x instanceof Matrix){
			if(this == x){
				return true;
			}
			if(size != ((Matrix)x).getSize()){//checks size right off the bat
				return false;
			}

			for(int i = 0; i < size; i++){//goes through each row
				List row = matrix[i];//row
				List rowX = ((Matrix)x).matrix[i];
				if(row.length() != rowX.length()){//checks lengths
					return false;
				}else{
					if(row.length() > 0){
						row.moveFront();
						rowX.moveFront();
						for(int j = 0; j < row.length(); j++){//iterates through the row Entries
							if(((Entry)row.get()).equals(((Entry)rowX.get())) == true){//using equals() method of Entry
								;
							}else{
								return false;
							}
							row.moveNext();
							rowX.moveNext();
						}
					}
				}
			}
			return true;//if the method lasts this long return true
		}else{//error message
			System.err.println("equals: Object passed in is not an instance of Matrix");
			System.exit(1);
			return false;
		}
	}

//Manipulation Procedures -----------------------------------------------------

	//sets this Matrix to the zero state

	void makeZero(){
		for(int i = 0; i < size; i++){//goes through each matrix row and uses the clear() method of List
			matrix[i].clear();
		}
	}

	//returns a new Matrix with the same entries as this Matrix

	Matrix copy(){
		Matrix M = new Matrix(this.getSize());//creates an empty Matrix of the same size
		for(int i = 0; i < size; i++){//goes through each row of Matrix
			List row = matrix[i];//row
			if(row.length() > 0){
				row.moveFront();
				for(int j = 0; j < row.length(); j++){//iterates through the row
					Entry E = ((Entry)row.get());
					int col = E.getCol();
					double val = E.getVal();
					Entry EE = new Entry(col, val);
					M.matrix[i].append(EE);//stores the Entry in the new Matrix
					row.moveNext();
				}	
			}	
		}
		return M;
	}

	//changes ith row, jth column of this Matrix to x
	//pre: 1 <= i <= getSize(), 1 <= j <= getSize()

	void changeEntry(int i, int j, double x){
		if(i > size || i < 1 || j < 1 || j > size){//checks the row and column values passed in 
			System.err.println("changeEntry: error with column/row values");
			System.exit(1);
		}
		
		List L = matrix[i-1];//accesses the List in row i
		if(L.length() > 0){	
			L.moveFront();
			int complete = 0;//insert flag
			for(int k = 0; k < L.length(); k++){//goes through each Node in List
				if(((Entry)L.get()).getCol() == j){//if an Entry with column j already exists
					((Entry)L.get()).changeVal(x);//change that Entry value
					complete = 1;//sets insert flag
					break;//breaks out of for loop
				}else if(((Entry)L.get()).getCol() > j){//if the column of that Entry is greater than j
					Entry E = new Entry(j, x);//new Entry obj
					L.insertBefore(E);//keeping column order
					complete = 1;//sets insert flag
					break;//breaks out of for loop
				}
				L.moveNext();//moves to next Entry
			}
			if(complete == 0){//if flag was never set
				Entry E = new Entry(j, x);//new Entry
				L.append(E);//add E to end of List
			}
		}else{//if length() == 0
			Entry E = new Entry(j, x);//new Entry
			L.prepend(E);//add E to List
		}
		
	}

	//returns a new Matrix that is the scalar product of this Matrix with x

	Matrix scalarMult(double x){
		Matrix M = new Matrix(getSize());//creates new Matrix
		for(int k = 0; k < size; k++){//iterates through all of the rows
			List L = matrix[k];//creates List variable for that row
			List LL = M.matrix[k];
			if(L.length() > 0){//if there are Entrys in the List
				L.moveFront();//moves to the front
				for(int j = 0; j < L.length(); j++){//iterates through each entry in the row
					Entry E = (Entry)L.get();//gets value of the Entry
					Entry EE = new Entry(E.getCol(), (E.getVal() * x));//creates new Entry with the value of E multiplied by x
					LL.append(EE);//adds EE to the end of the new created Matrix row
					L.moveNext();//moves to next Entry of List
				}
			}
		}
		return M;//return the Matrix
	}

	//returns a new Matrix that is the sum of this Matrix with M
	//pre: getSize() == M.getSize()

	Matrix add(Matrix M){
		if(getSize() != M.getSize()){
			System.err.println("add: the Matrices are different sizes");
			System.exit(1);
		}
		Matrix newM;
		if(this == M){//checks to see if the matrices are the same Object
			newM = scalarMult(2.0);
		}else{
			newM = new Matrix(getSize());
			for(int  i = 0; i < getSize(); i++){//iterates through rows
				List L = matrix[i];//gets the rows for the matrices
				List LL = M.matrix[i];
				int Lmax = 0;//variables for scope checking of the rows
				int LLmax = 0;
				int Lcount = 0;
				int LLcount = 0;
				if(L.length() > 0){
					L.moveFront();
					Lmax = L.length();
				}
				if(LL.length() > 0){
					LL.moveFront();
					LLmax = LL.length();
				}
				for(int k = 0; k < (L.length() + LL.length()); k++){//iterates through all the Entrys in the (i+1)th row
					double Lval = 0;
					double LLval = 0;
					int Lcol = 0;
					int LLcol = 0;
					if(Lcount  < Lmax){//makes sure L is in scope
						Entry E = ((Entry)L.get());
						Lval = E.getVal();
						Lcol = E.getCol();
					}
					if(LLcount < LLmax){//makes sure LL is in scope
						Entry EE = ((Entry)LL.get());
						LLval = EE.getVal();
						LLcol = EE.getCol();
					}
					
					if(Lcol == 0 && LLcol == 0){//if both are out of scope
						break;
					}else if(Lcol == 0 && LLcol != 0){//if one is out of scope 
						newM.changeEntry((i+1), LLcol, LLval);
						LL.moveNext();
						LLcount++;
					}else if(Lcol != 0 && LLcol == 0){//if one is out of scope
						newM.changeEntry((i+1), Lcol, Lval);
                                                L.moveNext();
                                                Lcount++;
					}else if(Lcol > LLcol){//compares col values
						newM.changeEntry((i+1), LLcol, LLval);
                                                LL.moveNext();
                                                LLcount++;
					}else if(Lcol < LLcol){//compares col values
						newM.changeEntry((i+1), Lcol, Lval);
                                                L.moveNext();
                                                Lcount++;
					}else{//if col values are the same
						double val = (Lval + LLval);
						newM.changeEntry((i+1), Lcol, val);
						L.moveNext();
						LL.moveNext();
						Lcount++;
						LLcount++;
					}
				}
			}
		}	
		return newM;//return new Matrix
	}

	//returns a new Matrix that is the difference of this Matrix with M
	//pre: getSize() == M.getSize()

	Matrix sub(Matrix m){
		if(getSize() != m.getSize()){//the matrices must be the same size
                        System.err.println("add: the Matrices are different sizes");
                        System.exit(1);
                }
                Matrix newM = new Matrix(getSize());//Matrix to be returned
                if(this == m){//checks to see if the matrices are the same Object
			;
		}else{
		for(int i = 0; i < getSize(); i++){//iterates through rows
                        List L = matrix[i];//row i of first Matrix
                        List LL = m.matrix[i];//row i of second Matrix
			int Lmax = 0;//count values
                        int LLmax = 0;
                        int Lcount = 0;
                        int LLcount = 0;
                        if(L.length() > 0){
	                        L.moveFront();
                                Lmax = L.length();//setting count values
                        }
                        if(LL.length() > 0){
                                LL.moveFront();
                                LLmax = LL.length();
                        }
                        for(int k = 0; k < (L.length() + LL.length()); k++){//iterates through all the Entrys in the (i+1)th row
                        	double Lval = 0;
                                double LLval = 0;
                                int Lcol = 0;
                                int LLcol = 0;
                                if(Lcount  < Lmax){//makes sure the List is in scope
                                	Entry E = ((Entry)L.get());//sets values for that Entry
                                        Lval = E.getVal();
                                        Lcol = E.getCol();
                                }
                                if(LLcount < LLmax){
                                        Entry EE = ((Entry)LL.get());
                                        LLval = EE.getVal();
                                        LLcol = EE.getCol();
                                }
	
				if(Lcol == 0 && LLcol == 0){//if both Lists are out of scope
                                                ;
                                }else if(Lcol == 0 && LLcol != 0){//if L is out of scope but LL isn't
                 	               newM.changeEntry((i+1), LLcol, (-LLval));
                                       LL.moveNext();
                                       LLcount++;
                                }else if(Lcol != 0 && LLcol == 0){//if LL is out of scope but L isn't
                                       newM.changeEntry((i+1), Lcol, Lval);
                                       L.moveNext();
                                       Lcount++;
                                }else if(Lcol > LLcol){//compares col values
                                       newM.changeEntry((i+1), LLcol, (-LLval));
                                       LL.moveNext();
                                       LLcount++;
                                }else if(Lcol < LLcol){//compares col values
                                       newM.changeEntry((i+1), Lcol, Lval);
                                       L.moveNext();
                                       Lcount++;
                                }else{//if col values are the same
                                       double val = (Lval - LLval);
                                       if(val != 0){
						newM.changeEntry((i+1), Lcol, val);
                                       }
				       L.moveNext();
                                       LL.moveNext();
                                       Lcount++;
                                       LLcount++;
                                }
                 	 }
		}
		}
		return newM;//return new Matrix
	}

	//returns a new Matrix that is the transpose of this Matrix

	Matrix transpose(){
		Matrix M = new Matrix(getSize());//new Matrix
		for(int i = 0; i < getSize(); i++){//iterates throguh rows
			List L = matrix[i];//row
			if(L.length() > 0){
				L.moveFront();
				for(int j = 0; j < L.length(); j++){//iterates through the row
					int col = ((Entry)L.get()).getCol();
					double val = ((Entry)L.get()).getVal();
					M.changeEntry(col, (i+1), val);//switches column and row values
					L.moveNext();
				}
			}
		}
		return M;//returns the new Matrix
	}

	//returns a new Matrix that is the product of this Matrix with M
	//pre: getSize() == M.getSize()


	Matrix mult(Matrix M){
	if(getSize() != M.getSize()){
	    System.err.println("add: the Matrices are different sizes");
	    System.exit(1);
	}
	Matrix newM = new Matrix(getSize());
	for(int i = 0; i < getSize(); i++){//iterates through cols
	
	    List col = new List();//list for col
	
	    for(int j = 0; j < getSize(); j++){//iterates to fill col List
	        List L = M.matrix[j];//row
	        if(L.length() > 0){
	            L.moveFront();
	            for(int y = 0; y < L.length(); y++){//iterates to find an Entry that matches the current col value
	                Entry E = ((Entry)L.get());
	                if(E.getCol() == (i+1)){
	                    Entry EE = new Entry((j+1), E.getVal());
						col.append(EE);
	                    break;
	                }else if(E.getCol() < (i+1)){
	                    L.moveNext();
	                }else{
	                    break;
	                }
	            }
	        }
	    }
	
	    for( int k = 0; k < getSize(); k++){//iterates through rows     
	
	        List row = matrix[k];//row
	
	        double value = dotProduct(row, col);//dotProduct math on the two Lists
	        if(value != 0){
	            newM.changeEntry((k+1), (i+1), value);//adds Entry to the Matrix to be returned
	        }
	
	    }
	
	}
	
	return newM;//returns the new Matrix
	
	}


		

//Other Functions -------------------------------------------------------------

	//overrides Object's toString() method

	public String toString(){
		String S = "";//String to be returned
		for(int i = 0; i < getSize(); i++){//iterates through rows
			List row = matrix[i];//List row
			String SS = row.toString();//calls toString() method of the List
			if(row.length() > 0){
				S += ((i+1) + ": " + SS + "\n");//adds SS to S and formats it
			}
		}
		return S;//returns the overall String
	}
	

}



