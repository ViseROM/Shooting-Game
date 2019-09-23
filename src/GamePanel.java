import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;

/**
 * Stores content of the game panel within the game window
 *
 * @author Vachia Thoj
 * @version 1.0
 */
public class GamePanel extends JPanel implements Runnable, KeyListener
{
    //Width and height of panel
    public static final int WIDTH = 700;
    public static final int HEIGHT = 700;
    
    //Thread to run the game
    private Thread thread;
    private boolean running;
    
    private BufferedImage image;
    private Graphics2D g;
    
    //Game framerate
    private final int FPS = 30;
    private double averageFPS;
    
    //Game context to manage the different game states
    private static GameContext gameContext;
    
    /**
     * Constructor for objects of class GamePanel
     */
    public GamePanel()
    {
        super();
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setFocusable(true);
        this.requestFocus();
        gameContext = GameContext.instance();
    }

    public void addNotify()
    {
        super.addNotify();
        if(thread == null)
        {
            addKeyListener(this);
            thread = new Thread(this);
            thread.start();
        }
    }
    
    public void run()
    {
        running = true;
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) image.getGraphics();
        
        //Add anti-aliasing
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        long startTime;
        long URDTimeMillis;
        long waitTime;
        long totalTime = 0;
        
        int frameCount = 0;
        int maxFrameCount = 30;
        long targetTime = 1000 / FPS;
        
        while(running == true)
        {
            startTime = System.nanoTime();
            
            update();
            draw();
            drawToScreen();
            
            URDTimeMillis = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime - URDTimeMillis;
            
            try{
                Thread.sleep(waitTime);
            }catch(Exception e){
                
            }
            totalTime += System.nanoTime() - startTime;
            frameCount++;
            
            if(frameCount == maxFrameCount)
            {
                averageFPS = 1000.0 / ((totalTime / frameCount) / 1000000);
                frameCount = 0;
                totalTime = 0;
            } 
        }
    }
    
    private void update()
    {
        gameContext.update();
    }
    
    private void draw()
    {
        gameContext.draw(g);
    }
    
    private void drawToScreen()
    {
        Graphics g2 = this.getGraphics();
        g2.drawImage(image, 0, 0, null);
        g2.dispose();
    }
    
    public void keyTyped(KeyEvent key){}
    
    public void keyPressed(KeyEvent key)
    {
        Keys.keySet(key.getKeyCode(), true);
    }
    
    public void keyReleased(KeyEvent key)
    {
        Keys.keySet(key.getKeyCode(), false);
    }
}
