package dk.mtdm.managementSystem.save;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import dk.mtdm.managementSystem.world.World;
import dk.mtdm.managementSystem.world.chunk.ChunkList;

public class Save {
  private String path = "src\\dk\\mtdm\\managementSystem\\world\\saveing\\";
  public Save(String name) throws IOException{
    while(true){
      if(!new File(path + name).mkdir()){
        System.out.println("directoctory not made");
        if(!new File(path + name).exists()){
          System.out.println("err");
          throw new IOException("failed to make directory: " + path);
        }else{
          System.out.print("that name is already in use, do you wish to overwrite it?: Y/N ");
          String answer = "";
          try(Scanner scan = new Scanner(System.in)){
            answer =  scan.nextLine();
            System.out.println(answer);
            if(answer.equals("y") || answer.equals("Y") || answer.equals("Yes") || answer.equals("yes")){
              break;
            }else if(answer.equals("N") || answer.equals("n") ||answer.equals("No") || answer.equals("no")){
              System.out.print("what should the savefile be called?: ");
              name =  scan.nextLine();
              scan.close();
            }
            System.out.println(name);
          }
        }
      }else{
        break;
      }
    }
    path += name  + "\\";
    if(!new File(path+"dim").mkdir()){
      if(!new File(path+"dim").exists()){
        System.out.println("err");
        throw new IOException("failed to make directory: " + path + "\\dim");
      }else{
        System.out.println("directory already exists: " + path + "\\dim");
      }
    }
  }
  public void state(){
    String world = World.getState();
    String[][] dimensions = ChunkList.getState();
    int dimID = Integer.parseInt(dimensions[dimensions.length-1][0]);
    try {
      FileWriter thing = new FileWriter(path + "general.world");
      thing.write(world);
      thing.close();
      for (int i = 0; i < dimensions.length-1; i++) {
        if(!new File(path + "dim\\" + (dimID+i)).mkdir()){
          if(!new File(path+"dim\\").exists()){
            System.out.println("err");
            throw new IOException("failed to make directory: " + path + "dim\\" + (dimID+i));
          }else{
            System.out.println("directory already exists: " + path + "dim\\" + (dimID+i));
          }
        }
        for (int j = 0; j < dimensions[i].length; j++) {
          try {
            new File(path + "dim\\" + (dimID+i) + "\\"+ j + ".chunk").createNewFile();
            thing = new FileWriter(path + "dim\\" + (dimID+i) + "\\"+ j + ".chunk");
            thing.write(dimensions[i][j]);
          } catch (FileNotFoundException e) {
            System.out.println("could not find file: " + path + "dim\\" + (dimID+i) + "\\"+ j + ".chunk");
          }catch (IOException e){
            e.printStackTrace();
          }
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    System.out.println("complete");
  }
}
