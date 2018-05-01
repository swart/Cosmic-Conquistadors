public class Bunker extends DefaultCritter {
  
  public Bunker() {
    super();
    pictureFilename = "bunker.png";
  }
  
  public Bunker(double x, double y, double v, double theta, String filename) {
    super(x, y, v, theta, filename);
  }
  
  /**
   * Checks if this DefaultCritter collides with another DefaultCritter.
   */
  @Override
  public boolean collide(DefaultCritter c) {
    DefaultCritter critter = c;
    if((critter.getX() - 1 <= x && x <= critter.getX() + 1) && 
       (critter.getY() - 1 <= y && y <= critter.getY() + 1)) {
      return true;
    }
    else 
      return false;
  }  
}
