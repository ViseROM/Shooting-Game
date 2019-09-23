import java.awt.*;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;

/**
 * The HelpButton class. Describes a help button.
 *
 * @author Vachia Thoj
 * @version 1.0
 */
public class HelpButton extends Button
{
    private BufferedImage image1;
    private BufferedImage image2;
    
    public HelpButton()
    {
        super();
        try{
            image1 = ImageIO.read(getClass().getResourceAsStream("images/helpButton1.png"));
            image2 = ImageIO.read(getClass().getResourceAsStream("images/helpButton2.png"));
            this.width = image1.getWidth();
            this.height = image1.getHeight();
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error loading graphics");
            System.exit(0);
        }
    }
    
    public BufferedImage getImage() {return image1;}
    
    public void draw(Graphics2D g)
    {
        //Draw Help Button
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
