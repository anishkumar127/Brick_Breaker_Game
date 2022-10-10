package Game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.Timer;
public class GamePlay extends JPanel implements ActionListener, KeyListener {
    private boolean play = false;  // automatic play off.
    private int score =0;
    private int totalBrick = 21;
    private Timer timer;
    private int delay = 8;
    private int ballposX = 120;
    private int ballposY = 350;
    private int ballXdir = -1;
    private int ballYdir = -2;
    private int playerX = 350;


    // object
    private MapGenerator map;
    public GamePlay(){
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(true);

        // timer start
        timer = new Timer(delay,this);
        timer.start();

        map = new MapGenerator(3,7);
    }

    // color
    public void paint(Graphics g){
        // black canvas
        g.setColor(Color.black);
        g.fillRect(1,1,692,592);

        // border
        g.setColor(Color.yellow);
        g.fillRect(0,0,692,3);
        g.fillRect(0,3,3,592);
        g.fillRect(691,3,3,592);

        // paddle
        g.setColor(Color.green);
        g.fillRect(playerX,550,100,8);

        // bricks
        map.draw((Graphics2D) g);
        // ball
        g.setColor(Color.red);
        g.fillOval(ballposX,ballposY,20,20);

        // score
        g.setColor(Color.green);
        g.setFont(new Font("serif",Font.BOLD,20));
        g.drawString("Score:"+score,550,30);


        // game over loss game.

        if(ballposY>=570){
            play=false;
//            ballXdir=0;
//            ballposY=0;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.green);
            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString("Game Over !!, Score:"+score,200,300);

            // restart game
            g.setFont(new Font("serif",Font.BOLD,25));
            g.drawString("Press Enter to Restart!!"+score,230,350);

        }
            // player win.
            if(totalBrick<=0){
                play=false;
//                ballXdir=0;
//                ballposY=0;
                ballXdir = 0;
                ballYdir = 0;
                g.setColor(Color.green);
                g.setFont(new Font("serif",Font.BOLD,30));
                g.drawString("You Won!!"+score,200,300);

                // restart game
                g.setFont(new Font("serif",Font.BOLD,25));
                g.drawString("Press Enter to Restart!!"+score,230,350);
            }

                g.dispose();

    }
    // key movement left and right
    private void moveLeft(){
        play = true;
        playerX = playerX-20;
    }
    private void moveRight(){
        play = true;
        playerX=playerX+20;
    }


    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_LEFT){
            if(playerX<10){
                playerX=10;
            } else {
                moveLeft();
            }
        }
        if(e.getKeyCode()==KeyEvent.VK_RIGHT){
            if(playerX>=600){
                playerX=600;
            } else {
                moveRight();
            }
        }

        if(e.getKeyCode()==KeyEvent.VK_ENTER){
            if(!play){
                score=0;
                totalBrick=21;
                ballposX=120;
                ballposY=350;
                ballXdir=-1;
                ballYdir=-2;
                playerX=320;

                map = new MapGenerator(3,7);
                repaint(); // extra
            }
        }
        // repaint for render size different location.
       repaint();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start(); // extra
        if(play){

        if(ballposX<=0){
            ballXdir=-ballXdir;
        }
        if(ballposX>=670){
            ballXdir =-ballXdir;
        }
        if(ballposY<=0){
            ballYdir=-ballYdir;
        }
            if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 30, 8)))
            {
                ballYdir = -ballYdir;
                ballXdir = -2;
            }
            else if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX + 70, 550, 30, 8)))
            {
                ballYdir = -ballYdir;
                ballXdir = ballXdir + 1;
            }
            else if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX + 30, 550, 40, 8)))
            {
                ballYdir = -ballYdir;
            }

        Rectangle ballRect = new Rectangle(ballposX,ballposY,20,20);
        Rectangle paddleRect = new Rectangle(playerX,550,100,8);

        A:for(int i=0; i<map.map.length; i++){
            for(int j=0; j<map.map[0].length; j++){
                if(map.map[i][j]>0){
                    int width = map.brickWidth;
                    int height = map.brickHeight;
                    int brickXpos = 80+j*width;
                    int brickYpos = 50+i*height;

                    Rectangle brickRect = new Rectangle(brickXpos,brickYpos,width,height);
                    if(ballRect.intersects(brickRect)){
                        map.setBrick(0,i,j);
                        totalBrick--;
                        score+=5; // score increase by 5 value.
                        if(ballposX+19<=brickXpos || ballposX+1>=brickXpos+width){
                            ballXdir =-ballXdir;
                        }else{
                            ballYdir=-ballYdir;
                        }

                        break A;
                    }
                }
            }
        }

            ballposX+=ballXdir;
            ballposY+=ballYdir;
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e){}
    @Override
    public void keyTyped(KeyEvent e){}

}
