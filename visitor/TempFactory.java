package visitor;

import java.util.*;

class TempFactory{

	static Stack<String> tempStack; 
	static int tempcounter;

	public TempFactory(){
		tempStack = new Stack<String>();
		tempcounter = 5;
	}

	public String newTemp(){
		String newtemp = "TEMP "+tempcounter;
		tempStack.push(newtemp);
		tempcounter++;
		// System.out.println("TEMPFACTORY: Created TEMP "+tempcounter);
		return newtemp;
	}

	public void deleteTemp(){
		tempStack.pop();
		tempcounter--;
	}

	public void setCounter(int number){
		//Thetoume ton counter se kathe procedure wste na synexistei apo ekei i arithmisi twn TEMP
		// System.out.println("TEMPFACTORY: Counter set at "+number);
		tempcounter = number;
	}

}