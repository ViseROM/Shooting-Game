import java.awt.Graphics2D;
import java.awt.*;

/**
 * The bullet class. Describes the attributes of a bullet
 *
 * @author Vachia Thoj
 * @version 1.0
 */
public class Bullet
{
    //x and y position of the bullet
    private double x;
    private double y;
    
    //Change in x and y position of bullet
    private double dx;
    private double dy;
    
    //Radius of the bullet
    private int radius;
    
    private double radians;
    
    //The speed of the bullet
    private double speed;
    
    public Bullet(double x, double y, double angle)
    {
        this.x = x;
        this.y = y;
        radius = 5;
        radians = Math.toRadians(angle);
        speed = 15;
        dx = Math.cos(radians) * speed;
        dy = Math.sin(radians) * speed;
    }
    
    public double getX() {return x;}
    public double getY() {return y;}
    public double getDx() {return dx;}
    public double getDy() {return dy;}
    public int getRadius() {return radius;}
    public double speed() {return speed;}
    
    public void setX(double x) {this.x = x;}
    public void setY(double y) {this.y = y;}
    public void setDx(double dx) {this.dx = dx;}
    public void setDy(double dy) {this.dy = dy;}
    public void setRadius(int radius) {this.radius = radius;}
    public void setSpeed(double speed) {this.speed = speed;}
    
    
    /**
     * Updates for the bullet
     */ 
    public void update()
    {
        x += dx;
        y += dy;
    }
    
    /**
     * Draws the bullet
     *
     * @param  g  Graphics2D
     */ 
    public void draw(Graphics2D g)
    {
        g.setColor(Color.YELLOW);
        g.fillOval((int) (x - radius), (int) (y - radius), 2 * radius, 2 * radius);
    }
    
    /**
     * Checks if bullet is touching the edge of the window/screen
     *
     * @return    TRUE if bullet is touching or beyond window/screen, otherwise FALSE
     */ 
    public boolean isTouchingEdge()
    {
        if(x < -radius || x > GamePanel.WIDTH + radius ||
           y < (radius + 100) || y > GamePanel.HEIGHT + radius)
        {
            return true;
        }
        return false;
    }
}
