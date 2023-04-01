package dk.mtdm.managementSystem.save;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import dk.mtdm.managementSystem.world.World;

public class load {
  private String path = "src\\dk\\mtdm\\managementSystem\\world\\saveing\\";
  
  public load(String saveName){
    path += saveName + "\\";
    int CHUNK_WIDTH; 
    int HEIGHT;
    int seed1;
    float BlockStone;
    float BlockBedrock;
    float BlockAir;
    {
      File world = new File(path + "general.world");
      try (Scanner worldReader = new Scanner(world)) {
        CHUNK_WIDTH = worldReader.nextInt();
        HEIGHT = worldReader.nextInt();
        seed1 = worldReader.nextInt();
        BlockStone = worldReader.nextFloat();
        BlockBedrock = worldReader.nextFloat();
        BlockAir = worldReader.nextFloat();
      } catch (FileNotFoundException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
        return;
      } catch (NoSuchElementException e){
        e.printStackTrace();
        return;
      }
    }{
      File world = new File(path + "general.world");
      try (Scanner worldReader = new Scanner(world)) {
        CHUNK_WIDTH = worldReader.nextInt();
        HEIGHT = worldReader.nextInt();
        seed1 = worldReader.nextInt();
        BlockStone = worldReader.nextFloat();
        BlockBedrock = worldReader.nextFloat();
        BlockAir = worldReader.nextFloat();
      } catch (FileNotFoundException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
        return;
      } catch (NoSuchElementException e){
        e.printStackTrace();
        return;
      }
    }
    World.setState(0, 0, 0, 0, 0, 0);
  }
}
