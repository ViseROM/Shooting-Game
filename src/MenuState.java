import java.awt.Graphics2D;
import java.awt.*;
import java.util.*;

/**
 * Manages the menu state
 *
 * @author Vachia Thoj
 * @version 1.0
 */
public class MenuState extends GameState
{
    private ArrayList<Button> buttons;
    private ArrayList<Transition> transitions;
    private boolean transition;
    private int state;
    /**
     * Constructor for objects of class MenuState
     */
    public MenuState()
    {
        super();
        
        buttons = new ArrayList<Button>();
        transitions = new ArrayList<Transition>();
        addTransitions();
        
        //Add a startButton
        StartButton startButton = new StartButton();
        startButton.setX((GamePanel.WIDTH / 2) - (startButton.getWidth() / 2));
        startButton.setY((GamePanel.HEIGHT / 2) - (startButton.getHeight() / 2));
        buttons.add(startButton);
        
        //Add a helpButton;
        HelpButton helpButton = new HelpButton();
        helpButton.setX((GamePanel.WIDTH / 2) - (startButton.getWidth() / 2));
        helpButton.setY(startButton.getY() + startButton.getHeight() + 50);
        buttons.add(helpButton);
        
        transition = false;
        state = 0;
    }
    
    public void update()
    {
        if(transition == false && (startGame() == true || help() == true))
        {
            transition = true;
        }
        
        if(transition == true)
        {
            //Update transitions
            for(int i = 0; i < transitions.size(); i++)
            {
                transitions.get(i).update();
                if(transitions.get(i).getWidth() >= 800)
                {
                    transitions.clear();
                    (GameContext.instance()).changeState(state);
                }
            }
        }
        //changeState();
    }
    
    private void addTransitions()
    {
        //Left to right transition animation
        transitions.add(new Transition(0, 0, 0, 100, 20, 1));
        transitions.add(new Transition(0, 200, 0, 100, 20, 1));
        transitions.add(new Transition(0, 400, 0, 100, 20, 1));
        transitions.add(new Transition(0, 600, 0, 100, 20, 1));
            
        //Right to left transition animation
        transitions.add(new Transition(GamePanel.WIDTH, 100, 0,100, 20, 2));
        transitions.add(new Transition(GamePanel.WIDTH, 300, 0,100, 20, 2));
        transitions.add(new Transition(GamePanel.WIDTH, 500, 0,100, 20, 2));
    }
    
    public void draw(Graphics2D g)
    {
        //Draw Background
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
        
        //Draw Title
        g.setColor(Color.BLACK);
        g.setFont(new Font("Century Gothic", Font.BOLD, 64));
        String s = "Shooting Game";
        int length = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
        g.drawString(s, (GamePanel.WIDTH / 2) - (length / 2), 100);
        
        //Draw Buttons
        for(int i = 0; i < buttons.size(); i++)
        {
            buttons.get(i).draw(g);
        }
        
        //Draw transitions
        if(transition == true)
        {
            for(int i = 0; i < transitions.size(); i++)
            {
                transitions.get(i).draw(g);
            }
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
    
    private boolean startGame()
    {
        for(int i = 0; i < buttons.size(); i++)
        {
            StartButton b = null;
            if(buttons.get(i) instanceof StartButton)
            {
                b = (StartButton) buttons.get(i);
                
                if(b.isMouseClickingButton() == true)
                {
                    buttons.clear();
                    state = 2;
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean help()
    {
        for(int i = 0; i < buttons.size(); i++)
        {
            HelpButton b = null;
            if(buttons.get(i) instanceof HelpButton)
            {
                b = (HelpButton) buttons.get(i);
                
                if(b.isMouseClickingButton() == true)
                {
                    buttons.clear();
                    state = 1;
                    return true;
                }
            }
        }
        return false;
    }
}
