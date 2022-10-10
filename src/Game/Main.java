package Game;

import javax.swing.*;
import javax.swing.JFrame;
public class Main {
    public static void main(String[] args) {
        JFrame f = new JFrame();
        f.setTitle("Brick Breaker");
        f.setSize(700,600);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        f.setResizable(false);

        // object game play.
        GamePlay gamePlay = new GamePlay();
        f.add(gamePlay);
    }
}
