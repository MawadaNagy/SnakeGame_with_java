
package snakegame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import javax.swing.Timer;


public final class GamePanel extends JPanel implements ActionListener{

    int screen_width = 500;
    int screen_height = 500;
    int unit_size = 10;
    int game_units = (screen_width * screen_height)/unit_size;
    int delay = 50;
    int x[] = new int[game_units];
    int y[] = new int[game_units];
    int bodyparts = 6;
    int EatenApples = 0;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;
    
    
    GamePanel(){
        
        random = new Random();
        this.setPreferredSize(new Dimension(screen_width, screen_height));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener((KeyListener) new MyKeyAdapter());
        StartGame();
        
    }
    
    public void StartGame(){
       
        NewApple(); 
        running = true;
        timer = new Timer(delay, this);
        timer.start();
        
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Draw(g);
        
    }
    
    public void Draw(Graphics g){
        
        if(running){
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, unit_size, unit_size);

            for(int i =0; i<bodyparts; i++){
                if(i==0){
                    g.setColor(Color.GREEN);
                    g.fillRect(x[i], y[i], unit_size, unit_size);
                }else{
                    g.setColor(Color.GREEN);
                    g.fillRect(x[i], y[i], unit_size, unit_size);
                }
            }
            g.setColor(Color.BLUE);
            g.setFont(new Font("Ink Free",Font.BOLD, 30));
            g.drawString("SCORE: " + EatenApples, 185, 30);
            
        }else{
            GameOver(g);
        }
    }
    
    public void NewApple(){
        
        appleX = random.nextInt(screen_width/unit_size)*unit_size;
        appleY = random.nextInt(screen_height/unit_size)*unit_size;
        
    }
    
    public void Move(){
        
        for(int i = bodyparts; i>0; i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        
        switch(direction){
            case 'U':
                y[0] = y[0] - unit_size;
                break;
            case 'D':
                y[0] = y[0] + unit_size;
                break;
            case 'L':
                x[0] = x[0] - unit_size;
                break;
            case 'R':
                x[0] = x[0] + unit_size;
                break;
        }
        
    }
    
    public void CheckApple(){
        
        if((x[0]==appleX) && (y[0]==appleY)){
            bodyparts++;
            EatenApples++;
            NewApple();
            
        }
        
    }
    
    public void CheckCollision(){
        //check if head collides with body
        for(int i = bodyparts; i>0;i--){          //head collides with body
            if((x[0]==x[i])&&(y[0]==y[i])){
                running = false;
            }
        }
        
        //check if head coliides with left border
        if(x[0]<0){
            running = false;
        }
        
        //check if head collides with right border
        if(x[0]>screen_width){
            running = false;
        }
        
        //check if head collides with upper border
        if(y[0]<0){
            running = false;
        }
        
        //check if head collides with bottom border
        if(y[0]>screen_height){
            running = false;
        }
        
    }
    
    public void GameOver(Graphics g){
        //SCORE
        g.setColor(Color.BLUE);
        g.setFont(new Font("Ink Free",Font.BOLD, 30));
        g.drawString("SCORE: " + EatenApples, 185, 30);

        //GAME OVER 
        g.setColor(Color.BLUE);
        g.setFont(new Font("Ink Free",Font.BOLD, 75));
        g.drawString("GAME OVER", 30, 250);
        
        //RESTART
        g.setColor(Color.BLUE);
        g.setFont(new Font("Ink Free",Font.BOLD, 30));
        g.drawString("Press SPACE to Restart", 80, 300);
          
    }
    
    public void Restart(){
        
        //Reset all variables
        bodyparts = 6;
        EatenApples = 0;
        direction = 'R';
        running = true;
        
        //reset snake position
        for(int i = 0; i<bodyparts; i++){
            x[i] = 0;
            y[i] = 0;
        }
        
        // creating new apple
        NewApple();
        
        //restarting the timer
        if(timer!=null){
            timer.stop();
            timer = new Timer(delay, this);
            timer.start();
        }
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(running){
            Move();
            CheckApple();
            CheckCollision();
        }
        repaint();
        
    }
    
    public class MyKeyAdapter extends KeyAdapter{
        
        @Override
        public void keyPressed(KeyEvent e){
            
            if(e.getKeyCode() == KeyEvent.VK_SPACE){
                Restart(); 
            }else{
                switch(e.getKeyCode()){
                    case KeyEvent.VK_LEFT:
                        if(direction != 'R'){
                            direction = 'L';
                            break;
                        }
                    case KeyEvent.VK_RIGHT:
                        if(direction != 'L'){
                            direction = 'R';
                            break;
                        }
                    case KeyEvent.VK_UP:
                        if(direction != 'D'){
                            direction = 'U';
                            break;
                        }
                    case KeyEvent.VK_DOWN:
                        if(direction != 'U'){
                            direction = 'D';
                            break;
                        }
                }
            }
            
            
        }
        
    }
    
    
}
