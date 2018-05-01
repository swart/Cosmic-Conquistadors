public class Shooter extends DefaultCritter {
  
  public Shooter() {
    super();
    pictureFilename = "shooter.png";
  }
  
  public Shooter(double x, double y, double v, double theta, String filename) {
    super(x, y, v, theta, filename);
  }
}
