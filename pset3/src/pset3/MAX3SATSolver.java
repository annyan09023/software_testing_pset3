package pset3;
import gov.nasa.jpf.vm.Verify;

public class MAX3SATSolver {
  public void solve(CNF3 formula) {
    // precondition: formula != null
    // postcondition: outputs a MAX-3SAT solution to "formula" (using JPF)
	Verify.resetCounter(0);
	int num = formula.numVars();
	boolean [] assignment = new boolean[num];
	for (int i=0; i< num; i++){
	  assignment[i] = Verify.getBoolean();	
	}
	if(formula.countSatisfiedClauses(assignment) > Verify.getCounter(0)){
		String cmd = "";
		cmd = cmd+"assignment <"+" ";
		for(int i=0; i<num; i++){
			cmd = cmd + assignment[i]+" ";
		}
		cmd = cmd +"> satisfies "+formula.countSatisfiedClauses(assignment)+" of "+formula.numClauses()+" clauses";
		Verify.setCounter(0, formula.countSatisfiedClauses(assignment));
		System.out.println(cmd);
	}
  }
}