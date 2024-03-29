package dk.mtdm.exceptions;

public class MissingTextureException extends Throwable{
  private String e;
  
  /**
   * a texture that is meant to be  drawn is not pressent in the file directory
   * @param exception what went wrong
   */
  public MissingTextureException(String exception){
    super(exception);
    e = exception;
  }
  @Override
  public void printStackTrace(){
    System.out.println("\nno texture found for this block");
    System.out.println("current error: " + e);
    super.printStackTrace();

  }
}
