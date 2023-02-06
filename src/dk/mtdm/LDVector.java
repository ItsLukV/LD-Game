package dk.mtdm;
//TODO: add comments
public class LDVector {
  private int x;
  private int y;

  public LDVector(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public int getX() {
    return x;
  }

  public LDVector copy(){
    return new LDVector(x, y);
  }

  public int getY() {
    return y;
  }

  public void setX(int x) {
    this.x = x;
  }

  public void setY(int y) {
    this.y = y;
  }

  public int dotProduct(LDVector vector) {
    return this.getX() * vector.getX() + this.getY() * vector.getY();
  }

  public void add(LDVector vector) {
    x += vector.getX();
    y += vector.getY();
  }

  public void multiply(LDVector vector) {
    x *= vector.getX();
    y *= vector.getY();
  }
}
