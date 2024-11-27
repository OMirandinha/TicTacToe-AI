
package tictactoe;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TicTacToe {
    
    // Grid dimensions
    int boardWidth = 600;
    int boardHeight = 650;

    JFrame frame = new JFrame("Tic-Tac-Toe");
    JLabel textLabel = new JLabel();
    JPanel panel = new JPanel();
    JPanel boardPanel = new JPanel();
    
    // determining board + players
    JButton[][] board = new JButton[3][3];
    String playerX = "X";
    String playerO = "O";
    String currentPlayer = playerX;
    
    boolean gameOver = false;
    int turns = 0;
    
    Opponent ai = new Opponent(); 
    
    TicTacToe(){
        //visual stuff
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        
        textLabel.setBackground(Color.darkGray);
        textLabel.setForeground(Color.white);
        textLabel.setFont(new Font("Arial", Font.BOLD, 50));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Tic-Tac-Toe");
        textLabel.setOpaque(true);
        
        panel.setLayout(new BorderLayout());
        panel.add(textLabel);
        frame.add(panel, BorderLayout.NORTH);
        
        // Reset button, only visible after match ends
        JButton restartButton = new JButton("Restart");
        restartButton.setFont(new Font("Arial", Font.BOLD, 20));
        restartButton.setBackground(Color.darkGray);
        restartButton.setForeground(Color.white);
        restartButton.setFocusable(false);
        restartButton.setVisible(false);
        restartButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                restartGame(restartButton);
            }
        });

        // Adds restart button below the title
        panel.add(restartButton, BorderLayout.SOUTH); 
        frame.add(panel, BorderLayout.NORTH);
        
        boardPanel.setLayout(new GridLayout(3, 3));
        boardPanel.setBackground(Color.darkGray);
        frame.add(boardPanel);
        
        for (int r = 0; r < 3; r++){
            for (int c = 0; c<3; c++){
                JButton tile = new JButton();
                board[r][c] = tile;
                boardPanel.add(tile);
                
                tile.setBackground(Color.DARK_GRAY);
                tile.setForeground(Color.white);
                tile.setFont(new Font("Arial", Font.BOLD, 120));
                tile.setFocusable(false);
                //tile.setText(currentPlayer);
                
                // Checks for player clicks
                tile.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        if (gameOver) return;
                        JButton tile = (JButton) e.getSource();
                        if (tile.getText() == ""){
                            tile.setText(currentPlayer);
                            turns++;
                            checkWinner(restartButton);
                            
                            // Changes the text on the top part
                            if(!gameOver){
                                currentPlayer = currentPlayer == playerX ? playerO : playerX;
                                textLabel.setText(currentPlayer + "'S turn");
                                
                                
                            }
                            
                            if (currentPlayer == playerO) {
            // Makes the AI move
            ai.aiMove(board, currentPlayer); 
            turns++;
            checkWinner(restartButton);
            if (!gameOver) {
                currentPlayer = playerX;
                textLabel.setText(currentPlayer + "'S turn");
            }
                            }
                            
                            
                        
                            
                        }
                        
                    }
                });
            }
        }
    
    }    
    
    void restartGame(JButton restartButton) {
        // Resets the board
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                board[r][c].setText("");
                board[r][c].setBackground(Color.DARK_GRAY);
                board[r][c].setForeground(Color.white);
            }
        }
        
        // Resets the game 
        turns = 0;
        gameOver = false;
        currentPlayer = playerX;  // Player X starts the game
        textLabel.setText("Tic-Tac-Toe");
        restartButton.setVisible(false); 
    }
    
    void checkWinner(JButton restartButton) {
        
        // Horizontal
        for (int r = 0; r < 3; r++){
            if (board[r][0].getText() == "") continue;
            
            if (board[r][0].getText() == board[r][1].getText() &&
                board[r][1].getText() == board[r][2].getText()) {
                
                for (int i =0; i < 3; i++){
                    setWinner(board[r][i]);
                }
                
                
                
                gameOver = true;
                  restartButton.setVisible(true);
        
                return;
                
            }
          
        }
        
        // Vertical
        for (int c = 0; c < 3; c++){
            if (board[0][c].getText() == "") continue;
            
            if (board[0][c].getText() == board[1][c].getText() &&
                board[1][c].getText() == board[2][c].getText()) {
                for (int i = 0; i < 3; i++){
                    setWinner(board[i][c]);
                }
                gameOver = true;
                restartButton.setVisible(true);

                return;
            }
        }
        
        // Diagonals
        if (board[0][0].getText() == board[1][1].getText() &&
            board[1][1].getText() == board[2][2].getText() &&
            board[0][0].getText() != ""){    
            for (int i = 0; i < 3; i++) {
                setWinner(board[i][i]);
            }  
            gameOver = true;
            restartButton.setVisible(true);
            return;
        }
        
        // Anti-Diagonal
        if(board[0][2].getText() == board[1][1].getText() &&
           board[1][1].getText() == board[2][0].getText() &&
           board[0][2].getText() != ""){
           setWinner(board[0][2]);
           setWinner(board[1][1]);
           setWinner(board[2][0]);
           gameOver = true;
           restartButton.setVisible(true);
           return;
        }
        
        // If turn limit is reached, a tie is declared
        if (turns == 9) {
            for (int r = 0; r < 3; r++){
                for (int c = 0; c < 3; c++){
                    setTie(board[r][c]);
                }
            }
            
            gameOver = true;
            restartButton.setVisible(true);
           
        }
    }
    
    
    void setWinner(JButton tile){
        tile.setForeground(Color.green);
        tile.setBackground(Color.gray);
        textLabel.setText(currentPlayer + " is the winner!");
        }
    
    void setTie(JButton tile) {
        tile.setForeground(Color.orange);
        tile.setBackground(Color.gray);
        textLabel.setText("Tie!");
        
    }
    
    
       public static void main(String[] args) {
        // Create an instance of the TicTacToe class
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TicTacToe();  
            }
        });
}
} 
    
                