package dk.mtdm.managementSystem.save;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import dk.mtdm.exceptions.IncorrectSaveSettingsLoaded;
import dk.mtdm.managementSystem.world.World;
import dk.mtdm.managementSystem.world.chunk.ChunkList;

public class load {
  private String path = "src\\dk\\mtdm\\managementSystem\\world\\saveing\\";
  private String name;

  public load(String saveName) throws IncorrectSaveSettingsLoaded{
    path += saveName + "\\";
    name = saveName;
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
        e.printStackTrace();
        return;
      } catch (NoSuchElementException e){
        e.printStackTrace();
        return;
      }
    }
    //int<dim><chunk>[blockx][blocky][item]
    ArrayList<ArrayList<int[][][]>> blocks = new ArrayList<ArrayList<int[][][]>>();
    blocks.add(new ArrayList<int[][][]>());
    {
      File chunk = new File(path + "dim\\0\\0.chunk");
      int dimID = 0;
      int chunkID = 0;
      while(chunk.exists()){
        try(Scanner chunkRead = new Scanner(chunk)){
          String data[][] = new String[CHUNK_WIDTH][]; //x,y
          for(int i = 0; i < CHUNK_WIDTH && chunkRead.hasNextLine();i++){
            data[i] = chunkRead.nextLine().split(";");
            if(data[i].length != HEIGHT){
              throw new IncorrectSaveSettingsLoaded(HEIGHT,data[i].length,name,true);
            }
            if(i+1 >= CHUNK_WIDTH || !chunkRead.hasNextLine()){
              throw new IncorrectSaveSettingsLoaded(CHUNK_WIDTH,i,name,false);
            }
          }
          int[][][] data2 = new int[CHUNK_WIDTH][HEIGHT][5];
          for (int x = 0; x < data2.length; x++){
            for (int y = 0; y < data2[x].length; y++) {
              String[] tempData = data[x][y].split(",");
              for (int info = 0; info < data2[x][y].length; info++) {
                data2[x][y][info] = Integer.parseInt(tempData[info]);
              }
            }
          }
          blocks.get(dimID).add(data2);
        } catch (FileNotFoundException e){
          e.printStackTrace();
        }
      }
    }
    World.setState(0, 0, 0, 0, 0, 0);
  }
}
