import javax.swing.JFrame;

/**
 * The game window. Sets up the window for the game.
 *
 * @author Vachia Thoj
 * @version 1.0
 */
public class Game
{
    public static void main(String[] args)
    {
        //Create window
        JFrame window = new JFrame("Particle Shooter");
        
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        
        //Create GamePanel object
        GamePanel panel = new GamePanel();
        panel.addMouseListener(new CustomMouseListener());
        panel.addMouseMotionListener(new CustomMouseListener());
        
        //Store panel in content pane
        window.setContentPane(panel);
        
        window.pack();
        
        //Put window in center of screen
        window.setLocationRelativeTo(null);
        
        //Make window visible
        window.setVisible(true);
    } 
}
