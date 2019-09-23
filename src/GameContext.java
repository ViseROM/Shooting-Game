import java.awt.Graphics2D;

/**
 * Write a description of class GameContext here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class GameContext
{
    private static GameContext context;
    private static int currentState;
    
    //To store the possible game states
    private static GameState[] states;
    
    //State transition table
    private int [][] nextState;
    
    private final int NUMSTATES = 4;
    
    private GameContext()
    {
        //Possible states
        states = new GameState[NUMSTATES];
        states[0] = new MenuState();
        states[1] = new HelpState();
        states[2] = new PlayState();
        states[3] = new GameOverState();
        
        //Transition table for states
        nextState = new int[NUMSTATES][NUMSTATES];
        nextState[0][0] = 0;   nextState[0][1] = 1;    nextState[0][2] = 2;     nextState[0][3] = 0;
        nextState[1][0] = 0;   nextState[1][1] = 1;    nextState[1][2] = 1;     nextState[1][3] = 1;  
        nextState[2][0] = 0;   nextState[2][1] = 2;    nextState[2][2] = 2;     nextState[2][3] = 3;     
        nextState[3][0] = 0;   nextState[3][1] = 3;    nextState[3][2] = 3;     nextState[3][3] = 3;
        
        //Set currentState to 0 (MenuState)
        currentState = 0;
    }
    
    public static GameContext instance()
    {
        if(context == null)
        {
            context = new GameContext();
        }
        return context;
    }
    
    public int getCurrentState(){return currentState;}
    
    public void update()
    {
        states[currentState].update();
    }
    
    public void draw(Graphics2D g)
    {
        states[currentState].draw(g);
    }
    
    public void changeState(int transition)
    {   
        clear();
        
        //Change the currentState
        currentState = nextState[currentState][transition];
    }
    
    private void clear()
    {
        states[0] = new MenuState();
        states[1] = new HelpState();
        states[2] = new PlayState();
        states[3] = new GameOverState();
    }
    
    public void isTouchingButton(int x, int y)
    {
        if(currentState == 0 && states[0] instanceof MenuState)
        {
            MenuState temp = (MenuState) states[0];
            temp.isTouchingButton(x, y);
        }
        else if(currentState == 1 && states[1] instanceof HelpState)
        {
            HelpState temp = (HelpState) states[1];
            temp.isTouchingButton(x, y);
        }
        else if(currentState == 3 && states[3] instanceof GameOverState)
        {
            GameOverState temp = (GameOverState) states[3];
            temp.isTouchingButton(x, y);
        }
    }
    
    public void isClickingButton(int x, int y)
    {
        if(currentState == 0 && states[0] instanceof MenuState)
        {
            MenuState temp = (MenuState) states[0];
            temp.isClickingButton(x, y);
        }
        else if(currentState == 1 && states[1] instanceof HelpState)
        {
            HelpState temp = (HelpState) states[1];
            temp.isClickingButton(x, y);
        }
        else if(currentState == 3 && states[3] instanceof GameOverState)
        {
            GameOverState temp = (GameOverState) states[3];
            temp.isClickingButton(x, y);
        }
    }
}
