import java.awt.Graphics2D;
import java.awt.*;
import java.util.*;

/**
 * Write a description of class HelpState here.
 *
 * @author Vachia Thoj
 * @version 1.0
 */
public class HelpState extends GameState
{
    private ArrayList<Button> buttons;
    private MenuButton menuButton;
    public HelpState()
    {
        super();
        buttons = new ArrayList<Button>();
        
        menuButton = new MenuButton();
        menuButton.setX((GamePanel.WIDTH / 2) - (menuButton.getWidth() / 2));
        menuButton.setY(GamePanel.HEIGHT - 200);
        buttons.add(menuButton);
    }
    
    public void update()
    {
        if(menu() == true)
        {
            (GameContext.instance()).changeState(0);
        }
    }
    
    public void draw(Graphics2D g)
    {
        //Draw Background
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
        
        //Draw Title
        g.setColor(Color.BLACK);
        g.setFont(new Font("Century Gothic", Font.BOLD, 64));
        String s = "HELP";
        int length = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
        g.drawString(s, (GamePanel.WIDTH / 2) - (length / 2), 100);
        
        g.setColor(Color.BLACK);
        g.setFont(new Font("Century Gothic", Font.BOLD, 28));
        g.drawString("Controls", 20, 150);
        g.drawString("------------------------------------------------------", 20, 165);
        
        //Draw Control information text
        g.setFont(new Font("Century Gothic", Font.PLAIN, 20));;
        g.setColor(Color.BLACK);
        g.drawString("LEFT ARROW:", 20, 200);
        g.drawString("RIGHT ARROW:", 20, 250);
        g.drawString("UP ARROW", 20, 300);
        g.drawString("DOWN ARROW:", 20, 350);
        g.drawString("SPACEBAR:", 20, 400);
        
        g.drawString("Move Left", 300, 200);
        g.drawString("Move Right", 300, 250);
        g.drawString("Move Up", 300, 300);
        g.drawString("Move Down", 300, 350);
        g.drawString("Shoot", 300, 400);
        
        //Draw buttons
        for(int i = 0; i < buttons.size(); i++)
        {
            buttons.get(i).draw(g);
        }
    }
    

    public void isTouchingButton(int mouseX, int mouseY)
    {
        int x;
        int y;
        int width;
        int height;
        
        for(int i = 0; i < buttons.size(); i++)
        {
            Button b = buttons.get(i);
            x = b.getX();
            y = b.getY();
            width = b.getWidth();
            height = b.getHeight();
            if((mouseX >= x && mouseX <= (x + width)) && (mouseY >= y && mouseY <= (y + height)))
            {
                b.setMouseTouchingButton(true);
            }
            else
            {
                b.setMouseTouchingButton(false);
            }
        }
    }
    
    
    public void isClickingButton(int mouseX, int mouseY)
    {
        int x;
        int y;
        int width;
        int height;
        
        for(int i = 0; i < buttons.size(); i++)
        {
            Button b = buttons.get(i);
            x = b.getX();
            y = b.getY();
            width = b.getWidth();
            height = b.getHeight();
            if((mouseX >= x && mouseX <= (x + width)) && (mouseY >= y && mouseY <= (y + height)))
            {
                b.setMouseClickingButton(true);
                break;
            }
            else
            {
                b.setMouseClickingButton(false);
            }
        }
    }
    
    private boolean menu()
    {
        for(int i = 0; i < buttons.size(); i++)
        {
            MenuButton b = null;
            if(buttons.get(i) instanceof MenuButton)
            {
                b = (MenuButton) buttons.get(i);
                
                if(b.isMouseClickingButton() == true)
                {
                    buttons.clear();
                    return true;
                }
            }
        }
        return false;
    }
}
