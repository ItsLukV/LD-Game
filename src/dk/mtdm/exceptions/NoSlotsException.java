package dk.mtdm.exceptions;

public class NoSlotsException extends Throwable{
  private String e;
  
  /**
   * a texture that is meant to be  drawn is not pressent in the file directory
   * @param exception what went wrong
   */
  public NoSlotsException(String exception){
    super(exception);
    e = exception;
  }
  @Override
  public void printStackTrace(){
    System.out.println("it was not possible to find either a slot containing the specified item, or an empty slot");
    System.out.println("current error: " + e);
    super.printStackTrace();
  }
}
