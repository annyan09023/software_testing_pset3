package pset3;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
public class CNF3 { // represents a 3-CNF formula
  private int numVars; // total number of boolean variables in the formula
  // the variables are v_0, v_1, ..., v_{numVars - 1}
  private List<Clause> clauses;
  public CNF3(String formula, int numVars, int numClauses) {
    // String "formula" represents the formula in 3-CNF;
    // "numVars" is the number of variables in the "formula";
    // "numClauses" is the number of clauses in the "formula".
    // assume: numVars >= 1, numClauses >= 1, and the
    //     variables are from the set { v0, v1, ..., v9 }
	this.clauses=new LinkedList<Clause>();
    StringTokenizer st = new StringTokenizer(formula, " \t\n\r\f()");
    // your code goes here
    this.numVars = numVars;
    for (int i=0 ; i< numClauses; i++){
    	int var1, var2, var3;
    	boolean neg1, neg2 , neg3;
    	String str = st.nextToken();
    	if(str.contains("and"))
    		str = st.nextToken();
    	if(str.contains("!")){
    	  neg1 = false;	 
    	  var1 = str.charAt(2)-'0';
    	}
    	else{
    	  neg1 = true;
          var1 = str.charAt(1)-'0';
    	}
    	str = st.nextToken();
    	str = st.nextToken();
    	if(str.contains("!")){
    	  neg2 = false;	 
    	  var2 = str.charAt(2)-'0';
    	}
    	else{
    	  neg2 = true;
    	  var2 = str.charAt(1)-'0';
    	}
    	str = st.nextToken();
    	str = st.nextToken();
    	if(str.contains("!")){
    	  neg3 = false;	 
    	  var3 = str.charAt(2)-'0';
    	}
    	else{
    	  neg3 = true;
    	  var3 = str.charAt(1)-'0';
    	}
    	//System.out.println(var1+" "+neg1+" "+var2+" "+neg2+" "+var3+" "+neg3);
    	this.clauses.add(new Clause(var1, neg1, var2, neg2, var3, neg3));
    }
  }
  public int numVars() {
    return numVars;
  }
  public int numClauses() {
    return clauses.size();
  }
  private void addClause(int v1, boolean n1, int v2, boolean n2, int v3, boolean n3) {
    clauses.add(new Clause(v1, n1, v2, n2, v3, n3));
  }
  private static class Clause {
    int var1, var2, var3;
    boolean neg1, neg2, neg3; // "neg" iff the corresponding variable is negated
    Clause(int v1, boolean n1, int v2, boolean n2, int v3, boolean n3) {
      var1 = v1; neg1 = n1;
      var2 = v2; neg2 = n2;
      var3 = v3; neg3 = n3;
    }
  }
  public int countSatisfiedClauses(boolean[] assignment) {
    //precondition: assignment != null && assignment.length == numVars
    int size = this.clauses.size();
    int count = 0;
    for(int i=0; i<size; i++){
    	Clause cls = clauses.get(i);
    	if(assignment[cls.var1]==cls.neg1||assignment[cls.var2]==cls.neg2||assignment[cls.var3]==cls.neg3)
    		count++;
    }
    return count;
  }
  
  public static void main(String[] a) {
    //CNF3 formula = new CNF3("(v0 or v1 or v2) and (!v0 or !v1 or v2) and (!v0 or v1 or !v2)", 3, 3);
   // System.out.println(formula.countSatisfiedClauses(new boolean[]{true,true,true}));
    CNF3 formula2 = new CNF3("(v0 or v1 or v2) and (v0 or !v1 or v3) and (!v0 or v1 or !v2) and (!v0 or !v1 or v2) and (!v0 or !v1 or !v2) and (!v0 or !v1 or !v3) and (v0 or v1 or !v2) and (v0 or v1 or v3) and (v0 or !v1 or v2) and (v0 or !v1 or !v2) and (!v0 or v1 or v2)", 4, 11);
    MAX3SATSolver solver = new MAX3SATSolver();
    solver.solve(formula2);
  }
}