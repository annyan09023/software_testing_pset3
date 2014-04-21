package pset3;
import java.util.LinkedList;
import java.util.List;
import gov.nasa.jpf.vm.Verify;
public class TestSequenceGenerator {
  public void generateAllBoundedSequences(String classname, List<MethodDecl> methods,
      ValueDomains doms) {
    // precondition: assume all methods in "methods" are declared by class "classname" and
    //     "doms" define value domains for all method types that appear in given methods;
    //     assume also that "classname" defines an instance method "invocationCount()", which
    //     returns the number of methods invoked on "this" since its creation
    // postcondition: prints a JUnit test suite to standard console; the suite contains
    //     method invocation sequences of length <= methods.size(), such that (1) each method
    //     sequence for each parameter value combination is executed and (2) each test case
    //     includes a test assertion that validates the result of method "invocationCount()".
	  Verify.resetCounter(0);
	  String output_cmd = "";
	  int funct_num = 0;
	  int method_size = methods.size();
	  int CALL_TIME = Verify.getInt(0, method_size);
	  for (int i=0; i < CALL_TIME; i++){
		funct_num = Verify.getInt(0, method_size-1);
		output_cmd = output_cmd+"var."+methods.get(funct_num).methodName()+"(";
		String[] Types = methods.get(funct_num).argumentTypes();
		int funct_variable = methods.get(funct_num).argumentTypes().length;
		for (int j=0; j<funct_variable; j++){
			String type = Types[j];
			int variable_num = doms.getDomain(type).size();
			int VAR_NUM = Verify.getInt(0, variable_num-1);
		    if(j<funct_variable-1)
		    	output_cmd = output_cmd + doms.getDomain(type).get(VAR_NUM)+",";
		    else
		    	output_cmd = output_cmd + doms.getDomain(type).get(VAR_NUM)+");\n";
		}
	  }
	  System.out.println("@Test public void test"+Verify.getCounter(0)+"(){");
	  System.out.println(classname+" var = new "+classname+"();");
	  if(output_cmd != "")
		  System.out.print(output_cmd);
	  System.out.println("assertTrue(var.invocationCount() == "+ funct_num);
	  System.out.println();
	  Verify.incrementCounter(0);
}
	  
  
  public static void main(String[] a) {
   /* MethodDecl m = new MethodDecl("C", "m", new String[]{"int"});
    List<MethodDecl> methods = new LinkedList<MethodDecl>();
    methods.add(m);
    new TestSequenceGenerator().generateAllBoundedSequences("C", methods, new ValueDomains());*/
    MethodDecl m = new MethodDecl("C", "m", new String[]{"int"});
    MethodDecl n = new MethodDecl("C", "n", new String[]{"java.lang.String", "boolean"});
    List<MethodDecl> methods = new LinkedList<MethodDecl>();
    methods.add(m);
    methods.add(n);
    new TestSequenceGenerator().generateAllBoundedSequences("C", methods, new ValueDomains());
  }
}
