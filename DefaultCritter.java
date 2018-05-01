public class DefaultCritter implements Critter {

  protected double x;
  protected double y;
  protected double v;
  protected double angle;
  protected int recharge;
  protected String pictureFilename;

  // Constructor for DefaultCritter
  public DefaultCritter() {
    this.x = 0;
    this.y = 0;
    this.v = 0;
    this.angle = 0;
    this.recharge = 0;
    this.pictureFilename = "default.png";
  }

  // Constructor for DefaultCritter
  public DefaultCritter(double x, double y, double v, double theta, String filename) {
    this.x = x;
    this.y = y;
    this.v = v;
    this.angle = theta;
    this.recharge = 0;
    this.pictureFilename = filename;
  }

  /**
   * Moves DefaultCritter forward in its direction.
   */
  public void move() {
    double stepX = -v*Math.sin(Math.toRadians(angle));
    double stepY = v*Math.cos(Math.toRadians(angle));
    if((0 <= x + stepX && x + stepX <= InvadersGameState.SCALE) && (0 <= y + stepY && y + stepY <= InvadersGameState.SCALE)) {
      x += stepX;
      y += stepY;
    }
  }

  /**
   * Moves DefaultCritter forward in its direction.
   * 
   * @param theta The degrees of rotation.
   */
  public void rotate(double theta) {
    if(-90 <= this.angle + theta && this.angle + theta <= 90)
      this.angle += theta;
  }
  
  /**
   * Returns the horizontal coordinate x.
   */
  public double getX() {
    return x;
  }
  
  /**
   * Returns the vertical coordinate y.
   */
  public double getY() {
    return y;
  }
  
  /**
   * Returns the current direction.
   */
  public double getAngle() {
   return angle;
  }
  
  /**
   * Returns the time left (in ms) before a missile is able to shoot again.
   */
  public int getRecharge() {
    return recharge;
  }

  /**
   * Set the horizontal coordinate x.
   */
  public void setX(double x) {
    this.x = x;
  }
  
  /**
   * Set the vertical coordinate y.
   */
  public void setY(double y) {
    this.y = y;
  }
  
  /**
   * Set the direction to a certain angle.
   */
  public void setAngle(double theta) {
    this.angle = theta;
  }
  
  /**
   * Set the velocity.
   */
  public void setV(double v) {
    this.v = v;
  }
  
  /**
   * Set the time left (in ms) before a missile is able to shoot again.
   */
  public void setRecharge(int time) {
    this.recharge = time;
  }

  /**
   * Collision between two DefaultCritter.
   * 
   * @param c Receives DefaultCritter as argument.
   * @return true if that DefaultCritter is in contact with this one.
   */
  public boolean collide(DefaultCritter c) {
    DefaultCritter critter = c;
    if((critter.getX() - 3 <= this.x && this.x <= critter.getX() + 3) &&
       (critter.getY() - 3 <= this.y && this.y <= critter.getY() + 3)) {
      return true;
    }
    else
      return false;
  }

  /**
   * Shows a picture that looks like the DefaultCritter to the screen.
   */
  public void draw() {
    StdDraw.picture(x, y, pictureFilename, angle);
  }
}
