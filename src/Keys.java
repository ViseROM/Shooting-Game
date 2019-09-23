import java.awt.event.KeyEvent;

/**
 * Manages key press events
 *
 * @author Vachia Thoj
 * @version 1.0
 */
public class Keys
{
    private static final int NUM_KEYS = 5;
    
    private static boolean keyState[] = new boolean[NUM_KEYS];
    private static boolean prevKeyState[] = new boolean[NUM_KEYS];
    
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int UP = 2;
    public static final int DOWN = 3;
    public static final int SPACE = 4;
    
    public static void keySet(int i, boolean b)
    {
        if(i == KeyEvent.VK_LEFT)
        {
            keyState[LEFT] = b;
        }
        else if(i == KeyEvent.VK_RIGHT)
        {
            keyState[RIGHT] = b;
        }
        else if(i == KeyEvent.VK_UP)
        {
            keyState[UP] = b;
        }
        else if(i == KeyEvent.VK_DOWN)
        {
            keyState[DOWN] = b;
        }
        else if(i == KeyEvent.VK_SPACE)
        {
            keyState[SPACE] = b;
        }
    }
    
    public static void update()
    {
        for(int i = 0; i < NUM_KEYS; i++)
        {
            prevKeyState[i] = keyState[i];
        }
    }
    
    public static boolean isPressed(int i) {
	return keyState[i] && !prevKeyState[i];
    }

    public static boolean isDown(int i) {
	return keyState[i];
    }
    
    public static boolean anyKeyDown() {
        for(int i = 0; i < NUM_KEYS; i++) {
            if(keyState[i]) return true;
	}
	return false;
    }
	
    public static boolean anyKeyPress() {
        for(int i = 0; i < NUM_KEYS; i++) {
            if(keyState[i] && !prevKeyState[i]) return true;
	}
	return false;
    }
}
