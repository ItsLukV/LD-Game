package dk.mtdm.exceptions;

public class IncorrectSaveSettingsLoaded extends Throwable{
  private String e;
  
  /**
   * an exception that is thrown a when chunk file has a conflicting state compared to the expected information found in the world file
   * @param expectedLength the size that is reported by the world file
   * @param foundLengt the size found in the chunk file
   * @param saveName the file that the problem is in
   * @param heightProblem whether the problem is along the y aksis
   */
  public IncorrectSaveSettingsLoaded(int expectedLength, int foundLengt,String saveName, boolean heightProblem){
    super("during loading of save: " + saveName +" a chunk with a" + (heightProblem ? "height of: " : "width of: ") + expectedLength+ ", instead a " + (heightProblem ? "height of: " : "width of: ") + foundLengt + " was found");
    e = "during loading of save: " + saveName +" a chunk with a" + (heightProblem ? "height of: " : "width of: ") + expectedLength+ ", instead a " + (heightProblem ? "height of: " : "width of: ") + foundLengt + " was found";
  }
  @Override
  public void printStackTrace(){
    System.out.println("\nno block found for this type");
    System.out.println("current error: " + e);
    super.printStackTrace();

  }
}
