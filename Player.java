import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class Player implements KeyListener {

  // Movement variables
  private double x, y;
  private char direction = 's';
  private double velocityX = 0;
  private double velocityY = 0;

  // Animation array sizes
  int walkSize = PlayerAssets.upAnimations.size();
  int pickupSize = PlayerAssets.pickUpDownAnimations.size();
  int currPlayerWalk = 0;

  // Movement logic
  boolean stopped = true;
  char nextDirection = direction;

  // Hotbar variables
  int[] hotbar = {0, 0};

  public Player(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public void updateDirectionMovements() {
    if (direction == 'w') {
      setVelocityX(0);
      setVelocityY(-0.25);
    } else if (direction == 's') {
      setVelocityX(0);
      setVelocityY(0.25);

    } else if (direction == 'a') {
      setVelocityX(-0.25);
      setVelocityY(0);

    } else if (direction == 'd') {
      setVelocityX(0.25);
      setVelocityY(0);
    }
  }

  public void update() {
    if (x % 1 == 0 && y % 1 == 0) {
      if (stopped) {
        setVelocityX(0);
        setVelocityY(0);
        currPlayerWalk = 0;
      } else {
        direction = nextDirection;
        updateDirectionMovements();
      }
    }
    x += velocityX;
    y += velocityY;
    currPlayerWalk += Math.max(Math.abs(velocityX * 4), Math.abs(velocityY * 4));
    currPlayerWalk %= walkSize;
  }

  public void render(Graphics g) {
    int nextX = (int) (x * 64), nextY = (int) (y * 64);
    // Walk animations
    if (getDirection() == 'w') {
      g.drawImage(PlayerAssets.upAnimations.get(currPlayerWalk), nextX, nextY, 64, 64, null,
          null);
    } else if (getDirection() == 'a') {
      g.drawImage(PlayerAssets.leftAnimations.get(currPlayerWalk), nextX, nextY, 64, 64, null,
          null);
    } else if (getDirection() == 'd') {
      g.drawImage(PlayerAssets.rightAnimations.get(currPlayerWalk), nextX, nextY, 64, 64,
          null,
          null);
    } else if (getDirection() == 's') {
      g.drawImage(PlayerAssets.downAnimations.get(currPlayerWalk), nextX, nextY, 64, 64,
          null,
          null);
    }

  }

  public void renderGrab(Graphics g, int grabIndex) {
    int nextX = (int) (x * 64), nextY = (int) (y * 64);
    if (getDirection() == 'w') {
      g.drawImage(PlayerAssets.pickUpUpAnimations.get(grabIndex), nextX, nextY, 64, 64, null,
          null);
    } else if (getDirection() == 'a') {
      g.drawImage(PlayerAssets.pickUpLeftAnimations.get(grabIndex), nextX, nextY, 64, 64, null,
          null);
    } else if (getDirection() == 'd') {
      g.drawImage(PlayerAssets.pickUpRightAnimations.get(grabIndex), nextX, nextY, 64, 64,
          null,
          null);
    } else if (getDirection() == 's') {
      g.drawImage(PlayerAssets.pickUpDownAnimations.get(grabIndex), nextX, nextY, 64, 64,
          null,
          null);
    }
  }

  public void grab(Graphics g, BufferedImage[] backgroundTile) {
    if (!stopped)
      return;

    int FPS = 15;
    double timePerTick = 1000000000 / FPS;
    double delta = 0;
    long lastTime = System.nanoTime();
    int currentFrame = 0;

    while (currentFrame < pickupSize) {
      long now = System.nanoTime();
      delta += (now - lastTime) / timePerTick;
      lastTime = now;

      if (delta >= 1) {
        for (BufferedImage background : backgroundTile) {
          g.drawImage(background, (int) (x * 64), (int) (y * 64), 64, 64, null, null);
        }
        int nextX = (int) (x * 64);
        int nextY = (int) (y * 64);
        if (getDirection() == 'w') {
          g.drawImage(PlayerAssets.pickUpUpAnimations.get(currentFrame), nextX, nextY, 64, 64, null, null);
        } else if (getDirection() == 'a') {
          g.drawImage(PlayerAssets.pickUpLeftAnimations.get(currentFrame), nextX, nextY, 64, 64, null,
              null);
        } else if (getDirection() == 'd') {
          g.drawImage(PlayerAssets.pickUpRightAnimations.get(currentFrame), nextX, nextY, 64, 64,
              null,
              null);
        } else if (getDirection() == 's') {
          g.drawImage(PlayerAssets.pickUpDownAnimations.get(currentFrame), nextX, nextY, 64, 64,
              null,
              null);
        }
        currentFrame++;
        delta--;
      }
    }

  }

  @Override
  public void keyTyped(KeyEvent e) {

  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (e.getKeyChar() == 'w' || e.getKeyChar() == 'W' || e.getKeyCode() == 38) {
      nextDirection = 'w';
      stopped = false;
    }
    // Movement direction (back)
    // set to the letter s or down arrow on the keyboard
    if (e.getKeyChar() == 's' || e.getKeyChar() == 'S' || e.getKeyCode() == 40) {
      nextDirection = 's';
      stopped = false;
    }
    // Movement direction (left)
    // set to the letter a or left arrow on the keyboard
    if (e.getKeyChar() == 'a' || e.getKeyChar() == 'A' || e.getKeyCode() == 37) {
      nextDirection = 'a';
      stopped = false;
    }
    // Movement direction (right)
    // set to the letter d or right arrow on the keyboard
    if (e.getKeyChar() == 'd' || e.getKeyChar() == 'D' || e.getKeyCode() == 39) {
      nextDirection = 'd';
      stopped = false;
    }

  }

  @Override
  public void keyReleased(KeyEvent e) {
    if (e.getKeyChar() == 'w' || e.getKeyChar() == 'W' || e.getKeyCode() == 38) {
      stopped = true;
    }
    // Movement direction (back)
    // set to the letter s or down arrow on the keyboard
    if (e.getKeyChar() == 's' || e.getKeyChar() == 'S' || e.getKeyCode() == 40) {
      stopped = true;
    }
    // Movement direction (left)
    // set to the letter a or left arrow on the keyboard
    if (e.getKeyChar() == 'a' || e.getKeyChar() == 'A' || e.getKeyCode() == 37) {
      stopped = true;
    }
    // Movement direction (right)
    // set to the letter d or right arrow on the keyboard
    if (e.getKeyChar() == 'd' || e.getKeyChar() == 'D' || e.getKeyCode() == 39) {
      stopped = true;
    }
  }

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }

  public void setDirection(char direction) {
    this.direction = direction;
  }

  public char getDirection() {
    return this.direction;
  }

  public void setVelocityX(double velocity) {
    this.velocityX = velocity;
  }

  public void setVelocityY(double velocity) {
    this.velocityY = velocity;
  }

}
