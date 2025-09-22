package game.othello;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OthelloGUI {
    private JFrame frame;
    private JButton[][] gridButtons;
    private JLabel player1ScoreLabel;
    private JLabel player2ScoreLabel;
    private JLabel turnLabel;
    private Board board;
    private boolean isPlayer1Turn;

    public OthelloGUI() {
        frame = new JFrame("Othello Game");
        board = new Board();
        isPlayer1Turn = true; // Start with Player 1's turn
        gridButtons = new JButton[8][8];

        initializeGUI();
    }

    private void initializeGUI() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel boardPanel = new JPanel(new GridLayout(8, 8));
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                gridButtons[i][j] = new JButton();
                gridButtons[i][j].setFont(new Font("Arial", Font.BOLD, 20));
                gridButtons[i][j].setBackground(Color.GREEN);
                gridButtons[i][j].setText(String.valueOf(board.getBoard()[i][j]));
                gridButtons[i][j].addActionListener(new MoveActionListener(i, j));
                boardPanel.add(gridButtons[i][j]);
            }
        }

        JPanel infoPanel = new JPanel();
        player1ScoreLabel = new JLabel("Player 1 (W): " + board.getPlayer1Score());
        player2ScoreLabel = new JLabel("Player 2 (B): " + board.getPlayer2Score());
        turnLabel = new JLabel("Turn: Player 1 (W)");
        infoPanel.setLayout(new GridLayout(1, 3));
        infoPanel.add(player1ScoreLabel);
        infoPanel.add(player2ScoreLabel);
        infoPanel.add(turnLabel);

        frame.add(boardPanel, BorderLayout.CENTER);
        frame.add(infoPanel, BorderLayout.NORTH);

        frame.setSize(600, 600);
        frame.setVisible(true);
    }

    private void updateBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                gridButtons[i][j].setText(String.valueOf(board.getBoard()[i][j]));
            }
        }
        player1ScoreLabel.setText("Player 1 (W): " + board.getPlayer1Score());
        player2ScoreLabel.setText("Player 2 (B): " + board.getPlayer2Score());
        turnLabel.setText("Turn: " + (!isPlayer1Turn ? "Player 1 (W)" : "Player 2 (B)"));
    }

    private void checkGameStatus() {
        int status = board.getGameStatus();
        if (status == Board.P1_wins) {
            JOptionPane.showMessageDialog(frame, "Player 1 Wins!");
            System.exit(0);
        } else if (status == Board.P2_wins) {
            JOptionPane.showMessageDialog(frame, "Player 2 Wins!");
            System.exit(0);
        } else if (status == Board.DRAW) {
            JOptionPane.showMessageDialog(frame, "Game is a Draw!");
            System.exit(0);
        }
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
            char currentPlayerSymbol = isPlayer1Turn ? 'W' : 'B';
            int status = board.move(currentPlayerSymbol, x, y);
            if (status == Board.INVALID) {
                JOptionPane.showMessageDialog(frame, "Invalid Move! Try Again.");
            } else {
                updateBoard();
                checkGameStatus();
                isPlayer1Turn = !isPlayer1Turn; // Switch turn
            }
        }
    }

    public static void main(String[] args) {
        new OthelloGUI();
    }
}
