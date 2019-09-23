import java.awt.Graphics2D;
import java.awt.*;
import java.util.*;

/**
 * Manages the Hud of the game
 *
 * @author Vachia Thoj
 * @version 1.0
 */
public class Hud
{
    //x and y position of the hid
    private double x;
    private double y;
    
    //The width and height of the hud
    private double width;
    private double height;
    
    private static Player player;
    
    private boolean slowDown;
    private long slowDownTimer;
    private long slowDownTimerDiff;
    private int slowDownLength;
    
    public Hud(double x, double y, double width, double height)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        
        player = player.instance();
        
        slowDown = false;
        slowDownTimer = 0;
        slowDownTimerDiff = 0;
        slowDownLength = 5000;
    }
    
    public double getX() {return x;}
    public double getY() {return y;}
    public double getWidth() {return width;}
    public double getHeight() {return height;}
    public long getSlowDownTimer(){return slowDownTimer;}
    public boolean getSlowDown() {return slowDown;}
    
    public void setX(double x) {this.x = x;}
    public void setY(double y) {this.y = y;}
    public void setWidth(double width) {this.width = width;}
    public void setHeight(double height) {this.height = height;}
    
    public void setSlowDown(boolean b) 
    {
        if(b == true)
        {
            slowDownTimer = System.nanoTime();
        }
        slowDown = b;
    }
    
    public void update()
    {
        //Slowdown update
        if(slowDownTimer != 0 && slowDown == true)
        {
            slowDownTimerDiff = (System.nanoTime() - slowDownTimer) / 1000000;
            if(slowDownTimerDiff > slowDownLength)
            {
                slowDownTimer = 0;
                slowDown = false;
            }
        }
    }
    
    public void draw(Graphics2D g)
    {
        //Draw hud background
        g.setColor(new Color(0, 0, 0));
        g.setStroke(new BasicStroke(2));
        g.drawRect((int) x, (int) y, (int) width, (int) height);
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect((int) x, (int) y, (int) width, (int) height);
        
        drawLifeMeter(g);
        drawPowerMeter(g);
        drawScore(g);
        drawSlowMeter(g);
    }
    
    
    private void drawLifeMeter(Graphics2D g)
    {
        //Draw life text
        g.setFont(new Font("Century Gothic", Font.BOLD, 20));
        String lifeText = "Life:";
        g.setColor(new Color(255, 255, 255));
        g.drawString(lifeText, 5, 40);
        
        //Draw life meter
        for(int i = 0; i < player.getLives(); i++)
        {
            g.setColor(new Color(255, 255, 255));
            g.setStroke(new BasicStroke(3));
            g.drawOval((int) 50 + (25 * i), 20, 20, 20);
            g.setColor(new Color(255, 255, 255, 128));
            g.fillOval((int) 50 + (25 * i), 20, 20, 20);
        }
    }
    
    private void drawPowerMeter(Graphics2D g)
    {
        //Draw power text
        g.setFont(new Font("Century Gothic", Font.BOLD, 20));
        String powerText = "Power:";
        g.setColor(new Color(255, 255, 255));
        g.drawString(powerText, 5, 90);
        
        //Draw power meter
        g.setColor(Color.YELLOW);
        g.fillRect(75, 70, player.getPower() * 20, 20);
        g.setColor(Color.YELLOW.darker());
        g.setStroke(new BasicStroke(3));
        for(int i = 0; i < player.getRequiredPower(); i++)
        {
            g.drawRect(75 + 20 * i, 70, 20, 20);
        }
    }
    
    private void drawScore(Graphics2D g)
    {
        //Draw Score Text
        g.setFont(new Font("Century Gothic", Font.BOLD, 20));
        String scoreText = "Score: " + player.getScore();
        g.setColor(new Color(255, 255, 255));
        g.drawString(scoreText, 575, 40);
    }
    
    private void drawSlowMeter(Graphics2D g)
    {
        //Draw slow meter
        if(slowDownTimer != 0)
        {
            //Draw Slow mode text
            g.setFont(new Font("Century Gothic", Font.BOLD, 20));
            String slowText = "Slow: ";
            g.setColor(new Color(255, 255, 255));
            g.drawString(slowText, 520, 90);
            
            g.setColor(new Color(255, 255, 255));
            g.setStroke(new BasicStroke(3));
            g.drawRect(580, 70, 100, 20);
            g.fillRect(580, 70, (int) (100 - 100.0 * slowDownTimerDiff / slowDownLength), 20);
        }
    }
}
