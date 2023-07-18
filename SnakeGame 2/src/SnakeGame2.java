import javax.swing.*;
public class SnakeGame2 extends JFrame {
    Board board;
    SnakeGame2(){
        board = new Board();
        add(board);
        pack();
       // setBounds(10,10,905,700);
        setResizable(false);
        setVisible(true);

//

    }
    public static void main(String[] args){
        //Initialize SnakeGame
        SnakeGame2 snakegame = new SnakeGame2();
    }
}
