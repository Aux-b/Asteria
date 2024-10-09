import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class HashtagGame extends JPanel implements ActionListener {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int DELAY = 100;

    private int hashtagX;
    private int hashtagY;
    private int score;
    private int foodX;
    private int foodY;
    private int[] obstacleX;
    private int[] obstacleY;
    private int numObstacles;

    private Timer timer;

    public HashtagGame() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);

        hashtagX = WIDTH / 2;
        hashtagY = HEIGHT / 2;
        score = 0;
        numObstacles = 3; // Initial number of obstacles

        timer = new Timer(DELAY, this);
        timer.start();

        setFocusable(true);
        addKeyListener(new HashtagKeyListener());
        generateFood();
        generateObstacles();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawFood(g);
        drawHashtag(g);
        drawScore(g);
        drawObstacles(g);
    }

    private void drawHashtag(Graphics g) {
        g.setColor(Color.BLUE);
        g.setFont(new Font("Arial", Font.BOLD, 29));
        g.drawString("#", hashtagX, hashtagY);
    }

    private void drawFood(Graphics gra) {
        gra.setColor(Color.YELLOW);
        gra.setFont(new Font("Arial", Font.BOLD, 24));
        gra.drawString("*", foodX, foodY);
    }

    private void drawObstacles(Graphics f) {
        f.setColor(Color.WHITE);
        f.setFont(new Font("Arial", Font.BOLD, 24));
        for (int i = 0; i < numObstacles; i++) {
            f.drawString("X", obstacleX[i], obstacleY[i]);
        }
    }

    private void drawScore(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Score: " + score, 10, 20);
    }

    private void moveHashtag(int dx, int dy) {
        hashtagX += dx;
        hashtagY += dy;
        checkCollision();
        repaint();
    }

    private void checkCollision() {
        int foodSize = 24; // Size of the food
        int obstacleSize = 24; // Size of the obstacle

        if (Math.abs(hashtagX - foodX) <= foodSize && Math.abs(hashtagY - foodY) <= foodSize) {
            score++;
            generateFood();
        }

        for (int i = 0; i < numObstacles; i++) {
            if (Math.abs(hashtagX - obstacleX[i]) <= obstacleSize && Math.abs(hashtagY - obstacleY[i]) <= obstacleSize) {
                score--;
                debt();
                break; // Exit the loop if collision detected with any obstacle
            }
        }
    }

    private void generateFood() {
        int foodSize = 24; // Size of the food
        int maxFoodX = WIDTH - foodSize;
        int maxFoodY = HEIGHT - foodSize;
        foodX = (int) (Math.random() * maxFoodX);
        foodY = (int) (Math.random() * maxFoodY);
    }

    private void generateObstacles() {
        int obstacleSize = 24;
        obstacleX = new int[numObstacles];
        obstacleY = new int[numObstacles];
        int maxObstacleX = WIDTH - obstacleSize;
        int maxObstacleY = HEIGHT - obstacleSize;
        for (int i = 0; i < numObstacles; i++) {
            obstacleX[i] = (int) (Math.random() * maxObstacleX);
            obstacleY[i] = (int) (Math.random() * maxObstacleY);
        }
    }

    public void actionPerformed(ActionEvent e) {
        
    }

    private void debt() {
        if (score < 0) {
            System.out.println("You're in debt. You now need to pay the medical people back, but they're chasing after you this time. Yikes!");
            numObstacles++; // Increase the number of obstacles
            generateObstacles();
        } else {
            System.out.println("Ouch! You hit an obstacle! -1 score to pay for medical bills!");
        }
    }

    private class HashtagKeyListener extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            switch (keyCode) {
                case KeyEvent.VK_UP:
                    moveHashtag(0, -10);
                    break;
                case KeyEvent.VK_DOWN:
                    moveHashtag(0, 10);
                    break;
                case KeyEvent.VK_LEFT:
                    moveHashtag(-10, 0);
                    break;
                case KeyEvent.VK_RIGHT:
                    moveHashtag(10, 0);
                    break;
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Asteria");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(new HashtagGame());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

// Labelled some of the code so it is easier for mod-makers, assisting developers, and me to edit and remember where everything is
