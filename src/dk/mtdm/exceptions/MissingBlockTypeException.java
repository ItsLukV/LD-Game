package dk.mtdm.exceptions;

public class MissingBlockTypeException extends Throwable{
  private String e;
  
  /**
 * TODO: write javadoc
 */
  public MissingBlockTypeException(String exception){
    super(exception);
    e = exception;
  }
  /**
 * TODO: write javadoc
 */
  @Override
  public void printStackTrace(){
    System.out.println("\nno block found for this type");
    System.out.println("current error: " + e);
    super.printStackTrace();

  }
}
