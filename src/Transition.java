import java.awt.Graphics2D;
import java.awt.*;

/**
 * Write a description of class Transition here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Transition
{
    private double x;
    private double y;
    private double width;
    private double height;
    private double speed;
    private int type;
    
    /**
     * Constructor for objects of class Transition
     */
    public Transition(double x, double y, double width, double height, double speed, int type)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.type = type;
    }
    
    public double getX() {return x;}
    public double getY() {return y;}
    public double getWidth() {return width;}
    public double getHeight() {return height;}
    public double getSpeed() {return speed;}
    public double getType() {return type;}
    
    public void setX(double x) {this.x = x;}
    public void setY(double y) {this.y = y;}
    public void setWidth(double width) {this.width = width;}
    public void setHeight(double height) {this.height = height;}
    public void setSpeed(double speed) {this.speed = speed;}
    public void setType(int type) {this.type = type;}
    
    public void update()
    {
        width += speed;
    }
    
    public void draw(Graphics2D g)
    {
        g.setColor(Color.BLACK);
        if(type == 1)
        {
            g.drawRect((int) x, (int) y, (int) width, (int) height);
            g.fillRect((int) x, (int) y, (int) width, (int) height);
        }
        else
        {
            g.drawRect((int) (GamePanel.WIDTH - width), (int) y, (int) width, (int) height);
            g.fillRect((int) (GamePanel.WIDTH - width), (int) y, (int) width, (int) height);
        }
    }
}
