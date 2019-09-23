import java.awt.Graphics2D;
import java.awt.*;

/**
 * The Explosion class. Describes the attributes of an explosion.
 * For this game, this class is used to create a circular explosion 
 * animation for when an enemy dies.
 * 
 * @author Vachia Thoj
 * @version 1.0
 */
public class Explosion
{
    //x and y position of the explosion
    private double x;
    private double y;
    
    //The radius of the explosion
    private int radius;
    
    //The maximum radius the explosion can reach
    private int maxRadius;
    
    public Explosion(double x, double y, int radius)
    {
        this.x = x;
        this.y = y;
        this.radius = radius;
        maxRadius = 5 * radius;
    }
    
    public double getX() {return x;}
    public double getY() {return y;}
    public int getRadius() {return radius;}
    public int getMaxRadius() {return maxRadius;}
    
    public void setX(double x) {this.x = x;}
    public void setY(double y) {this.y = y;}
    public void setRadius(int radius) {this.radius = radius;}
    public void setMaxRadius(int maxRadius) {this.maxRadius = maxRadius;}
    
    public void update()
    {
        radius += 2;
    }
    
    /**
     * Draws the explosion
     *
     * @param  g  Graphics2D
     */ 
    public void draw(Graphics2D g)
    {
        g.setColor(Color.WHITE);
        g.setStroke(new BasicStroke(3));
        g.drawOval((int) (x - radius), (int) (y - radius), 2 * radius, 2 * radius);
    }
}
