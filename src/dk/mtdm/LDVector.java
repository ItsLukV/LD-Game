package dk.mtdm;
/**
 * a class capable of operating either  as a point in space or a vector of movement.<br>
 * contains the methods needed to move change the vector.
 */
public class LDVector {
  private int x;
  private int y;

  public LDVector(int x, int y) {
    this.x = x;
    this.y = y;
  }
  /**
   * @return an identical LDVector
   */
  public LDVector copy(){
    return new LDVector(x, y);
  }
  /**
   * @param vector the LDVector that is to be used in the equation together with this vector
   * @return the dotProduct of the vector
   */
  public int dotProduct(LDVector vector) {
    return this.getX() * vector.getX() + this.getY() * vector.getY();
  }
  /**
   * increases the size of this vector by the given vector
   * @param vector the vector to be added into this vector
   */
  public void add(LDVector vector) {
    x += vector.getX();
    y += vector.getY();
  }
  /**
   * multiplies the size of this vector by the given vector
   * @param vector the vector to be multiplied into this vector
   */
  public void multiply(LDVector vector) {
    x *= vector.getX();
    y *= vector.getY();
  }
  public int getY() {
    return y;
  }
  public int getX() {
    return x;
  }
  public void setX(int x) {
    this.x = x;
  }
  public void setY(int y) {
    this.y = y;
  }
}
