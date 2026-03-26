import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class TicTacToe{
    Random random = new Random();
    int boardWidth= 600;
    int boardHeight= 650; //50 px extra for top panel of text box

    JFrame frame= new JFrame();
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();
    // JPanel restartPanel = new JPanel();
    // JLabel restartLabel = new JLabel();

    JButton[][] board= new JButton[3][3];
    String playerX= "X";
    String playerO= "O";
    String currplayer= playerX;
    boolean gameover=false;
    int turns=0;

    TicTacToe() {
        frame.setVisible(true);
        frame.setSize(boardWidth,boardHeight);
        frame.setLocationRelativeTo(null); //at the center
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        textLabel.setBackground(Color.DARK_GRAY);
        textLabel.setForeground(Color.BLACK);
        textLabel.setFont(new Font("Arial", Font.BOLD, 30));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("TIC-TAC-TOE");
        textLabel.setLayout(new BorderLayout());

        textPanel.add(textLabel);
        frame.add(textPanel, BorderLayout.NORTH);

        boardPanel.setLayout(new GridLayout(3,3));
        boardPanel.setBackground(Color.DARK_GRAY);
        frame.add(boardPanel);

        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                JButton tile= new JButton();
                board[i][j]=tile;
                boardPanel.add(tile);

                tile.setBackground(Color.black);
                tile.setForeground(Color.red);
                tile.setFont(new Font("Arial", Font.BOLD,120));
                tile.setOpaque(true);
                tile.setFocusable(false);

                tile.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e){
                        if(gameover) return;
                        JButton tile= (JButton) e.getSource();
                        if (tile.getText().equals("")){
                            tile.setText(currplayer);
                            tile.setForeground(Color.BLACK);
                            turns++;
                            checkWinner();
                            if(!gameover){
                                currplayer= currplayer==playerX ? playerO : playerX;                                
                                textLabel.setText(currplayer + " 's turn.");
                                if(currplayer == playerO) move(currplayer,tile);
                            }
                        }
                    }
                });
            }
        }
    }  
    void move(String player, JButton tile){
        while(!gameover){
            int row= random.nextInt(3);
            int col= random.nextInt(3);
            if(board[row][col].getText().equals("")){
                board[row][col].setText(player);
                tile.setForeground(Color.BLUE);
                turns++;
                checkWinner();
                if(!gameover) currplayer=playerX;
                textLabel.setText(currplayer + " 's turn.");
                return;
            }
        }
    }    
    void checkWinner(){
        //horizontal
        for(int r=0; r<3; r++){
            if(board[r][0].getText()=="") continue;
            if(board[r][0].getText()==board[r][1].getText() && board[r][1].getText()==board[r][2].getText()){
                for(int k=0; k<3; k++){
                    setWinner(board[r][k]);
                }
                gameover=true;
                if(restart(currplayer + " wins...")) setDefault(board[r][0], board[r][1], board[r][2]);
                return;
            }
        }
        // vertical
        for(int c=0; c<3; c++){
            if(board[0][c].getText()=="") continue;
            if(board[0][c].getText()==board[1][c].getText() && board[1][c].getText()==board[2][c].getText()){
                for(int k=0; k<3; k++){
                    setWinner(board[k][c]);
                }
                gameover=true;
                if(restart(currplayer + " wins...")) setDefault(board[0][c], board[1][c], board[2][c]);
                return;
            }
        }
        // Diagonal
        if(board[0][0].getText()==board[1][1].getText() && board[1][1].getText()==board[2][2].getText() && board[0][0].getText()!=""){                
            for(int i=0; i<3; i++)
                setWinner(board[i][i]);
            gameover=true;
            if(restart(currplayer + " wins...")) setDefault(board[0][0], board[1][1], board[2][2]);
            return;
        }
        if(board[0][2].getText()==board[1][1].getText() && board[1][1].getText()==board[2][0].getText() && board[0][2].getText()!=""){
            for(int i=0; i<3; i++)
                setWinner(board[3-i-1][i]);
            gameover=true;
            if(restart(currplayer + " wins...")) setDefault(board[2][0], board[1][1], board[0][2]);
            return;
        }
        if(turns==9){
            for(int i=0; i<3; i++){
                for(int j=0; j<3; j++){
                    setTie(board[i][j]);
                }
            }
            textLabel.setText("IT'S A TIE...GAME OVER");
            gameover=true;
            if(restart(" TIE ")){
                for(int i=0; i<3; i++){
                    setDefault(board[i][0], board[i][1], board[i][2]);
                }
            }
        }
    } 
    void setTie(JButton tile){
        tile.setBackground(Color.RED);
        tile.setForeground(Color.RED);
    }
    void setWinner(JButton tile){
        tile.setBackground(Color.GREEN);
        tile.setForeground(Color.GREEN);
        textLabel.setText(currplayer + " is the Winner");
    }
    boolean restart(String s){
        int choice= JOptionPane.showOptionDialog(null, "Do you want to play again?", s+ " Game Over...",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, JOptionPane.YES_OPTION);
        if(choice == JOptionPane.YES_OPTION){
            gameover=false;
            currplayer=playerO;
            turns=0;
            for(int i=0; i<3; i++){
                for(int j=0; j<3; j++){
                    board[i][j].setText("");
                }
            }
            return true;
        }
        else{
            frame.dispose();
            return false;
        }
        // if(gameover){
        //     restartLabel.setBackground(Color.BLUE);
        //     restartLabel.setForeground(Color.BLACK);
        //     restartLabel.setFont(new Font("Arial", Font.BOLD, 120));
        //     restartLabel.setText("RESTART? ");
        //     restartLabel.setLayout(new BorderLayout());
        //     restartPanel.add(restartLabel);
        //     restartPanel.setBackground(Color.BLUE);
        //     frame.add(restartPanel, BorderLayout.CENTER);
        // }
    }
    void setDefault(JButton tile1, JButton tile2, JButton tile3){
        tile1.setBackground(Color.BLACK);
        tile2.setBackground(Color.BLACK);
        tile3.setBackground(Color.BLACK);
    }

    public static void main(String args[]){
        new TicTacToe();
    }
}