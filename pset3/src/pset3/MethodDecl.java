package pset3;

public class MethodDecl { // represents "void", "instance" methods
  private String className; // class that declares the method
  private String methodName;
  private String[] argumentTypes;
  public MethodDecl(String clazz, String method, String[] types) {
    className = clazz;
    methodName = method;
    argumentTypes = types;
  }
  public String className() {
    return className;
  }
  public String methodName() {
    return methodName;
  }
  public String[] argumentTypes() {
    return argumentTypes;
  } 
}
