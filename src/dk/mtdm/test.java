package dk.mtdm;

import java.io.File;
import java.io.IOException;

import dk.mtdm.managementSystem.save.Save;

public class test {
  public static void main(String[] args) {
    try {
      new Save("help");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
