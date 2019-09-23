import java.awt.Graphics2D;
/**
 * Abstract class Button - Describes a button
 *
 * @author Vachia Thoj
 * @version 1.0
 */
public abstract class Button
{
    //x and y coordinage of button
    protected int x;
    protected int y;
    
    //Width and height of button
    protected int width;
    protected int height;
    
    protected boolean mouseTouchingButton;
    protected boolean mouseClickingButton;
    
    protected Button()
    {
        mouseTouchingButton = false;
        mouseClickingButton = false;
    }
    
    protected abstract void draw(Graphics2D g);
    
    protected int getX() {return x;}
    protected int getY() {return y;}
    protected int getWidth() {return width;}
    protected int getHeight() {return height;}
    protected boolean isMouseTouchingButton() {return mouseTouchingButton;}
    protected boolean isMouseClickingButton() {return mouseClickingButton;}
    
    protected void setX(int x) {this.x = x;}
    protected void setY(int y) {this.y = y;}
    protected void setWidth(int width) {this.width = width;}
    protected void setHeight(int height) {this.height = height;}
    protected void setMouseTouchingButton(boolean b) {mouseTouchingButton = b;}
    protected void setMouseClickingButton(boolean b) {mouseClickingButton = b;}
}
