package dk.mtdm;

import dk.mtdm.Commands.CommandHandler;
import dk.mtdm.itemsAndMore.Blocks.Block;
import dk.mtdm.itemsAndMore.texureFiles.BlockTextures;
import dk.mtdm.itemsAndMore.texureFiles.ItemTexture;
import dk.mtdm.itemsAndMore.texureFiles.breaking.BreakingTexures;
import dk.mtdm.location.LocationTypes;
import dk.mtdm.location.WorldWideLocation;
import dk.mtdm.managementSystem.Entitys.Player;
import dk.mtdm.managementSystem.world.World;
import dk.mtdm.managementSystem.world.chunk.ChunkList;
import dk.mtdm.misc.miscTextures.MiscTextures;
import processing.core.PApplet;

public class Sketch extends PApplet {
  public static Player player;
  public static int KeyCode;
  public static boolean gettingCommand = false;
  public static int width = 800;
  public static int height = 450;
  private final CommandHandler commandHandler = new CommandHandler();

  /**
   * a piece of code that runs as a preparation for PApplet
   */
  @Override
  public void settings() {
    size(Sketch.width, Sketch.height);
  }

  /**
   * a piece of code that runs once at the start of PApplet
   */
  @Override
  public void setup() {
    frameRate(60);

    BlockTextures.loadBlockTextures(this);
    MiscTextures.loadBlockTextures(this);
    ItemTexture.loadBlockTextures(this);
    BreakingTexures.loadBreakingTextures(this);

    World.setup(3, World.get_HEIGHT(), 20);
    ChunkList.getChunk(0).joinGen();
    int i = 0;
    while (// spawn conditions
    ChunkList.getChunk(0).getBlock(16, i).getSolidity() ||
        ChunkList.getChunk(0).getBlock(16, i + 1).getSolidity() ||
        ChunkList.getChunk(0).getBlock(16 + 1, i + 1).getSolidity() ||
        ChunkList.getChunk(0).getBlock(16 - 1, i + 1).getSolidity() ||
        ChunkList.getChunk(0).getBlock(16, i + 2).getSolidity()) {
      i++;
    }
    player = new Player(
        WorldWideLocation.create(16 * Block.getWidth(), -(i + 1) * Block.getHeight(), LocationTypes.canvas));
  }

  /**
   * a loop that is run by PApplet once on every frame
   */
  @Override
  public void draw() {
    if (gettingCommand) {
      commandHandler.show(g);
      return;
    }
    background(0, 0, 0);

    push();
    translate(-Player.getCanvas().getX() - Player.width / 2 + Sketch.width / 2,
        -Player.getCanvas().getY() - Player.height / 2 + Sketch.height / 2);
    // System.out.println(World.CanvasToGlobal(player.getPos()).getX() + " " +
    // World.CanvasToGlobal(player.getPos()).getY() + "\n" + player.getPos().getX()
    // + " " + player.getPos().getY() + "\n");
    World.show(g, (int) (Player.getCanvas().getX() / World.get_CHUNK_WIDTH() / Block.getWidth()) - 2,
        (int) (Player.getCanvas().getX() / World.get_CHUNK_WIDTH() / Block.getWidth()) + 1);
    player.draw(g);
    player.tick();

    pop();
    player.drawWithoutTranslate(g);
    // System.out.println(player.getPos().getX() + " " + player.getPos().getY() +
    // "\n" + World.CanvasToGlobal(player.getPos()).getX() + " " +
    // World.CanvasToGlobal(player.getPos()).getY() + "\n");

  }

  /**
   * every keyRelease interupt in the program will call this method which will decide what will happen based on which key is pressed <br>
   * <br>
   * this is used to stop movements  that are currently happening
   */
  @Override
  public void keyReleased() {
    boolean left = keyCode == 37 || keyCode == 65;
    boolean right = keyCode == 39 || keyCode == 68;
    boolean up = keyCode == 38 || keyCode == 87;
    boolean down = keyCode == 40 || keyCode == 83;
    player.keyReleased(left, right, up, down);
  }

  /**
   * every keyPress interupt in the program will call this method which will decide what will happen based on which key is pressed <br>
   * <br>
   * this is used to stop movements  that are currently happening
   */
  @Override
  public void keyPressed() {
    KeyCode = keyCode;

    if (key == ESC && gettingCommand) {
      key = 0;
      gettingCommand = false;
      return;
    } else if (key == ESC) {
      key = 0;
    }
    if (gettingCommand) {
      if (keyCode == ENTER) { // the key is Enter
        gettingCommand = false;
        commandHandler.execute();
        return;
      }
      commandHandler.keyPressed(keyCode);
      return;
    }

    if (keyCode == 84) {
      gettingCommand = true; // the key is t
      return;
    }
    boolean left = keyCode == 37 || keyCode == 65;
    boolean right = keyCode == 39 || keyCode == 68;
    boolean up = keyCode == 38 || keyCode == 87;
    boolean down = keyCode == 40 || keyCode == 83;
    boolean e = keyCode == 69;
    boolean one = keyCode == 49;
    boolean two = keyCode == 50;
    boolean three = keyCode == 51;
    player.keyPressed(left, right, up, down, e, one, two, three);
  }

  @Override
  public void mousePressed() {
    player.mousePressed(this);
  }
}
