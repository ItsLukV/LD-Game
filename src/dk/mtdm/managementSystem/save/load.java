package dk.mtdm.managementSystem.save;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import dk.mtdm.exceptions.MissingBlockTypeException;
import dk.mtdm.itemsAndMore.Blocks.Block;
import dk.mtdm.location.LocationTypes;
import dk.mtdm.location.WWL;
import dk.mtdm.location.WorldWideLocation;
import dk.mtdm.managementSystem.world.World;
import dk.mtdm.managementSystem.world.chunk.Chunk;
import dk.mtdm.exceptions.IncorrectSaveSettingsLoaded;
import dk.mtdm.managementSystem.world.chunk.ChunkList;

public class Load {
  public static void loadWorld(String saveName) throws IncorrectSaveSettingsLoaded{
    String path = "src\\dk\\mtdm\\managementSystem\\world\\saveing\\";
    path += saveName + "\\";
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
        seed1 = worldReader.nextInt();worldReader.nextLine();
        BlockStone = Float.parseFloat(worldReader.nextLine());
        BlockBedrock = Float.parseFloat(worldReader.nextLine());
        BlockAir = Float.parseFloat(worldReader.nextLine());
        GeneratorHeight = worldReader.nextInt();
      } catch (FileNotFoundException e) {
        e.printStackTrace();
        return;
      } catch (NoSuchElementException e){
        e.printStackTrace();
        return;
      }
    }
    World.setState(CHUNK_WIDTH, HEIGHT, seed1, BlockStone, BlockBedrock, BlockAir,GeneratorHeight);
    {
      boolean dimUp = true;
      int dimID = 0;
      while (true) {
        try{
          boolean up = true;
          int chunkID = 0;
          ArrayList<Chunk> chunks = new ArrayList<Chunk>();
          while(true){
            try{
              File chunk = new File(path + "dim\\" + dimID + "\\" +chunkID + ".chunk");
              if(!chunk.exists()){break;}
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
                  WWL pos = new WWL(x,y,LocationTypes.relative,chunkID);
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
          if(dimUp){
            dimID++;
            ChunkList.setDimensionID(dimID);
          }else{
            dimID--;
          }
          File check = new File(path + "dim\\" + dimID + "\\" + 0 + ".chunk");
          if(!check.exists()){break;}
        }catch(Exception e){
          if(dimUp){
            dimID = -1;
            dimUp = false;
          }else{
            break;
          }
        }
      }
    }
  }
}
