package dk.mtdm.exceptions;
//TODO: add comments
public class MissingTextureException extends Throwable{
  private String e;
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
