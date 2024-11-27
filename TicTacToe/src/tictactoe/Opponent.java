package tictactoe;

import javax.swing.JButton;

public class Opponent {
    
    private String playerO = "O"; // AI symbol
    private String playerX = "X"; // Player symbol

    // Function to make the AI's move using the Minimax algorithm
    public void aiMove(JButton[][] board, String currentPlayer) {
        int bestScore = Integer.MIN_VALUE;
        int bestMoveRow = -1;
        int bestMoveCol = -1;

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (board[r][c].getText().equals("")) {
                    board[r][c].setText(playerO); // Try AI move
                    int score = minimax(board, 0, false);
                    board[r][c].setText(""); // Undo move

                    if (score > bestScore) {
                        bestScore = score;
                        bestMoveRow = r;
                        bestMoveCol = c;
                    }
                }
            }
        }

        board[bestMoveRow][bestMoveCol].setText(playerO);
    }

    // Minimax algorithm
    private int minimax(JButton[][] board, int depth, boolean isMaximizing) {
        int score = evaluate(board);
        if (score == 10 || score == -10) {
            return score;
        }
        if (isBoardFull(board)) {
            return 0;
        }

        if (isMaximizing) {
            int best = Integer.MIN_VALUE;
            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < 3; c++) {
                    if (board[r][c].getText().equals("")) {
                        board[r][c].setText(playerO);
                        best = Math.max(best, minimax(board, depth + 1, false));
                        board[r][c].setText("");
                    }
                }
            }
            return best;
        } else {
            int best = Integer.MAX_VALUE;
            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < 3; c++) {
                    if (board[r][c].getText().equals("")) {
                        board[r][c].setText(playerX);
                        best = Math.min(best, minimax(board, depth + 1, true));
                        board[r][c].setText("");
                    }
                }
            }
            return best;
        }
    }

    // Evaluate the board and return a score
    private int evaluate(JButton[][] board) {
        // Check rows, columns, and diagonals
        // +10 for AI win, -10 for player win, 0 for tie
        for (int r = 0; r < 3; r++) {
            if (board[r][0].getText().equals(board[r][1].getText()) && board[r][1].getText().equals(board[r][2].getText())) {
                if (board[r][0].getText().equals(playerO)) return 10;
                else if (board[r][0].getText().equals(playerX)) return -10;
            }
        }

        for (int c = 0; c < 3; c++) {
            if (board[0][c].getText().equals(board[1][c].getText()) && board[1][c].getText().equals(board[2][c].getText())) {
                if (board[0][c].getText().equals(playerO)) return 10;
                else if (board[0][c].getText().equals(playerX)) return -10;
            }
        }

        if (board[0][0].getText().equals(board[1][1].getText()) && board[1][1].getText().equals(board[2][2].getText())) {
            if (board[0][0].getText().equals(playerO)) return 10;
            else if (board[0][0].getText().equals(playerX)) return -10;
        }

        if (board[0][2].getText().equals(board[1][1].getText()) && board[1][1].getText().equals(board[2][0].getText())) {
            if (board[0][2].getText().equals(playerO)) return 10;
            else if (board[0][2].getText().equals(playerX)) return -10;
        }

        return 0; // Tie or game still ongoing
    }

    private boolean isBoardFull(JButton[][] board) {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (board[r][c].getText().equals("")) {
                    return false;
                }
            }
        }
        return true;
    }
}
    

