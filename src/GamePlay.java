import org.w3c.dom.css.Rect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePlay extends JPanel implements KeyListener, ActionListener {

    private boolean play = false;
    private int score = 0;

    private int totalBricks = 21;

    private Timer timer;
    private int delay = 8;

    private int playerX = 310;

    private int ballPositionX = 120;
    private int ballPositionY = 350;
    private int ballDirectionX = -1;
    private int ballDirectionY = -2;

    private MapGenerator map;

    public GamePlay() {
        map = new MapGenerator(3, 7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics graphics) {
        // Background
        graphics.setColor(Color.black);
        graphics.fillRect(1, 1, 692, 592);

        // Map
        map.draw((Graphics2D) graphics);

        // Borders
        graphics.setColor(Color.magenta);
        graphics.fillRect(0, 0, 3, 592);
        graphics.fillRect(0, 0, 692, 3);
        graphics.fillRect(681, 0, 3, 592);

        // Score
        graphics.setColor(Color.white);
        graphics.setFont(new Font("serif", Font.BOLD, 25));
        graphics.drawString("Score: " + score, 565, 30);
        graphics.drawString("Bricks: " + totalBricks, 10, 30);

        // Paddle
        graphics.setColor(Color.cyan);
        graphics.fillRect(playerX, 550, 100, 8);

        // Ball
        graphics.setColor(Color.yellow);
        graphics.fillOval(ballPositionX, ballPositionY, 20, 20);

        // Win
        if(totalBricks == 0) {
            play = false;
            ballDirectionX = 0;
            ballDirectionY = 0;
            graphics.setColor(Color.green);
            graphics.setFont(new Font("serif", Font.BOLD, 30));
            graphics.drawString("YOU WON!", 260, 300);

            graphics.setColor(Color.white);
            graphics.setFont(new Font("serif", Font.BOLD, 20));
            graphics.drawString("Press Enter To Restart", 250, 400);
        }
        // Game-over
        if(ballPositionY > 570) {
            play = false;
            ballDirectionX = 0;
            ballDirectionY = 0;
            graphics.setColor(Color.red);
            graphics.setFont(new Font("serif", Font.BOLD, 30));
            graphics.drawString("GAME OVER", 250, 300);

            graphics.setColor(Color.white);
            graphics.setFont(new Font("serif", Font.BOLD, 20));
            graphics.drawString("Press Enter To Restart", 250, 400);
        }
        graphics.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if(play) {
            Rectangle ballRect = new Rectangle(ballPositionX, ballPositionY, 20, 20);
            Rectangle paddleRect = new Rectangle(playerX, 550, 100, 8);
            if(ballRect.intersects(paddleRect)) {
                ballDirectionY = - ballDirectionY;
            }

            A: for(int i = 0; i < map.map.length; i++) {
                for(int j = 0; j < map.map[0].length; j++) {
                    if(map.map[i][j] > 0) {
                        int brickX = j * map.brickWidth + 80;
                        int brickY = i * map.brickHeight + 50;
                        int brickWidth =  map.brickWidth;
                        int brickHeight = map.brickHeight;

                        Rectangle brickRect = new Rectangle(brickX, brickY, brickWidth, brickHeight);

                        if(ballRect.intersects(brickRect)) {
                            map.setBrickValue(0, i, j);
                            totalBricks--;
                            score+= 10;

                            if(ballPositionX + 19 <= brickRect.x || ballPositionX + 1 >= brickRect.x + brickRect.width) {
                                ballDirectionX = -ballDirectionX;
                            } else {
                                ballDirectionY = -ballDirectionY;
                            }

                            break A;
                        }


                    }
                }
            }
            ballPositionX += ballDirectionX;
            ballPositionY += ballDirectionY;
            if(ballPositionX < 0) {
                ballDirectionX = -ballDirectionX;
            }
            if(ballPositionY < 0) {
                ballDirectionY = -ballDirectionY;
            }
            if(ballPositionX > 661) {
                ballDirectionX = -ballDirectionX;
            }
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (playerX >= 581) {
                playerX = 581;
            } else {
                moveRight();
            }
        }

        if(e.getKeyCode() == KeyEvent.VK_LEFT) {
            if(playerX <= 3) {
                playerX = 3;
            } else {
                moveLeft();
            }
        }

        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
            if(!play) {
                play = true;
                ballPositionX = 120;
                ballPositionY = 350;
                ballDirectionX = -1;
                ballDirectionY = -2;
                playerX = 310;
                score = 0;
                totalBricks = 21;
                map = new MapGenerator(3, 7);

                repaint();
            }
        }
    }

    public void moveRight() {
        play = true;
        playerX += 20;
    }

    public void moveLeft() {
        play = true;
        playerX -= 20;
    }
}
