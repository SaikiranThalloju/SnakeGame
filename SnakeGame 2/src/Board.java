import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Board extends JPanel implements ActionListener {
    int height = 400;
    int width = 400;
    int MAX_DOTS = 1600;
    int DOT_SIZE = 10;
    int DOTS = 3;
    int [] x = new int[MAX_DOTS];
    int [] y = new int[MAX_DOTS];
    int apple_x;
    int apple_y;
    //Images
    Image body,apple,head,gameover;
    int DELAY = 150;
    Timer timer;

    boolean leftDirection = true;
    boolean rightDirection = false;
    boolean upDirection = false;
    boolean downDirection = false;
    boolean inGame = true;

    int Score;


    Board(){
        TAdapter tAdapter = new TAdapter();
        addKeyListener(tAdapter);
        setFocusable(true);
        setPreferredSize(new Dimension(width,height));
        setBackground(Color.black);
        initGame();
        loadImages();
      //  Restart();
    }
    public void initGame(){
        DOTS = 3;
        //initialize snake's position
        for(int i = 0;i<DOTS;i++){
            x[0] = 300;
            y[0] = 50;
            x[i] = x[0]+DOT_SIZE*i;
            y[i] = y[0];
        }
        //initializing apple's position
        locateapple();
        timer = new Timer(DELAY,this);
        timer.start();

    }
    //load images from resources folder to image object
    public void loadImages(){
        ImageIcon bodyIcon = new ImageIcon("C:\\Users\\saikiran thalloju\\IdeaProjects\\SnakeGame 2\\src\\resources\\dot.png");
        body = bodyIcon.getImage();
        ImageIcon headIcon = new ImageIcon("C:\\Users\\saikiran thalloju\\IdeaProjects\\SnakeGame 2\\src\\resources\\head.png");
        head = headIcon.getImage();
        ImageIcon appleIcon = new ImageIcon("C:\\Users\\saikiran thalloju\\IdeaProjects\\SnakeGame 2\\src\\resources\\apple.png");
        apple= appleIcon.getImage();
    }
    //snake and apple position
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        doDrawing(g);
    }
    //method for draw images
    public void doDrawing(Graphics g) {
        if(inGame) {
            g.drawImage(apple, apple_x, apple_y, this);
            for (int i = 0; i < DOTS; i++) {
                if (i == 0) {
                    g.drawImage(head, x[0], y[0], this);
                } else {
                    g.drawImage(body, x[i], y[i], this);
                }
            }
        }
            else{
                GameOver(g);
                timer.stop();
                // Restart();
            }

    }
    public void locateapple(){
        apple_x = (int)(Math.random()*39)*DOT_SIZE;
        apple_y = (int)(Math.random()*39)*DOT_SIZE;
    }
    //check collisions with border and body
    public void checkcollision(){
        //collision with body
        for(int i = 0;i<DOTS;i++){
            if(i>4 && x[0]== x[i] &&y[0]== y[i]){
                inGame = false;
            }
        }
        //collision with border
        if(x[0]<0){
            inGame = false;
        }
        if(x[0]>=width){
            inGame = false;
        }
        if(y[0]<0){
            inGame = false;
        }
        if(y[0]>=height){
            inGame = false;
        }
    }
    //display Gameover msg
    public void GameOver(Graphics g){
       String msg = "Press SPACE to Restart";
        Score = (DOTS -3)*100;
        String scoremsg = "Score : "+Integer.toString(Score);
        ImageIcon appleIcon = new ImageIcon("C:\\Users\\saikiran thalloju\\IdeaProjects\\SnakeGame 2\\src\\resources\\Game over (1).png");
        gameover = appleIcon.getImage();
        Font small = new Font("helvetica",Font.BOLD,16);
        FontMetrics fontmetrics = getFontMetrics(small);
        g.setColor(Color.WHITE);
        g.setFont(small);
      g.drawString(msg,(width-fontmetrics.stringWidth(msg))/2,height/4);
       g.drawString(scoremsg,150,3*(height/4));
        g.drawImage(gameover,160,180,this);

    }
    public void Restart(){
        inGame = true;
        initGame();
        loadImages();
        Score = 0;
        DOTS = 3;
        DELAY = 150;
        timer.start();
        repaint();


    }
    @Override
    public void actionPerformed(ActionEvent actionEvent){
        if(inGame) {
            checkApple();
            checkcollision();
            move();
        }
        repaint();

    }
    public void move(){
        for(int i = DOTS-1;i>0;i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        if(leftDirection){
            x[0]-=DOT_SIZE;
        }
        if(rightDirection){
            x[0] += DOT_SIZE;
        }
        if(upDirection){
            y[0] -= DOT_SIZE;
        }
        if(downDirection){
            y[0] += DOT_SIZE;
        }

    }
    //make snake eat food
    public void checkApple(){
        if(apple_x == x[0] && apple_y == y[0]){
            DOTS++;
            locateapple();
        }
    }
    // implement controls
     class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent KeyEvent){
            int key = KeyEvent.getKeyCode();
            if(key == KeyEvent.VK_SPACE){
                Restart();
            }

            if(key == KeyEvent.VK_LEFT && !rightDirection){
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }
            if(key == KeyEvent.VK_RIGHT && !leftDirection){
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }
            if(key == KeyEvent.VK_UP && !downDirection){
                upDirection = true;
                rightDirection = false;
                leftDirection = false;
            }
            if(key == KeyEvent.VK_DOWN && !upDirection){
                downDirection = true;
                rightDirection = false;
                leftDirection = false;
            }
        }
    }
}

