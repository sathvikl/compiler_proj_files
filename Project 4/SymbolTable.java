package edu.ufl.cise.cop5555.sp12.context;

import ...
public class SymbolTable {


	public SymbolTable(){
     //TODO Implement me
	}


	public void enterScope() {
		//TODO Implement me
	}

	public void exitScope() {
		//TODO Implement me
	}

	// returns the in-scope declaration of the name if there is one, 
	//otherwise it returns null
	public Declaration lookup(String ident) {
    //TODO Implement me
	}
	

	// if the name is already declared IN THE CURRENT SCOPE, returns false. 
	//Otherwise inserts the declaration in the symbol table
	public boolean insert(String ident, Declaration dec) {
	//TODO Implement me
	}


}