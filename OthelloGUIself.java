package game.othello;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;

public class OthelloGUIself {
    private JFrame frame;
    private JButton[][] gridButton;
    private JLabel player1ScoreLabel;
    private JLabel player2ScoreLabel;
    private JLabel turnLabel;
    private Board board;
    private boolean turn;
    private int size;
    private char symbol;
    Color defaultColor = new Color(0, 100, 0); // Default background color
    Color highlightColor = Color.YELLOW; // Highlight color for valid moves
    private ImageIcon whiteCircleIcon;
    private ImageIcon blackCircleIcon;
    private ImageIcon blinkCircleIcon;
    private Timer blinkingTimer;
    private JButton restartButton;

    public OthelloGUIself() {
        board = new Board();
        size = board.getBoardSize();
        frame = new JFrame("Othello");
        turn = true;
        gridButton = new JButton[size][size];

        // Load and scale images with debugging
        whiteCircleIcon = scaleImage("C:\\Users\\manis\\OneDrive\\Desktop\\java programmes\\game\\othello\\image\\WhiteImage.png", 50, 50);
        blackCircleIcon = scaleImage("C:\\Users\\manis\\OneDrive\\Desktop\\java programmes\\game\\othello\\image\\BlackImage.png", 40, 40);
        blinkCircleIcon = scaleImage("C:\\Users\\manis\\OneDrive\\Desktop\\java programmes\\game\\othello\\image\\BlinkImage.png", 40, 40);

        // Verify that the images exist
        // File whiteFile = new File("C:\\Users\\manis\\OneDrive\\Desktop\\java programmes\\game\\othello\\image\\WhiteImage.png");
        // File blackFile = new File("C:\\Users\\manis\\OneDrive\\Desktop\\java programmes\\game\\othello\\image\\BlackImage.png");
        // System.out.println("White image exists: " + whiteFile.exists());
        // System.out.println("Black image exists: " + blackFile.exists());
    }

    private void startGame() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel boardPanel = new JPanel(new GridLayout(size, size));
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                gridButton[i][j] = new JButton();
                gridButton[i][j].setBackground(defaultColor);

                // Set initial icons based on board state
                char cell = board.getBoard()[i][j];
                if (cell == 'W') { // Player 1 ('W')
                    gridButton[i][j].setIcon(whiteCircleIcon);
                } else if (cell == 'B') { // Player 2 ('B')
                    gridButton[i][j].setIcon(blackCircleIcon);
                } else {
                    gridButton[i][j].setIcon(null);
                }

                gridButton[i][j].addActionListener(new MoveActionListener(i, j));
                boardPanel.add(gridButton[i][j]);
            }
        }

        JPanel infoPanel = new JPanel();
        player1ScoreLabel = new JLabel("Player 1 (White): " + board.getPlayer1Score());
        player2ScoreLabel = new JLabel("Player 2 (Black): " + board.getPlayer2Score());
        turnLabel = new JLabel("Turn : Player 1 (White)");
        infoPanel.setLayout(new GridLayout(1, 3));
        infoPanel.setBackground(new Color(181, 101, 29));
        infoPanel.add(player1ScoreLabel);
        infoPanel.add(player2ScoreLabel);
        infoPanel.add(turnLabel);

        frame.add(boardPanel, BorderLayout.CENTER);
        frame.add(infoPanel, BorderLayout.NORTH);
        infoPanel.setPreferredSize(new Dimension(30, 20));

        frame.setSize(600, 600);
        frame.setResizable(true);
        frame.setVisible(true);

        symbol = 'W'; // Player 1 starts

        updateBoard(); // Initialize the board display
        List<List<Integer>> validPlace = board.validMoveForPlayer(symbol);
        //ChangeColor(highlightColor, validPlace); // Highlight valid moves
        addBlinkingEffect(validPlace);

        restartButton = new JButton("Restart Game");
        restartButton.addActionListener(e -> restartGame());
        infoPanel.add(restartButton);
    }

    private void restartGame() {
        board = new Board(); // Reset board state
        turn = true; // Reset turn to Player 1 (White)
        symbol = 'W'; // Set starting player symbol
        stopBlinkingEffect();
        updateBoard(); // Reset UI board
        List<List<Integer>> validPlace = board.validMoveForPlayer(symbol);
        //ChangeColor(highlightColor, validPlace); // Highlight valid moves for Player 1
        //turnLabel.setText("Turn : Player 1 (White)");
        addBlinkingEffect(validPlace);
        //stopBlinkingEffect();
    }

   

    private void updateBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                char cell = board.getBoard()[i][j];
                if (cell == 'W') { // Player 1 ('W')
                    gridButton[i][j].setIcon(whiteCircleIcon); // Set white circle image
                } else if (cell == 'B') { // Player 2 ('B')
                    gridButton[i][j].setIcon(blackCircleIcon); // Set black circle image
                } else {
                    gridButton[i][j].setIcon(null); // Clear the image for empty cells
                    gridButton[i][j].setText("");  // Clear any leftover text
                }
            }
        }
        player1ScoreLabel.setText("Player 1 (White): " + board.getPlayer1Score());
        player2ScoreLabel.setText("Player 2 (Black): " + board.getPlayer2Score());
        turnLabel.setText("Turn : " + (turn ? "Player 1 (White)" : "Player 2 (Black)"));
    }

    private void addBlinkingEffect(List<List<Integer>> validMoves) {
        blinkingTimer = new Timer(500, new ActionListener() { // Toggle every 500ms
            boolean visible = false;

            @Override
            public void actionPerformed(ActionEvent e) {
                visible = !visible;

                for (List<Integer> move : validMoves) {
                    int i = move.get(0);
                    int j = move.get(1);

                    if (visible) {
                        gridButton[i][j].setIcon(blinkCircleIcon); // Show dot
                    } else {
                        gridButton[i][j].setIcon(null); // Hide dot
                    }
                }
            }
        });

        blinkingTimer.start();
    }

    private void stopBlinkingEffect() {
        if (blinkingTimer != null) {
            blinkingTimer.stop();
            blinkingTimer = null; // Release timer
        }
    }

    private ImageIcon scaleImage(String filePath, int width, int height) {
        ImageIcon icon = new ImageIcon(filePath);
        Image image = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(image);
    }

    private void checkStatus() {
        int status = board.getGameStatus();
        int response = JOptionPane.CANCEL_OPTION;
        if (status == Board.P1_wins) {
            response = JOptionPane.showConfirmDialog(frame, "Player 1 Wins! Restart Game?", "Game Over", JOptionPane.YES_NO_OPTION);
        } else if (status == Board.P2_wins) {
            response = JOptionPane.showConfirmDialog(frame, "Player 2 Wins! Restart Game?", "Game Over", JOptionPane.YES_NO_OPTION);
        } else if (status == Board.DRAW) {
            response = JOptionPane.showConfirmDialog(frame, "It's a Draw! Restart Game?", "Game Over", JOptionPane.YES_NO_OPTION);
        }
        if (response == JOptionPane.YES_OPTION) restartGame();
        else if(response == JOptionPane.NO_OPTION) System.exit(0);
    }
    

    private class MoveActionListener implements ActionListener {
        private int x;
        private int y;

        public MoveActionListener(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            symbol = turn ? 'W' : 'B'; // Determine current player's symbol

            List<List<Integer>> validPlace = board.validMoveForPlayer(symbol);
           // ChangeColor(defaultColor, validPlace); // Clear previous highlights
            stopBlinkingEffect();
            int status = board.move(symbol, x, y);
            if (status == Board.INVALID) {
                JOptionPane.showMessageDialog(frame, "Invalid Move! Try again!");
               // ChangeColor(highlightColor, validPlace); // Re-highlight valid moves
                addBlinkingEffect(validPlace); 
                return;
            }
            turn = !turn; // Switch player turn
            symbol = turn ? 'W' : 'B';
            updateBoard(); // Update the board
            checkStatus(); // Check game state
            

            validPlace = board.validMoveForPlayer(symbol);
            if (validPlace.size() == 0) {
                skipTurn(); // Handle skipped turn
            } else {
                //ChangeColor(highlightColor, validPlace); // Highlight valid moves
                addBlinkingEffect(validPlace); 
            }
        }
    }

    private void ChangeColor(Color color, List<List<Integer>> validPlace) {
        for (List<Integer> lst : validPlace) {
            gridButton[lst.get(0)][lst.get(1)].setBackground(color);
        }
    }

    private void skipTurn() {
        JOptionPane.showMessageDialog(frame, "No valid moves! Turn skipped.");
        turn = !turn;
        symbol = turn ? 'W' : 'B';
        updateBoard();
        List<List<Integer>> validPlace = board.validMoveForPlayer(symbol);
        //ChangeColor(highlightColor, validPlace);
        addBlinkingEffect(validPlace);
        turnLabel.setText("Turn : " + (turn ? "Player 1 (W)" : "Player 2 (B)"));
        //ChangeColor(highlightColor, validPlace);
    }

    public static void main(String[] args) {
        OthelloGUIself O = new OthelloGUIself();
        O.startGame();
    }
}
