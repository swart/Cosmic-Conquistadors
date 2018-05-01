public class Turret extends DefaultCritter {
  
  public Turret() {
    super();
    pictureFilename = "turret.png";
  }
  
  public Turret(double x, double y, double v, double theta, String filename) {
    super(x, y, v, theta, filename);
  }
}
