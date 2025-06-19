//package LAB2;

import javax.swing.*;

public class App {
    public static void main(String[] args) throws Exception {
        // Tạo cửa sổ JFrame
        JFrame mainWindow = new JFrame("Flappy Bird");
        
        int boardWidth = 360;
        int boardHeight = 640;
        mainWindow.setSize(boardWidth, boardHeight);

        mainWindow.setLocationRelativeTo(null);

        mainWindow.setResizable(false);

        Game game = new Game();
        mainWindow.add(game);

        mainWindow.setVisible(true);

        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}