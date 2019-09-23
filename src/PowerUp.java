import java.awt.Graphics2D;
import java.awt.*;

/**
 * The PowerUp class. Describes the attributes of a power up
 *
 * @author Vachia Thoj
 * @version 1.0
 */
public class PowerUp
{
    //The x and y position of a power up
    private double x;
    private double y;
    
    //Change in x and y positon
    private double dx;
    private double dy;
    
    //The radius of the power up
    private int radius;
    
    //The speed the power up moves at
    private double speed;
    
    //Stores type of power up
    private int type;
    
    //The colors of the power up
    private Color color1;
    private Color color2;
    
    public PowerUp(double x, double y, int type)
    {
        this.x = x;
        this.y = y;
        radius = 5;
        speed = 8;
        
        this.type = type;
        switch(type){
            //Add powerUp to gun (Yellow powerUp)
            case 1:     color1 = new Color(204, 204, 0);
                        color2 = new Color(204, 204, 0, 200);
                        break;
            //+1 life (Pink powerUp)
            case 2:     color1 = new Color(255, 102, 255);
                        color2 = new Color(255, 102, 255, 200);
                        break;
            //(Black powerUp)
            case 3:     color1 = new Color(0, 0, 0);
                        color2 = new Color(0, 0, 0, 200);
                        break;
            default:    color1 = new Color(204, 204, 0);
                        color2 = new Color(204, 204, 0, 200);
                        break;
        }
        
        double radians = Math.toRadians(90);
        dx = Math.cos(radians) * speed;
        dy = Math.sin(radians) * speed;
    }
    
    public double getX() {return x;}
    public double getY() {return y;}
    public double getDx() {return dx;}
    public double getDy() {return dy;}
    public int getRadius() {return radius;}
    public double getSpeed() {return speed;}
    public int getType() {return type;}
    
    public void setX(double x) {this.x = x;}
    public void setY(double y) {this.y = y;}
    public void setDx(double dx) {this.dx = dx;}
    public void setDy(double dy) {this.dy = dy;}
    public void setRadius(int radius) {this.radius = radius;}
    public void setSpeed(double speed) {this.speed = speed;}
    public void setType(int type) {this.type = type;}
    
    public void update()
    {
        x += dx;
        y += dy;
    }
    
    /**
     * Draws the power up
     *
     * @param  g  Graphics2D
     */ 
    public void draw(Graphics2D g)
    {
        g.setColor(color1);
        g.setStroke(new BasicStroke(3));
        g.drawRect((int) (x - radius),(int) y - radius, 2 * radius, 2 * radius);
        g.setColor(color2);
        g.fillRect((int) (x - radius), (int) (y - radius), 2 * radius, 2 * radius);
    }
}
