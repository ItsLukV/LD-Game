package dk.mtdm.managementSystem.save;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import dk.mtdm.managementSystem.world.World;
import dk.mtdm.managementSystem.world.chunk.ChunkList;

public class Save {
  private String path = "src\\dk\\mtdm\\managementSystem\\world\\saveing\\";
  public Save(String name) throws IOException{
    path+= name;
    if(!new File(path).mkdir()){
      System.out.println("err");
      throw new IOException("failed to make directory: " + path+name);
    }
    if(!new File(path+"\\dim").mkdir()){
      System.out.println("err");
      throw new IOException("failed to make directory: " + path+name);
    }
  }
  public void state(){
    String world = World.getState();
    String[][] dimensions = ChunkList.getState();
    int dimID = Integer.parseInt(dimensions[dimensions.length-1][0]);
    try {
      FileWriter thing = new FileWriter(path + ".world");
      thing.write(world);
      thing.close();
      for (int i = 0; i < dimensions.length; i++) {
        if(!new File(path + "\\dim" + (dimID+i-1)).mkdir()){
          System.out.println("err");
          throw new IOException("failed to make directory: " + path + "\\dim" + (dimID+i-1));
        }
        for (int j = 0; j < dimensions[i].length; j++) {
          thing = new FileWriter(path + "\\dim\\" + j);
          thing.write(dimensions[i][j]);
        }
      }
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
