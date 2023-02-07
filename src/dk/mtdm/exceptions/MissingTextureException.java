package dk.mtdm.exceptions;

public class MissingTextureException extends Throwable{
  private String e;
  
  /**
 * TODO: write javadoc
 */
  public MissingTextureException(String exception){
    super(exception);
    e = exception;
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
