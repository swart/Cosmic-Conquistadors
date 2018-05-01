public class Enemy extends DefaultCritter {
  
  public Enemy() {
    super();
    pictureFilename = "enemy.png";
  }
  
  public Enemy(double x, double y, double v, double theta, String filename) {
    super(x, y, v, theta, filename);
  }
  
  /**
   * Enemy should only be drawn upwards, ignoring direction.
   */
  @Override
  public void draw() {
    StdDraw.picture(this.getX(), this.getY(), pictureFilename);
  }
}
