public class Missile extends DefaultCritter {
  
  public Missile() {
    super();
    pictureFilename = "missile.png";
  }
  
  public Missile(double x, double y, double v, double theta, String filename) {
    super(x, y, v, theta, filename);
  }
}