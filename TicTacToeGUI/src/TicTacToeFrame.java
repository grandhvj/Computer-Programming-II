import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeFrame extends JFrame {
    private TicTacToeButton[][] buttons;
    private JTextArea messageArea;
    private char currentPlayer;
    private char[][] board;
    private boolean gameEnded;

    public TicTacToeFrame() {
        super("Tic Tac Toe");
        currentPlayer = 'X';
        board = new char[3][3];
        buttons = new TicTacToeButton[3][3];
        gameEnded = false;

        setLayout(new BorderLayout());

        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(3, 3));
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new TicTacToeButton(i, j);
                buttons[i][j].addActionListener(new ButtonClickListener());
                boardPanel.add(buttons[i][j]);
            }
        }
        add(boardPanel, BorderLayout.CENTER);

        messageArea = new JTextArea(3, 20);
        messageArea.setEditable(false);
        add(new JScrollPane(messageArea), BorderLayout.SOUTH);

        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(e -> System.exit(0));
        add(quitButton, BorderLayout.NORTH);

        pack();
    }

    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            TicTacToeButton button = (TicTacToeButton) e.getSource();
            int row = button.getRow();
            int col = button.getCol();
            if (board[row][col] == '\0' && !gameEnded) {
                board[row][col] = currentPlayer;
                button.setText(String.valueOf(currentPlayer));
                if (checkWin(row, col)) {
                    messageArea.append("Player " + currentPlayer + " wins!\n");
                    gameEnded = true;
                    promptPlayAgain();
                } else if (isBoardFull()) {
                    messageArea.append("The game is a tie!\n");
                    gameEnded = true;
                    promptPlayAgain();
                } else {
                    currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
                }
            } else {
                messageArea.append("Invalid move!\n");
            }
        }
    }

    private boolean checkWin(int row, int col) {
        // Check row, column, and diagonals for a win
        return (board[row][0] == currentPlayer && board[row][1] == currentPlayer && board[row][2] == currentPlayer) ||
                (board[0][col] == currentPlayer && board[1][col] == currentPlayer && board[2][col] == currentPlayer) ||
                (board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer) ||
                (board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer);
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '\0') {
                    return false;
                }
            }
        }
        return true;
    }

    private void promptPlayAgain() {
        int response = JOptionPane.showConfirmDialog(this, "Do you want to play again?", "Play Again", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            resetGame();
        } else {
            System.exit(0);
        }
    }

    private void resetGame() {
        currentPlayer = 'X';
        board = new char[3][3];
        gameEnded = false;
        messageArea.setText("");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
    }
}
