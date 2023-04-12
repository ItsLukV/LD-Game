package dk.mtdm.managementSystem.save;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.xml.namespace.QName;

import dk.mtdm.exceptions.MissingBlockTypeException;
import dk.mtdm.itemsAndMore.Blocks.AirBlock;
import dk.mtdm.itemsAndMore.Blocks.BedrockBlock;
import dk.mtdm.itemsAndMore.Blocks.Block;
import dk.mtdm.location.LocationTypes;
import dk.mtdm.location.WorldWideLocation;
import dk.mtdm.managementSystem.world.World;
import dk.mtdm.managementSystem.world.chunk.Chunk;
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
    int GeneratorHeight;
    {
      File world = new File(path + "general.world");
      try (Scanner worldReader = new Scanner(world)) {
        CHUNK_WIDTH = worldReader.nextInt();
        HEIGHT = worldReader.nextInt();
        seed1 = worldReader.nextInt();
        BlockStone = worldReader.nextFloat();
        BlockBedrock = worldReader.nextFloat();
        BlockAir = worldReader.nextFloat();
        GeneratorHeight = worldReader.nextInt();
      } catch (FileNotFoundException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
        return;
      } catch (NoSuchElementException e){
        e.printStackTrace();
        return;
      }
    }
<<<<<<< HEAD
    World.setState(CHUNK_WIDTH, HEIGHT, seed1, BlockStone, BlockBedrock, BlockAir,GeneratorHeight);
    {
      int dimID = 0;
      while (true) {
        try{
          boolean up = true;
          int chunkID = 0;
          ArrayList<Chunk> chunks = new ArrayList<Chunk>();
          while(true){
            try{
              File chunk = new File(path + "dim\\" + dimID + chunkID + ".chunk");
              Scanner chunkReader = new Scanner(chunk);
              ArrayList<String[]> set = new ArrayList<String[]>();
              while (chunkReader.hasNextLine()){
                set.add(chunkReader.nextLine().split(";"));
              }
              chunkReader.close();
              Block[][] blocks = new Block[CHUNK_WIDTH][HEIGHT];
              for (int y = 0; y < set.size(); y++) {
                for (int x = 0; x < blocks.length; x++) {
                  try {
                  WorldWideLocation pos = new WorldWideLocation(x,y,LocationTypes.relative,chunkID);
                    blocks[x][y] = Block.fromState(set.get(y)[x],pos);
                  } catch (MissingBlockTypeException e) {
                    e.printStackTrace();
                  }
                }
              }
              if(up){
                chunks.add(new Chunk(blocks,chunkID,GeneratorHeight));
                chunkID++;
              }else{
                chunks.add(0,new Chunk(blocks,chunkID,GeneratorHeight));
                chunkID--;
              }

            }catch(Exception e){
              if(up){
                chunkID = -1;
                up = false;
              }else{
                break;
              }
            }
          }
          dimID++;
        }catch(Exception e){
          break;
        }
      }
    }
=======
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
>>>>>>> 72046ac448d9a17789a17d5d32ee7dfff79c04c9
  }
}
