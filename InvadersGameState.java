import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.awt.Font;

public class InvadersGameState {
  
  // Scale of the canvas.
  public static final int SCALE = 100;
  // The time (in ms) for each pause.
  public static final int TIME = 10;
  
  private ArrayList<Missile> missiles;
  private ArrayList<Enemy> enemies;
  private ArrayList<Bunker> bunkers;  
  private Turret turret;  
  private Shooter shooter;
  private int score;
  private int lives;
  private int level;
  
  // Constructor for InvadersGameState
  public InvadersGameState() {
    missiles = new ArrayList<Missile>();
    enemies = new ArrayList<Enemy>();  
    bunkers = new ArrayList<Bunker>();
    turret = new Turret(SCALE/2, 5, 0, 0, "shooter.png");
    shooter = new Shooter(SCALE/2, 5, 0, 0, "shooter.png");
    createEnemies();
    createBunkers();
    score = 0;
    lives = 3;
    level = 1;  
  }
  
  // Constructor for InvadersGameState, with arguments for advancing to the next level.
  public InvadersGameState(int score, int level, int lives, ArrayList<Bunker> bunkers) {
    this();
    this.score = score;
    this.lives = lives;
    this.level = level;
    this.bunkers = bunkers;
  }
  
  /**
   * Create enemies in arraylist.
   */
  public void createEnemies() {
    for(int i = 0; i < 4; i++) {
      for(int j = 0; j < 8; j++)
        enemies.add(new Enemy(j*8 + 5, SCALE - i*8 - 10, 0.1 + 0.05*level, -90, "enemy_" + i/2 + ".png"));
    }
  }
  
  /**
   * Create bunkers in arraylist.
   */
  public void createBunkers() {
    for(int i = 0; i < 4; i++) {
      for(int j = 0; j < 5; j++) {
        bunkers.add(new Bunker(SCALE*0.2 - 3 + 1.68*j, 20 - 1.68*i, 0, 0, "bunker.png"));
        bunkers.add(new Bunker(SCALE*0.5 - 3 + 1.68*j, 20 - 1.68*i, 0, 0, "bunker.png"));
        bunkers.add(new Bunker(SCALE*0.8 - 3 + 1.68*j, 20 - 1.68*i, 0, 0, "bunker.png"));
      }
    }
  }  
    
  /**
   * Game loop
   */
  public void gameloop() {
    collision();
    input(); //moves turret
    update(); //moves enemies, enemies shoot, moves missiles, recharge++
    draw();
  }
    
  /**
   * Detects and manage collisions with critters.
   */
  private void collision() {
    // If enemies touch the ground or shooter, automatically end game by setting lives to -1.
    for(int j = 0; j < enemies.size(); j++) {
      if(missiles.size() != 0 && enemies.size() != 0 && enemies.get(j).collide(turret) || enemies.get(j).getY() <= 3) {
        lives = -1;
      }
    }    
    // Removes any  missile that is touching the edges.
    for(int i = 0; i < missiles.size(); i++) {  
      if(missiles.size() != 0 &&
         (2 >= missiles.get(i).getX() || missiles.get(i).getX() >= SCALE - 2) || 
         (2 >= missiles.get(i).getY() || missiles.get(i).getY() >= SCALE - 2)) {
        missiles.remove(i);
      }
    }
    // If missile touches the shooter, decrement 1 life and remove missile.
    for(int i = 0; i < missiles.size(); i++) {  
      if(missiles.size() != 0 && missiles.get(i).collide(turret)) {
        missiles.remove(i);
        lives--;  
      }
    }
    // If missile touches any enemy, remove both missile and enemy.
    for(int i = 0; i < missiles.size(); i++) {  
      for(int j = 0; j < enemies.size(); j++) {
        if(missiles.size() != 0 && enemies.size() != 0 && missiles.get(i).collide(enemies.get(j))) {
          enemies.remove(j);
          missiles.remove(i);
          score++;
          break;
        }
      }      
    }    
    // If missile touches any bunker, remove both missile and bunker.
    for(int i = 0; i < missiles.size(); i++) {  
      for(int j = 0; j < bunkers.size(); j++) {
        if(missiles.size() != 0 && bunkers.size() != 0 && bunkers.get(j).collide(missiles.get(i))) {
          bunkers.remove(j);
          missiles.remove(i);
          break;
        }
      }   
    }
  }
  
  /**
   * Turret and shooter movement and position; shooter rotation and shooting missiles from shooter.
   */
  private void input() {
    turret.setV(0);
    // Move right if right arrow is pressed.
    if(StdDraw.isKeyPressed(KeyEvent.VK_RIGHT)) {
      turret.setV(0.5);
      turret.setAngle(-90);
    }
    // Move left if left arrow is pressed.    
    if(StdDraw.isKeyPressed(KeyEvent.VK_LEFT)) {
      turret.setV(0.5);
      turret.setAngle(90);           
    }      
    // Rotate right if up arrow is pressed.    
    if(StdDraw.isKeyPressed(KeyEvent.VK_UP)) {
      shooter.rotate(1.5);
    }
    // Rotate left if down arrow is pressed.  
    if(StdDraw.isKeyPressed(KeyEvent.VK_DOWN)) {
      shooter.rotate(-1.5);
    }
    // Shoot a new missile from shooter.
    if((StdDraw.isKeyPressed(KeyEvent.VK_SPACE)) && shooter.getRecharge() >= 350) {
      missiles.add(new Missile(shooter.getX(), shooter.getY() + 3.5, 0.5, shooter.getAngle(), "missile_0.png"));
      shooter.setRecharge(0);
    }
  }
  
  /**
   * Updates movement, updates recharges, enemies might shoot back.
   */
  private void update() {
    // When enemies near edge, flip direction (from right -> left or left -> right) and move downwards.
    if(enemies.size() != 0) {
      for(int i = 0; i < enemies.size(); i++) {
        if(3 >= enemies.get(i).getX()) {
          for(int z = 0; z < enemies.size(); z++) {
            enemies.get(z).setAngle(-90);
            enemies.get(z).setY(enemies.get(z).getY() - 2);        
          }
        }
        if(enemies.get(i).getX() >= SCALE - 3) {
          for(int w = 0; w < enemies.size(); w++) {
            enemies.get(w).setAngle(90);
            enemies.get(w).setY(enemies.get(w).getY() - 2);
          }
        }
      }
      // move all enemies forward
      for(int i = 0; i < enemies.size(); i++) {
        enemies.get(i).move();
      }
    
      // move all missiles forward
      for(int i = 0; i < missiles.size(); i++) {
        missiles.get(i).move();
      }
    
      // move turret and shooter
      turret.move();
      shooter.setX(turret.getX());
    
      // Enemies shoot back; only shoots when there's no enemy below and if it's fully recharged.
      Enemy randEnemy = enemies.get((int)(Math.random()*enemies.size()));
      Missile randMissile = new Missile(randEnemy.getX(), randEnemy.getY() - 8, 0.5, 180, "missile_1.png");
      Missile temp1 = new Missile(randEnemy.getX(), randEnemy.getY() - 16, 0.5, 180, "missile_1.png");
      Missile temp2 = new Missile(randEnemy.getX(), randEnemy.getY() - 24, 0.5, 180, "missile_1.png");  
      // Checks if there is any enemy below, sets flag if true.    
      boolean collideFlag = false;
      for(int i = 0; i < enemies.size(); i++) {
        if(randMissile.collide(enemies.get(i)) || temp1.collide(enemies.get(i)) || temp2.collide(enemies.get(i))) {
          collideFlag = true;
          break;
        }
      } // If flag is not set missile will be shot.
      if(!collideFlag && (Math.random() <= level/(5.0*enemies.size())) && randEnemy.getRecharge() >= 500) {
        missiles.add(randMissile);
        randEnemy.setRecharge(0);
      }
    
      // Updates recharges of the enemies and shooter.
      for(int i = 0; i < enemies.size(); i++) {
        if(enemies.get(i).getRecharge() <= 500) {
          enemies.get(i).setRecharge(enemies.get(i).getRecharge() + TIME);
        }
      }
      if(shooter.getRecharge() <= 350) {
        shooter.setRecharge(shooter.getRecharge() + TIME);
      }
    }
  }
  
  /**
   * Draws all graphics
   */
  private void draw() {
    ArrayList<Critter> all = new ArrayList<Critter>();
    all.addAll(enemies);
    all.addAll(missiles);
    all.addAll(bunkers);
    all.add(shooter);
    
    Invaders.background();
    
    for(int i = 0; i < all.size(); i++) {
      all.get(i).draw();      
    }
    // display score, level and lives
    Invaders.status(score, level, lives);
    StdDraw.show();
    StdDraw.pause(TIME);
  }
  
  /**
   * Checks if the turret is alive.
   */
  public boolean isAlive() {
    if(lives >= 0) 
      return true;
    else 
      return false;
  }
  
  /**
   * Checks if level has been completed, i.e. no enemy is alive.
   */
  public boolean isLevelComplete() {
    if(enemies.size() == 0) {
      level++;
      return true;
    }
    else 
      return false;   
  }
  
  /**
   * Return score.
   */
  public int getScore() {
    return score;
  }
  
  /**
   * Return level.
   */
  public int getLevel() {
    return level;
  }  
  
  /**
   * Returns lives.
   */
  public int getLives() {
    return lives;
  }
  
  /**
   * Returns ArrayList of current Bunkers.
   */
  public ArrayList<Bunker> getBunkers() {
    return bunkers;
  }
  
}
