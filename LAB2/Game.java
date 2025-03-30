package LAB2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

class Game extends JPanel implements KeyListener, ActionListener {
    int boardWidth = 360;
    int boardHeight = 640;

    Image backgroundImage;
    Image birdImage;
    Image topPipeImage;
    Image bottomPipeImage;

    Bird bird;

    ArrayList<Pipe> pipes = new ArrayList<>();

    int pipeVelocity = -4;
    int birdVelocity = 0;
    int gravity = 1;
    boolean isGameOver = false;
    float score = 0;

    Timer gameLoop;
    Timer placePipesLoop;

    class Bird {
        int x = 10, y = 320;
        int width = 34;
        int height = 24;
        Image image;

        Bird(Image image) {
            this.image = image;
        }

        public void resetSetting() {
            x = boardWidth / 8;
            y = boardHeight / 8;
        }
    }

    class Pipe {
        int x, y;
        int width, height;
        Image image;
        boolean scored = false; 

        Pipe(Image image, int x, int y, int width, int height) {
            this.image = image;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }
    }

    Game() {
        super();
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setFocusable(true);
        addKeyListener(this);

        // Tải ảnh lên
        backgroundImage = new ImageIcon(getClass().getResource("flappybirdbg.png")).getImage();
        birdImage = new ImageIcon(getClass().getResource("flappybird.png")).getImage();
        topPipeImage = new ImageIcon(getClass().getResource("toppipe.png")).getImage();
        bottomPipeImage = new ImageIcon(getClass().getResource("bottompipe.png")).getImage();

        // Khởi tạo chim
        bird = new Bird(birdImage);

        // Khởi tạo vòng lặp game
        gameLoop = new Timer(1000 / 60, this);
        gameLoop.start();

        // Khởi tạo ống
        placePipesLoop = new Timer(1800, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placePipes();
            }
        });
        placePipesLoop.start();
    }

    public void resetSetting() {
        bird.resetSetting();
        pipes = new ArrayList<>();

        pipeVelocity = -4;
        birdVelocity = 0;
        gravity = 1;
        isGameOver = false;
        score = 0;

        gameLoop.start();
        placePipesLoop.start();
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        draw(graphics);
    }

    public void draw(Graphics graphics) {
        graphics.drawImage(backgroundImage, 0, 0, boardWidth, boardHeight, null);

        graphics.drawImage(bird.image, bird.x, bird.y, bird.width, bird.height, null);

        for (Pipe pipe : pipes) {
            graphics.drawImage(pipe.image, pipe.x, pipe.y, pipe.width, pipe.height, null);
        }

        graphics.setColor(Color.white);
        graphics.setFont(new Font("Arial", Font.BOLD, 22));
        if (isGameOver) {
            graphics.drawString("Game Over! Your score is " + (int) score, 10, 30);
        } else {
            graphics.drawString(String.valueOf((int) score), 10, 32); // Ép kiểu float thành int
        }
    }

    public void moveBird() {
        birdVelocity += gravity;
        bird.y += birdVelocity;
        bird.y = Math.min(bird.y, boardHeight - bird.height);
        bird.y = Math.max(bird.y, 0);

        if (bird.y + bird.height >= boardHeight || bird.y <= 0) {
            isGameOver = true;
            gameLoop.stop();
            placePipesLoop.stop();
        }
    }

    public void movePipes() {
        for (Pipe pipe : pipes) {
            pipe.x += pipeVelocity;

            if (!pipe.scored && pipe.x + pipe.width < bird.x) {
                score+=0.5;
                pipe.scored = true;
            }
        }
        // Loại bỏ ống ra khỏi màn hình
        pipes.removeIf(pipe -> pipe.x + pipe.width < 0);
    }

    public void placePipes() {
        int pipeWidth = 52;
        int pipeHeight = 640;
        int spaceBetweenPipes = boardHeight / 4; 
    
        int randomYPine = (int) (Math.random() * (boardHeight - spaceBetweenPipes));
    
        Pipe topPipe = new Pipe(topPipeImage, boardWidth, randomYPine - pipeHeight, pipeWidth, pipeHeight);
        pipes.add(topPipe);
    
        Pipe bottomPipe = new Pipe(bottomPipeImage, boardWidth, randomYPine + spaceBetweenPipes, pipeWidth, pipeHeight);
        pipes.add(bottomPipe);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE && !isGameOver) {
            birdVelocity = -10;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!isGameOver) {
            moveBird();
            movePipes();
            repaint();

            checkCollision();
        }
    }

    public void checkCollision() {
        for (Pipe pipe : pipes) {
            if (bird.x < pipe.x + pipe.width &&
                bird.x + bird.width > pipe.x &&
                bird.y < pipe.y + pipe.height &&
                bird.y + bird.height > pipe.y) {
                isGameOver = true;
                gameLoop.stop();
                placePipesLoop.stop();
                break;
            }
        }
    }
}