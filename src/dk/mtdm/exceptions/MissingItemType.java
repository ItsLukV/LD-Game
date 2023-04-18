package dk.mtdm.exceptions;

public class MissingItemType  extends Throwable{
  private String e;
  
  /**
   * a texture that is meant to be  drawn is not pressent in the file directory
   * @param exception what went wrong
   */
  public MissingItemType(String exception){
    super(exception);
    e = exception;
  }
  @Override
  public void printStackTrace(){
    System.out.println("\nno type was found for this item");
    System.out.println("current error: " + e);
    super.printStackTrace();

  }
}