import java.awt.*;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;


/**
 * The MenuButton class. Describes a MenuButton.
 *
 * @author Vachia Thoj
 * @version 1.0
 */
public class MenuButton extends Button
{
    private BufferedImage image1;
    private BufferedImage image2;
    public MenuButton()
    {
        super();
        
         try{
            image1 = ImageIO.read(getClass().getResourceAsStream("images/menuButton1.png"));
            image2 = ImageIO.read(getClass().getResourceAsStream("images/menuButton2.png"));
            this.width = image1.getWidth();
            this.height = image1.getHeight();
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error loading graphics");
            System.exit(0);
        }
    }
    
    
    public void update()
    {
        
    }
    
    public void draw(Graphics2D g)
    {
        //Draw menu button
        if(mouseTouchingButton == false)
        {
            g.drawImage(image1, x, y, null);
        }
        else
        {
            g.drawImage(image2, x, y, null);
        }
    }
}
