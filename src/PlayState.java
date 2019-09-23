import java.awt.Graphics2D;
import java.awt.*;
import java.util.*;

/**
 * The PlayState class. Describes what occurs in the Play State
 *
 * @author Vachia Thoj
 * @version 1.0
 */
public class PlayState extends GameState
{   
    private static Player player;
    private Hud hud;
    public static ArrayList<Bullet> bullets;
    public static ArrayList<Enemy> enemies;
    private ArrayList<Explosion> explosions;
    private ArrayList<PowerUp> powerUps;
    
    private long waveStartTimer;
    private long waveStartTimerDiff;
    private boolean waveStart;
    private int waveNumber;
    private int waveDelay;
    
    private final int MAX_HEALTH_DROP = 4;
    private int healthDrop;
    
    private final int MAX_SLOW_DROP = 2;
    private int slowDrop;
    
    private ArrayList<Transition> transitions;
    
    public PlayState()
    {
        super();
        player = Player.instance();
        hud = new Hud(0, 0, 700, 100);
        bullets = new ArrayList<Bullet>();
        enemies = new ArrayList<Enemy>();
        explosions = new ArrayList<Explosion>();
        powerUps = new ArrayList<PowerUp>();
        transitions = new ArrayList<Transition>();
        
        waveStartTimer = 0;
        waveStartTimerDiff = 0;
        waveStart = false;
        waveNumber = 0;
        waveDelay = 2000;
        
        healthDrop = 0;
        slowDrop = 0;
    }
    
    
    public Player getPlayer(){return player;}
    public ArrayList<Bullet> getBullets(){return bullets;}
    public int getWaveNumber() {return waveNumber;}
    
    public void update()
    {
        //Start wave
        if(waveStartTimer == 0 && enemies.size() == 0 && powerUps.size() == 0) 
        {
            waveNumber++;
            waveStart = false;
            waveStartTimer = System.nanoTime();
            player.setDisabled(true);
            slowDrop = 0;
        }
        else
        {
            waveStartTimerDiff = (System.nanoTime() - waveStartTimer) / 1000000;
            
            if(waveStartTimerDiff > waveDelay)
            {
                waveStart = true;
                waveStartTimer = 0;
                waveStartTimerDiff = 0;
                player.setDisabled(false);
            }
        }
        
        //Create enemies
        if(waveStart == true && enemies.size() == 0 && powerUps.size() == 0) 
        {
            createNewEnemies();
        }
        
        //Handle user input
        handleInput();
        
        //Update player
        player.update();
        
        //Update hud
        hud.update();
        
        //Update bullets
        for(int i = 0; i < bullets.size(); i++)
        {
            Bullet b = bullets.get(i);
            
            b.update();
            
            //Check if bullet is touching edge of window
            boolean remove = b.isTouchingEdge();
            if(remove == true)
            {
                bullets.remove(i);
                --i;
            }
        }
        
        //Update enemies
        for(int i = 0; i < enemies.size(); i++)
        {
            enemies.get(i).update();
        }
        
        //Update explosions
        for(int i = 0; i < explosions.size(); i++)
        {
            Explosion e = explosions.get(i);
            e.update();
            
            //Remove explosion when it has reached its max radius
            if(e.getRadius() >= e.getMaxRadius())
            {
                explosions.remove(i);
                i--;
            }
        }
        
        //Update powerUps
        for(int i = 0; i < powerUps.size(); i++)
        {
            PowerUp pu = powerUps.get(i);
            pu.update();
            
            double pux = pu.getX();
            double puy = pu.getY();
            int pur = pu.getRadius();
            
            //Remove powerUp if it reaches bottom of screen
            if(puy > GamePanel.HEIGHT - pur)
            {
                powerUps.remove(i);
                i--;
            }
        }
        
        //Player-enemy collision
        for(int i = 0; i < enemies.size(); i++)
        {
            Enemy e = enemies.get(i);
            
            double ex = e.getX();
            double ey = e.getY();
            int er = e.getRadius();
            
            double px = player.getX();
            double py = player.getY();
            int pr = player.getRadius();
            
            if(isColliding(px, py, ex, ey, pr, er) == true)
            {
                if(player.isHit() == false)
                {
                    player.decreaseLives(1);
                    player.setHit(true);
                    player.increaseScore(-10);
                }
            }
        }
        
        //Player-powerUp collision
        for(int i = 0; i < powerUps.size(); i++)
        {
            PowerUp pu = powerUps.get(i);
            
            double pux = pu.getX();
            double puy = pu.getY();
            int pur = pu.getRadius();
            
            double px = player.getX();
            double py = player.getY();
            int pr = player.getRadius();
            
            //Remove powerUp if colliding with player
            if(isColliding(px, py, pux, puy, pr, pur) == true)
            {
                if(pu.getType() == 1)
                {
                    player.increasePower(1);
                }
                else if(pu.getType() == 2)
                {
                    player.increaseLives(1);
                }
                else if(pu.getType() == 3)
                {
                    hud.setSlowDown(true);
                    for(int j = 0; j < enemies.size(); j++)
                    {
                        enemies.get(j).setSlow(true);
                    }
                }
                powerUps.remove(i);
                i--;
            }
        }
        
        //Bullet-enemy collision
        for(int i = 0; i < bullets.size(); i++)
        {
            Bullet b = bullets.get(i);
            double bx = b.getX();
            double by = b.getY();
            int br = b.getRadius();
            for(int j = 0; j < enemies.size(); j++)
            {
                Enemy e = enemies.get(j);
                double ex = e.getX();
                double ey = e.getY();
                int er = e.getRadius();
                
                if(isColliding(bx, by, ex, ey, br, er) == true)
                {
                    e.loseLife(1);
                    bullets.remove(i);
                    i--;
                    break;
                }
            }
        }
        
        //Check if enemy can move at normal speed
        if(hud.getSlowDown() == false)
        {
            for(int i = 0; i < enemies.size(); i++)
            {
                enemies.get(i).setSlow(false);
            }
        }
        
        //Check if an enemy is dead
        for(int i = 0; i < enemies.size(); i++)
        {
            Enemy e = enemies.get(i);
            
            double x = e.getX();
            double y = e.getY();
            int radius = e.getRadius();
            if(e.getLives() <= 0)
            {
                if(waveNumber > 1)
                {
                    //Explode enemy
                    e.explode();
                }
                
                player.increaseScore(e.getType() * e.getRank());
                
                //Remove enemy
                enemies.remove(i);
                
                //Create explosion animation
                explosions.add(new Explosion(x, y, radius));
                
                //Call dropPowerUp()
                dropPowerUp(x, y);
                
                i--;
            }
        }
        
        
        if(endGame() == true)
        {
            //Update transitions and changeState
            for(int i = 0; i < transitions.size(); i++)
            {
                transitions.get(i).update();
                if(transitions.get(i).getWidth() >= 800)
                {
                    GameContext.instance().changeState(3);
                }
            }
        }
    }
    
    public void draw(Graphics2D g)
    {
       //Draw background
       
       g.setColor(new Color(0, 100, 255, 255));
       g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
       
       if(hud.getSlowDown() == true)
       {
           g.setColor(new Color(255, 255, 255, 64));
           g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
       }
       
       //Draw player
       player.draw(g);
       
       //Draw hud
       hud.draw(g);
       
       //Draw bullets
       for(int i = 0; i < bullets.size(); i++)
       {
           bullets.get(i).draw(g);
       }
       
       //Draw enemies
       for(int i = 0; i < enemies.size(); i++)
       {
           enemies.get(i).draw(g);
       }
       
       //Draw explosions
       for(int i = 0; i < explosions.size(); i++)
       {
           explosions.get(i).draw(g);
       }
       
       //Draw powerups
       for(int i = 0; i < powerUps.size(); i++)
       {
           powerUps.get(i).draw(g);
       }
       
       //Draw wave number
       if(waveStartTimer != 0 && waveNumber < 5)
       {
           g.setFont(new Font("Century Gothic", Font.PLAIN, 32));
           String s; 
           if(waveNumber != 4)
           {
               s = " - Wave " + waveNumber + " -";
           }
           else
           {
               s = " - Final Wave - ";
           }
           
           int length = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
           int alpha = (int) (255 * Math.sin(3.14 * waveStartTimerDiff / waveDelay));
           
           if(alpha > 255)
           {
               alpha = 255;
           }
           
           g.setColor(new Color(255, 255, 255, alpha));
           g.drawString(s, GamePanel.WIDTH / 2 - length / 2, GamePanel.HEIGHT / 2);
       }
       
       //Draw Transitions
       for(int i = 0; i < transitions.size(); i++)
       {
           transitions.get(i).draw(g);
       }
    }
    
    private void handleInput()
    {
        if(Keys.isDown(Keys.LEFT) == true)
        {
            player.setLeft(true);
        }
        else if(Keys.isDown(Keys.LEFT) == false)
        {
            player.setLeft(false);
        }
        
        if(Keys.isDown(Keys.RIGHT) == true)
        {
            player.setRight(true);
        }
        else if(Keys.isDown(Keys.RIGHT) == false)
        {
            player.setRight(false);
        }
        
        if(Keys.isDown(Keys.UP))
        {
            player.setUp(true);
        }
        else if(Keys.isDown(Keys.UP) == false)
        {
            player.setUp(false);
        }
        
        if(Keys.isDown(Keys.DOWN) == true)
        {
            player.setDown(true);
        }
        else if(Keys.isDown(Keys.DOWN) == false)
        {
            player.setDown(false);
        }
        
        if(Keys.isDown(Keys.SPACE) == true)
        {
            player.setFiring(true);
        }
        else if(Keys.isDown(Keys.SPACE) == false)
        {
            player.setFiring(false);
        }
    }
    
    private void createNewEnemies()
    {
        enemies.clear();
        Enemy e;
        
        if(waveNumber == 1)
        {
            for(int i = 0; i < 6; i++)
            {
                enemies.add(new Enemy(1, 2));
            }
        }
        else if(waveNumber == 2)
        {
            for(int i = 0; i < 3; i++)
            {
                enemies.add(new Enemy(1, 2));
            }
            
            for(int i = 0; i < 2; i++)
            {
                enemies.add(new Enemy(1, 3));
            }
            
            for(int i = 0; i < 3; i++)
            {
                enemies.add(new Enemy(2, 1));
            }
        }
        else if(waveNumber == 3)
        {
            for(int i = 0; i < 2; i++)
            {
                enemies.add(new Enemy(1, 2));
            }
            
            for(int i = 0; i < 3; i++)
            {
                enemies.add(new Enemy(1, 3));
            }
            
            for(int i = 0; i < 4; i++)
            {
                enemies.add(new Enemy(2, 2));
            }
            
            for(int i = 0; i < 1; i++)
            {
                enemies.add(new Enemy(3,2));
            }
        }
        else if(waveNumber == 4)
        {
            for(int i = 0; i < 3; i++)
            {
                enemies.add(new Enemy(1, 3));
            }
            
            for(int i = 0; i < 2; i++)
            {
                enemies.add(new Enemy(2, 1));
            }
            
            for(int i = 0; i < 3; i++)
            {
                enemies.add(new Enemy(2, 2));
            }
            
            for(int i = 0; i < 4; i++)
            {
                enemies.add(new Enemy(3,2));
            }
        }
    }
    
    private boolean isColliding(double x1, double y1, double x2, double y2, int r1, int r2)
    {
        double dx = x1 - x2;
        double dy = y1 - y2;
        double distance = Math.sqrt(dx * dx + dy * dy);
        
        if(distance < r1 + r2)
        {
            return true;
        }
        return false;
    }
    
    private void dropPowerUp(double x, double y)
    {
        double dropRate = Math.random() * 100;
        
        if(dropRate < 30)
        {
            powerUps.add(new PowerUp(x, y, 1));
        }
        else if(dropRate < 40)
        {
            if(healthDrop < MAX_HEALTH_DROP && waveNumber > 1)
            {
                powerUps.add(new PowerUp(x, y, 2));
                ++healthDrop;
            }
        }
        else if(dropRate < 50)
        {
            if(slowDrop < MAX_SLOW_DROP && waveNumber > 2)
            {
                powerUps.add(new PowerUp(x, y, 3));
                ++slowDrop;
            }
        }
    }
    
    private boolean endGame()
    {
        if(player.getLives() == 0 || (waveNumber >= 5 && enemies.size() == 0 && powerUps.size() == 0))
        {
            player.setDisabled(true);
            enemies.clear();
            powerUps.clear();
            bullets.clear();
            
            //Left to right transition animation
            transitions.add(new Transition(0, 0, 0, 100, 20, 1));
            transitions.add(new Transition(0, 200, 0, 100, 20, 1));
            transitions.add(new Transition(0, 400, 0, 100, 20, 1));
            transitions.add(new Transition(0, 600, 0, 100, 20, 1));
            
            //Right to left transition animation
            transitions.add(new Transition(GamePanel.WIDTH, 100, 0,100, 20, 2));
            transitions.add(new Transition(GamePanel.WIDTH, 300, 0,100, 20, 2));
            transitions.add(new Transition(GamePanel.WIDTH, 500, 0,100, 20, 2));
            
            return true;
        }
        return false;
    }
}
