package dk.mtdm;

public class LDVector {
  private int x;
  private int y;
  
  public LDVector(int x,int y){
    this.x = x;
    this.y = y;
  }
  public int getX(){
    return x;
  }
  public int getY(){
    return y;
  }
  public int dotProduct(LDVector vector){
    return this.getX()*vector.getX()+this.getY()*vector.getY();
  }
}
