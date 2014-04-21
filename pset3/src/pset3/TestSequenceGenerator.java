package pset3;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import gov.nasa.jpf.vm.Verify;
public class TestSequenceGenerator {
	private List<Object> methods_cmd = new ArrayList<Object>();
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
	  int count = 0;
	  int i = 0;
	  for (int j=0; j<methods.size();j++){
		  int type_number = methods.get(j).argumentTypes().length;
		  int[] variable_size = new int[type_number];
		  int[] variable_count = new int[type_number];
		  
		  for (int s=0; s<type_number; s++){
			    String type =  methods.get(j).argumentTypes()[s];
				int size = doms.getDomain(type).size();
				variable_size[s] = size;
		//System.out.println("size:"+size);
				variable_count[s] = 0;
		  }
		  while (variable_count[0] < variable_size[0]){
			String cmd = "var."+methods.get(j).methodName()+"(";
				  for (int z=0; z<type_number; z++){
					 String type =  methods.get(j).argumentTypes()[z];
					 if(z < type_number-1)
					   cmd = cmd+doms.getDomain(type).get(variable_count[z])+","; 
					 else{
					   cmd = cmd + (doms.getDomain(type).get(variable_count[z])+");");
					   methods_cmd.add(cmd);
					 }
				  }
		   variable_count[type_number-1]++;
		   for (int r=1; r<type_number; r++){
			   //System.out.println("hello");
			 if(variable_count[type_number-r]==variable_size[type_number-r]){
			   variable_count[type_number-r-1]++;
			   variable_count[type_number-r]=0;
			 }
		   }
		  }			  
	  }
	  while(i<=methods.size()){
		  if(i==0){
		    System.out.println("@Test public void test"+count+"() {");
		    System.out.println(classname+" var = new "+classname+"();");
		    System.out.println("assertTrue(var.invocationCount() =="+" "+i+");");
		    System.out.println();
		    count++;
		    
		  }
		
		  if(i!=0){
			  int size = methods_cmd.size();
			  int[] cmd_count = new int[i];
			  for (int j=0; j< i; j++){
				  cmd_count [j]=0;
			  }
			  while(cmd_count[0]<size){
				 System.out.println("@Test public void test"+count+"() {");
				 System.out.println(classname+" var = new "+classname+"();"); 
				 for (int n=0;n<i;n++){
				   System.out.println(methods_cmd.get(cmd_count[n]));	 
				  }
		         System.out.println("assertTrue(var.invocationCount() =="+" "+i+");");
		         System.out.println();
		         count++;
				 cmd_count[i-1]++;
				 for (int r=1; r<i; r++){
					 if(cmd_count[i-r]==size){
					   cmd_count[i-r-1]++;
					   cmd_count[i-r]=0;
					 }
				   }
				  }		
			  }
			
		  
	  i++;
  }
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
