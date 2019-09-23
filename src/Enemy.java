import java.awt.Graphics2D;
import java.awt.*;
/**
 * The Enemy class. Describes the attributes of an enemy
 *
 * @author Vachia Thoj
 * @version 1.0
 */
public class Enemy
{
    //x and y coordinate of enemy
    private double x;
    private double y;
    
    //Change in x and y position of enemy
    private double dx;
    private double dy;
    
    //The radius of the enemy
    private int radius;
    
    //The speed that the enemy moves at
    private double speed;
    
    //Type of enemy and its rank
    private int type;
    private int rank;
    
    //Number of lives the enemy has
    private int lives;
    
    //Color of enemy
    private Color color1;
    private Color color2;
    
    private boolean ready;
    private boolean hit;
    private long hitTimer;
    
    private boolean slow;
    
    /**
     * Constructor for objects of class Enemy
     */
    public Enemy(int type, int rank)
    {
        this.type = type;
        this.rank = rank;
        
        if(type == 1)
        {
            color1 = Color.BLUE;
            if(rank == 1)
            {
                speed = 6;
                radius = 10;
                lives = 1;
            }
            else if(rank == 2)
            {
                speed = 4;
                radius = 15;
                lives = 1;
            }
            else if(rank == 3)
            {
                speed = 4;
                radius = 25;
                lives = 2;
            }
        }
        else if(type == 2)
        {
            color1 = Color.GREEN;
            if(rank == 1)
            {
                speed = 8;
                radius = 10;
                lives = 1;
            }
            else if(rank == 2)
            {
                speed = 6;
                radius = 15;
                lives = 2;
            }
        }
        else if(type == 3)
        {
            color1 = Color.GRAY;
            if(rank == 1)
            {
                speed = 3;
                radius = 15;
                lives = 3;
            }
            else if(rank == 2)
            {
                speed = 3;
                radius = 35;
                lives = 5;
            }
        }
        color2 = Color.WHITE;
        
        x = Math.random() * GamePanel.WIDTH / 2 + GamePanel.WIDTH / 4;
        y = (radius + 100);
        
        double angle = Math.random() * 140 + 20;
        double radians = Math.toRadians(angle);
        
        dx = Math.cos(radians) * speed;
        dy = Math.sin(radians) * speed;
        
        ready = false;
        hit = false;
        hitTimer = 0;
        
        slow = false;
    }
    
    public double getX() {return x;}
    public double getY() {return y;}
    public double getDx() {return dx;}
    public double getDy() {return dy;}
    public int getRadius() {return radius;}
    public double getSpeed() {return speed;}
    public int getType() {return type;}
    public int getRank() {return rank;}
    public int getLives() {return lives;}
    public boolean gotHit() {return hit;}
    public boolean isSlow() {return slow;}
    
    public void setX(double x) {this.x = x;}
    public void setY(double y) {this.y = y;}
    public void setDx(double dx) {this.dx = dx;}
    public void setDy(double dy) {this.dy = dy;}
    public void setRadius(int radius) {this.radius = radius;}
    public void setSpeed(double speed) {this.speed = speed;}
    public void setLives(int lives) {this.lives = lives;}
    public void setHit(boolean b) {this.hit = b;}
    public void setSlow(boolean b) {slow = b;}
    
    public void update()
    {
        
        if(slow == true)
        {
            x += dx * 0.5;
            y += dy * 0.5;
        }
        else
        {
            x += dx;
            y += dy;
        }
        
        
        if(ready == false)
        {
            if(x > radius && x < GamePanel.WIDTH - radius &&
               y > (radius + 100) && y < GamePanel.HEIGHT - radius)
            {
                ready = true;
            }
        }
        
        checkBoundaries();
        
        if(hit == true)
        {
            long elapsedTime = (System.nanoTime() - hitTimer) / 1000000;
            if(elapsedTime > 50)
            {
                hit = false;
                hitTimer = 0;
            }
        }
    }
    
    public void draw(Graphics2D g)
    {
        if(hit == false)
        {
            g.setColor(color1);
            g.fillOval((int) (x - radius), (int) (y - radius), 2 * radius, 2 * radius);
            g.setStroke(new BasicStroke(3));
            g.setColor(color1.darker());
            g.drawOval((int) (x - radius), (int) (y - radius), 2 * radius, 2 * radius);
            g.setStroke(new BasicStroke(1));
        }
        else
        {
            g.setColor(color2);
            g.fillOval((int) (x - radius), (int) (y - radius), 2 * radius, 2 * radius);
            g.setStroke(new BasicStroke(3));
            g.setColor(color2.darker());
            g.drawOval((int) (x - radius), (int) (y - radius), 2 * radius, 2 * radius);
            g.setStroke(new BasicStroke(1));
        }
    }
    
    private void checkBoundaries()
    {
        if(x < radius && dx < 0)
        {
            dx = -dx;
        }
        
        if(x > GamePanel.WIDTH - radius && dx > 0)
        {
            dx = -dx;
        }
        
        if(y < (radius + 100) && dy < 0)
        {
            dy = -dy;
        }
        
        if(y > GamePanel.HEIGHT - radius && dy > 0)
        {
            dy = -dy;
        }
    }
    
    public void loseLife(int damage)
    {
        lives -= damage;
        
        hit = true;
        hitTimer = System.nanoTime();
        if(lives < 0)
        {
            lives = 0;
        }
    }
    
    public void explode()
    {
        if(rank > 1)
        {   
            int amount;
            
            if(type == 1)
            {
                amount = 2;
            }
            else
            {
                amount = 3;
            }
            
            for(int i = 0; i < amount; i++)
            {
                Enemy e = new Enemy(getType(), getRank() - 1);
                e.x = this.x;
                e.y = this.y;
                PlayState.enemies.add(e);
            }
        }
    }
}
