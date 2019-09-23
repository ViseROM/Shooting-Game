import java.awt.Graphics2D;
import java.awt.*;
import java.util.*;

/**
 * The GameOverState class. Manages the GameOverState of the game.
 *
 * @author Vachia Thoj
 * @version 1.0
 */
public class GameOverState extends GameState
{   
    private ArrayList<Button> buttons;
    private static Player player;
    public GameOverState()
    {
        super();
        buttons = new ArrayList<Button>();
        
        //Add a menuButton
        MenuButton menuButton = new MenuButton();
        menuButton.setX((GamePanel.WIDTH / 2) - (menuButton.getWidth() / 2));
        menuButton.setY((GamePanel.HEIGHT / 2) + 100);
        buttons.add(menuButton);
        player = player.instance();
    }
    
    public void update()
    {
        if(menu() == true)
        {
            player.clear();
            player = null;
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
        String text = "Game Over";
        int length1 = (int) g.getFontMetrics().getStringBounds(text, g).getWidth();
        g.drawString(text, (GamePanel.WIDTH / 2) - (length1 / 2), 100);
        
        //Draw Score
        g.setColor(Color.BLACK);
        g.setFont(new Font("Century Gothic", Font.BOLD, 64));
        String score = "Score: " + player.getScore();
        int length2 = (int) g.getFontMetrics().getStringBounds(score, g).getWidth();
        g.drawString(score, (GamePanel.WIDTH / 2) - (length2 / 2), GamePanel.HEIGHT / 2);
        
        
        //Draw Buttons
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
                    return true;
                }
            }
        }
        return false;
    }
}
