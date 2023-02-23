package dk.mtdm.exceptions;

public class MissingDataException extends Throwable{
  private String e;
  
  /**
 * TODO: write javadoc
 */
  public MissingDataException(String exception){
    super(exception);
    e = exception;
  }
  public MissingDataException(){
    super("location type not found, likely null, please set location type");
    e = "location type not found, likely null, please set location type";
  }
  /**
 * TODO: write javadoc
 */
  @Override
  public void printStackTrace(){
    System.out.println("\nno texture found for this block");
    System.out.println("current error: " + e);
    super.printStackTrace();

  }
}
