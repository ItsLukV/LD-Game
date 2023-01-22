package dk.mtdm.exceptions;

public class MissingBlockTypeException extends Throwable{
  private String e;
  public MissingBlockTypeException(String exception){
    super(exception);
    e = exception;
  }
  @Override
  public void printStackTrace(){
    System.out.println("current error: " + e);
    super.printStackTrace();

  }
}