import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.awt.Font;

public class Invaders {
  
  public static void main(String[] args) {
    
    // Setup the canvas and scale.
    StdDraw.setCanvasSize(600, 600);
    StdDraw.setScale(0, InvadersGameState.SCALE);
    StdDraw.enableDoubleBuffering();  
    
    // Declaration of the game state.
    InvadersGameState game = new InvadersGameState(); 
    
    // Starts game music
    StdAudio.loop("music.wav");       
   
    // Displays title screen until the player starts or quits the game .
    while(!StdDraw.isKeyPressed(KeyEvent.VK_SPACE) && !StdDraw.isKeyPressed(KeyEvent.VK_Q)) {
      title();
    }
    
    // Game loop
    while(!StdDraw.isKeyPressed(KeyEvent.VK_Q)) {
      game.gameloop();
      // Restart game if the player is not alive.
      if(!game.isAlive()) {
        gameover();
        game = new InvadersGameState();   
      }
      // Advance to next level; resets enemies while keeping score, level, lives and bunkers.
      if(game.isLevelComplete()) {
        int score = game.getScore();
        int level = game.getLevel();
        int lives = game.getLives() + 1; // extra life for completing level.
        ArrayList<Bunker> bunkers = game.getBunkers();
        game = new InvadersGameState(score, level, lives, bunkers);
      }
    }
    System.exit(0);
  }

  /**
   * Displays the title screen.
   */
  public static void title() {
    background();
    Font gameFont = new Font("Times New Roman", Font.BOLD,35);
    StdDraw.setPenColor(StdDraw.YELLOW);
    StdDraw.setFont(gameFont);
    StdDraw.text(InvadersGameState.SCALE/2, 80, "COSMIC CONQUISTADORS");
    gameFont = new Font("Times New Roman", Font.BOLD, 20);
    StdDraw.setFont(gameFont);
    //Draws the rest of the start screen text.
    StdDraw.text(InvadersGameState.SCALE/2, 70, "Press Space to Save The World!");
    StdDraw.text(InvadersGameState.SCALE/2, 60, "Shoot (space)");
    StdDraw.text(InvadersGameState.SCALE/2, 50, "Rotate: Left (up), Stop (release key), Right (down)");
    StdDraw.text(InvadersGameState.SCALE/2, 40, "Move: Left (left), Stop (release key), Right (right)");
    StdDraw.text(InvadersGameState.SCALE/2, 20, "Quit (q)");
    StdDraw.show();
  }
  
  /**
   * Displays the background.
   * 
   * https://blogs-images.forbes.com/erikkain/files/2016/12/Rogue-One-Death-Star.jpg
   */
  public static void background() {
    StdDraw.picture(InvadersGameState.SCALE/2, InvadersGameState.SCALE/2, "background.png");
  }
  
  /**
   * Displays the game over message.
   */
  public static void gameover() {
    Font gameFont = new Font("Times New Roman", Font.BOLD,75); // creates a font type.
    StdDraw.setPenColor(StdDraw.RED); // sets the pen colour to yellow.
    StdDraw.setFont(gameFont); // sets the font type to the created font.
    StdDraw.text(InvadersGameState.SCALE/2, InvadersGameState.SCALE/2, "GAME OVER!");
    StdDraw.show();
    StdDraw.pause(4000); // Shows game over for 4 seconds. 
  }
  
  /**
   * Text that shows the current score, level and lives of the player.
   */
  public static void status(int score, int level, int lives) {
    Font font = new Font("Arial", Font.BOLD, 20);
    StdDraw.setPenColor(StdDraw.WHITE);
    StdDraw.setFont(font);
    StdDraw.text(InvadersGameState.SCALE/2, InvadersGameState.SCALE - 5, "SCORE<" + score + ">    LEVEL<" + level + ">    LIVES<" + lives + ">");    
  }
  
}
