package dk.mtdm.exceptions;

public class MissingDataException extends Throwable{
  private String e;
  
  /**
   * descibes when a piece of information was not provided or is not usable, this primarily happens when using WorldWideLocation and requesting information that requres mor information than what was given
   * @param exception
   */
  public MissingDataException(String exception){
    super(exception);
    e = exception;
  }
    /**
   * descibes when a locationType is not found, this primarily happens when using WorldWideLocation and parsing null
   * @param exception what went wrong
   */
  public MissingDataException(){
    super("location type not found, likely null, please set location type");
    e = "location type not found, likely null, please set location type";
  }
  @Override
  public void printStackTrace(){
    System.out.println("\nno texture found for this block");
    System.out.println("current error: " + e);
    super.printStackTrace();

  }
}
