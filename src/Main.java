import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        JFrame window = new JFrame();
        GamePlay gamePlay = new GamePlay();

        window.setBounds(10, 10, 700, 600);
        window.setTitle("Brick Breaker");
        window.setResizable(false);
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.add(gamePlay);

    }
}
