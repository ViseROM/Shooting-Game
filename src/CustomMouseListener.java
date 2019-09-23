import java.awt.event.*;

/**
 * Manages mouse events
 *
 * @author Vachia Thoj
 * @version 1.0
 */
public class CustomMouseListener implements MouseListener, MouseMotionListener
{
    /**
     * Constructor for objects of class CustomMouseListener
     */
    public CustomMouseListener() 
    {
        
    }

    public void mouseClicked(MouseEvent e)
    {
        
    }
    
    public void mouseEntered(MouseEvent e)
    {
    
    }
    
    public void mouseExited(MouseEvent e)
    {
        
    }
    
    public void mousePressed(MouseEvent e)
    {
        (GameContext.instance()).isClickingButton(e.getX(), e.getY());
    }
    
    public void mouseReleased(MouseEvent e)
    {
        
    }
    
    public void mouseDragged(MouseEvent e)
    {
        
    }
    
    public void mouseMoved(MouseEvent e)
    {
        (GameContext.instance()).isTouchingButton(e.getX(), e.getY());
    }
}
