public interface Critter {

  public void move();

  public void rotate(double theta);

  public double getX();

  public double getY();

  public double getAngle();

  public int getRecharge();

  public void setX(double x);

  public void setY(double y);

  public void setV(double v);

  public void setAngle(double theta);

  public void setRecharge(int time);

  public boolean collide(DefaultCritter c);

  public void draw();
}
