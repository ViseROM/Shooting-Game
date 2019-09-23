import java.awt.Graphics2D;
/**
 * Abstract class GameState - write a description of the class here
 *
 * @author (your name here)
 * @version (version number or date here)
 */
public abstract class GameState
{
    //protected static GameContext context;
    
    protected GameState()
    {
     
    }
    
    protected abstract void update();
    protected abstract void draw(Graphics2D g);
}
