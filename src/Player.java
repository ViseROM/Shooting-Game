import java.awt.Graphics2D;
import java.awt.*;

/**
 * The player class. Describes the attributes a player has.
 *
 * @author Vachia Thoj
 * @version 1.0
 */
public class Player
{   
    private static Player instance = null;
    
    //x and y position of player
    private double x;
    private double y;
    
    //Change in x and y position of player
    private double dx;
    private double dy;
    
    //The radius of the player
    private int radius;
    
    //The speed that the player moves at
    private double speed;
    
    //Number of lives the player has
    private final int MIN_LIVES = 0;
    private final int MAX_LIVES = 6;
    private int lives;
    
    //To keep track if player has been hit
    private boolean hit;
    private long hitTimer;
    private long hitDelay;
    
    //Flag that tells if player is moving in a certain direction
    private boolean left;
    private boolean right;
    private boolean up;
    private boolean down;
    
    //For firing gun
    private boolean firing;
    private long firingTimer;
    private long firingDelay;
    private boolean disabled;
    
    //Strength of player's gun
    private final int MAX_POWER_LEVEL = 4;
    private int powerLevel;
    private int power;
    private int[] requiredPower;
    
    //To keep track of player score
    private final int MAX_SCORE = 9999;
    private final int MIN_SCORE = 0;
    private int score;
    
    /**
     * Constructor for objects of class Player
     */
    private Player()
    {
       x = GamePanel.WIDTH / 2;
       y = (GamePanel.HEIGHT + 100) / 2;
       radius = 15;
       speed = 10;
       
       
       left = false;
       right = false;
       up = false;
       down = false;
       
       lives = 3;
       hit = false;
       hitTimer = 0;
       hitDelay = 2000;
       
       firing = false;
       firingTimer = System.nanoTime();
       firingDelay = 400;
       disabled = false;
       
       powerLevel = 0;
       power = 0;
       requiredPower = new int[] {1, 2, 3, 4, 5};
       
       score = 0;
    }
    
    public static Player instance()
    {
        if(instance == null)
        {
            instance = new Player();
        }
        return instance;
    }
    
    public double getX() {return x;}
    public double getY() {return y;}
    public double getDx() {return dx;}
    public double getDy() {return dy;}
    public int getRadius() {return radius;}
    public double getSpeed() {return speed;}
    public double getLives() {return lives;}
    public boolean isHit() {return hit;}
    public boolean isLeft() {return left;}
    public boolean isRight() {return right;}
    public boolean isUp() {return up;}
    public boolean isDown() {return down;}
    public boolean isFiring() {return firing;}
    public int getPowerLevel() {return powerLevel;}
    public int getPower() {return power;}
    public int getRequiredPower() {return requiredPower[powerLevel];}
    public int getScore() {return score;}
    public boolean isDisabled() {return disabled;}
    
    public void setX(double x) {this.x = x;}
    public void setY(double y) {this.y = y;}
    public void setDx(double dx) {this.dx = dx;}
    public void setDy(double dy) {this.dy = dy;}
    public void setRadius(int radius) {this.radius = radius;}
    public void setSpeed(int speed){this.speed = speed;}
    public void setLives(int lives) {this.lives = lives;}
    public void setLeft(boolean b) {left = b;}
    public void setRight(boolean b) {right = b;}
    public void setUp(boolean b) {up = b;}
    public void setDown(boolean b) {down = b;}
    public void setFiring(boolean b) {firing = b;}
    public void setDisabled(boolean b) {disabled = b;}
    
    public void setHit(boolean b) 
    {
        if(b == true)
        {
            hitTimer = System.nanoTime();
        }
        
        hit = b;
    }
    
    public void setScore(int score)
    {
        if(score < MIN_SCORE)
        {
            this.score = MIN_SCORE;
        }
        else if(score > MAX_SCORE)
        {
            this.score = MAX_SCORE;
        }
        else
        {
            this.score = score;
        }
    }
    
    
    public void update()
    {
        shoot();
        
        if(left == true)
        {
            dx = -speed;
        }
        
        if(right == true)
        {
            dx = speed;
        }
        
        if(up == true)
        {
            dy = -speed;
        }
        
        if(down == true)
        {
            dy = speed;
        }
        
        x += dx;
        y += dy;
        
        checkBoundaries();
        
        dx = 0;
        dy = 0;
    }

    public void draw(Graphics2D g)
    {   
        if(hit == true)
        {   
            g.setColor(new Color(255, 0, 0));
            g.setStroke(new BasicStroke(3));
            g.drawOval((int) (x - radius), (int) (y - radius), 2 * radius, 2 * radius);
            g.setColor(new Color(255, 0, 0, 128));
            g.fillOval((int) (x - radius), (int) (y - radius), 2 * radius, 2 * radius);
            
            long elapsed = (System.nanoTime() - hitTimer) / 1000000;
            if(elapsed > hitDelay)
            {
                hit = false;
            }
        }
        else
        {
            g.setColor(new Color(255, 255, 255));
            g.setStroke(new BasicStroke(3));
            g.drawOval((int) (x - radius), (int) (y - radius), 2 * radius, 2 * radius);
            g.setColor(new Color(255, 255, 255, 128));
            g.fillOval((int) (x - radius), (int) (y - radius), 2 * radius, 2 * radius);
        }
    }
    
    private void checkBoundaries()
    {
        if(x < radius)
        {
            x = radius;
        }
        
        if(x > GamePanel.WIDTH - radius)
        {
            x = GamePanel.WIDTH - radius;
        }
        
        if(y < radius)
        {
            y = radius;
        }
        
        if(y > GamePanel.HEIGHT - radius)
        {
            y = GamePanel.HEIGHT - radius;
        }
    }
    
    private void shoot()
    {
        if(disabled == false && firing == true)
        {
            long elapsed = (System.nanoTime() - firingTimer) / 1000000;
            if(elapsed > firingDelay)
            {   
                if(powerLevel < 2)
                {
                    PlayState.bullets.add(new Bullet(x, y, 270));
                }
                else if(powerLevel < 4)
                {
                    PlayState.bullets.add(new Bullet(x - 7, y, 270));
                    PlayState.bullets.add(new Bullet(x + 7, y, 270));
                }
                else if(powerLevel == 4 && power < requiredPower[powerLevel])
                {
                    PlayState.bullets.add(new Bullet(x, y, 270));
                    PlayState.bullets.add(new Bullet(x + 7, y, 275));
                    PlayState.bullets.add(new Bullet(x - 7, y, 265));
                }
                else
                {
                    PlayState.bullets.add(new Bullet(x, y, 270));
                    PlayState.bullets.add(new Bullet(x + 7, y, 275));
                    PlayState.bullets.add(new Bullet(x - 7, y, 265));
                    PlayState.bullets.add(new Bullet(x, y, 180));
                    PlayState.bullets.add(new Bullet(x, y, 360));
                }
                firingTimer = System.nanoTime();
            }
        }
    }
    
    public void increasePower(int amount)
    {
        power += amount;
        if(powerLevel >= MAX_POWER_LEVEL && power >= requiredPower[powerLevel])
        {
            power = requiredPower[powerLevel];
            return;
        }
        
        if(power >= requiredPower[powerLevel])
        {
            ++powerLevel;
            power = 0;
        }
    }
    
    public void increaseScore(int amount)
    {
        score += amount;
        
        if(score < MIN_SCORE)
        {
            score = MIN_SCORE;
        }
        else if(score > MAX_SCORE)
        {
            score = MAX_SCORE;
        }
    }
    
    public void increaseLives(int amount)
    {
        lives += amount;
        
        if(lives < MIN_LIVES)
        {
            lives = MIN_LIVES;
        }
        else if(lives > MAX_LIVES)
        {
            lives = MAX_LIVES;
        }
    }
    
    public void decreaseLives(int amount)
    {
        lives -= amount;
        
        if(lives < MIN_LIVES)
        {
            lives = MIN_LIVES;
        }
        else if(lives > MAX_LIVES)
        {
            lives = MAX_LIVES;
        }
    }
    
    public void clear()
    {
        instance = null;
    }
}
