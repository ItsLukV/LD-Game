package dk.mtdm.exceptions;

public class IncorrectSaveSettingsLoaded extends Throwable{
  private String e;
  
  /**
 * TODO: write javadoc
 */
  public IncorrectSaveSettingsLoaded(int expectedLength, int foundLengt,String saveName, boolean heightProblem){
    super("during loading of save: " + saveName +" a chunk with a" + (heightProblem ? "height of: " : "width of: ") + expectedLength+ ", instead a " + (heightProblem ? "height of: " : "width of: ") + foundLengt + " was found");
    e = "during loading of save: " + saveName +" a chunk with a" + (heightProblem ? "height of: " : "width of: ") + expectedLength+ ", instead a " + (heightProblem ? "height of: " : "width of: ") + foundLengt + " was found";
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
