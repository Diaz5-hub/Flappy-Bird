import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class FlappyPanel extends JPanel implements KeyListener, ActionListener {
    final int wallXVelocity = 5;    //how fast the wall is moving towards the bird
    final int wallWidth = 50;
    final int screenwidth = 525, screenheight = 550;
    int flappyHeight = screenheight/4;
    int flappyVel = 0, flappyAcceler =8,flappyImpulse = 1;      //the impulse makes the bird fall smoothly in y direction
    int score = 0;
    int[] wallX = {screenwidth,screenwidth+screenwidth/2};
    int[] gap = {(int)(Math.random()*(screenheight-150)),(int)(Math.random()*(screenheight-100))};
    boolean gameOver = false;
    Timer time = new Timer(40,this);
    public FlappyPanel(){
        setSize(screenwidth,screenheight);
        setFocusable(true);
        addKeyListener(this);
        setBackground(Color.black);
        //new Timer(40,this).start();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if(!gameOver){
            g.setColor(Color.yellow);
            g.setFont(g.getFont().deriveFont(Font.BOLD,26F));
            g.drawString("Score: " + score,screenwidth/2-18,20);
            drawWall(g);    //draw wall first so the bird is drawn over it
             logic();
            drawFlappy(g);
        }
        else{
            g.setColor(Color.yellow);
            g.setFont(g.getFont().deriveFont(Font.BOLD,26F));
            g.drawString("Score: " + score,screenwidth/2-18,20);
        }

    }
    public void drawFlappy(Graphics g){
        g.setColor(Color.yellow);
        g.fillRect(75,flappyHeight+ flappyVel,25,25); //add flappyVel to y as it allows the bird to move in y direction each time space is pressed

    }
    public void drawWall(Graphics g){
        for(int i=0;i<2;i++) {
            g.setColor(Color.red);
            g.fillRect(wallX[i], 0, wallWidth, screenheight);

            g.setColor(Color.black);
            g.fillRect(wallX[i], gap[i], wallWidth, 100);    //setting the gap in the approaching wall
        }
    }

    public void logic(){

        for(int i=0;i<2;i++) {
            if (wallX[i] <= 100 && wallX[i] + wallWidth >= 100 || wallX[i] <= 75 && wallX[i]+wallWidth>=75) {
                if ((flappyHeight + flappyVel) >= 0 && (flappyHeight + flappyVel) <= gap[i]
                        || (flappyHeight + flappyVel + 25) >= gap[i] + 100 && (flappyHeight + flappyVel + 25) <= screenheight) {
                    gameOver = true;        //if hit the wall
                }
            }
            if(flappyHeight+flappyVel<=0 || flappyHeight + flappyVel+25>= screenheight){
                gameOver = true;
            }
            if(75 > wallX[i]+wallWidth){
                score++;
            }
            if(wallX[i]+wallWidth<=0){
                wallX[i] = screenwidth;
                gap[i] = (int)(Math.random()*(screenheight-150));
            }
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        flappyAcceler+= flappyImpulse;
        flappyVel+=flappyAcceler;
        wallX[0]-=wallXVelocity;       //this is what makes the wall move towards the user
        wallX[1] -=wallXVelocity;

        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == e.VK_SPACE){
            flappyAcceler = -9;
        }
        if(code == e.VK_S){
            time.start();
        }
        if(code == e.VK_R){
            time.stop();
            flappyHeight = screenheight/4;
            flappyVel = 0;
            score = 0;
            flappyAcceler =8;
            flappyImpulse = 1;      //the impulse makes the bird fall smoothly in y direction
            wallX[0] = screenwidth;
            wallX[1] = screenwidth+screenwidth/2;
            gap[0] = (int)(Math.random()*(screenheight-150));
            gap[1] = (int)(Math.random()*(screenheight-100));
            gameOver = false;
            repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
