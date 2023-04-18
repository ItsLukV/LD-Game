package dk.mtdm.exceptions;

public class MissingBlockTypeException extends Throwable{
  private String e;
  
  /**
   * descibes when a blocktype is not found, this primarily happens when using blockpicker and parsing null
   * @param exception a message of what went wrong
   */
  public MissingBlockTypeException(String exception){
    super(exception);
    e = exception;
  }
  @Override
  public void printStackTrace(){
    System.out.println("\nno block found for this type");
    System.out.println("current error: " + e);
    super.printStackTrace();

  }
}
