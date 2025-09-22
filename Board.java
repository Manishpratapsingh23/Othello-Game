package game.othello;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private char board[][];
    private final int boardSize=8;
    private final char p1='W';
    private final char p2='B';
    private int p1_score, p2_score;
    private int count;
    private boolean check;
    public final static int P1_wins=1;
    public final static int P2_wins=2;
    public final static int DRAW=3;
    public final static int INCOMPLETE=4;
    public final static int INVALID=5;
    public final static int[] x_axix={-1,-1,-1,0,1,1,1,0};
    public final static int[] y_axix={-1,0,1,1,1,0,-1,-1};
    public List<List<Integer>> validMove;

    public Board(){
        board=new char[boardSize][boardSize];
        for(int i=0;i<boardSize;i++){
            for(int j=0;j<boardSize;j++){
                board[i][j]=' ';
            }
        }
        board[3][3] = p1;
        board[3][4] = p2;
        board[4][3] = p2;
        board[4][4] = p1;

        p1_score=2;
        p2_score=2;
        check=false;
    }

    int getBoardSize(){
        return boardSize;
    }

    char[][] getBoard(){
        return board;
    }

    int getPlayer1Score(){
        return p1_score;
    }

    int getPlayer2Score(){
        return p2_score;
    }

    public int getGameStatus() {
        if (isBoardFull()) {
            if (p1_score > p2_score) {
                return P1_wins;
            } else if (p2_score > p1_score) {
                return P2_wins;
            } else {
                return DRAW;
            }
        }
        return INCOMPLETE;
    }

    private boolean isBoardFull() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    int move(char symbol, int x, int y) {
        // Check for invalid coordinates or occupied position
        if (x < 0 || x >= boardSize || y < 0 || y >= boardSize || board[x][y] != ' ') {
            System.out.println("Invalid move: Out of bounds or position already occupied.");
            return INVALID;
        }
    
        boolean validMove = false; // To track if the move is valid
        int flippedPieces = 0;    // To count the pieces flipped for this move
    
        // Iterate through all directions
        for (int i = 0; i < 8; i++) {
            int X = x + x_axix[i];
            int Y = y + y_axix[i];
            int c = 0;
    
            // Traverse in the current direction
            while (X >= 0 && X < boardSize && Y >= 0 && Y < boardSize && board[X][Y] != ' ' && board[X][Y] != symbol) {
                c++;
                X += x_axix[i];
                Y += y_axix[i];
            }
    
            // Check if a valid line exists
            if (c > 0 && X >= 0 && X < boardSize && Y >= 0 && Y < boardSize && board[X][Y] == symbol) {
                validMove = true; // Valid move identified
                board[x][y] = symbol; // Place the current player's symbol
                //flippedPieces=symbol=='W' ? p1_score++ : p2_score++;      // Increase score for placing a piece
    
                // Flip opponent's pieces
                X = x + x_axix[i];
                Y = y + y_axix[i];
                while (c > 0) {
                    board[X][Y] = symbol;
                    X += x_axix[i];
                    Y += y_axix[i];
                    c--;
                }
            }
        }
        // Update scores and validate the move
        if (validMove) {
            calculateScores();
            if(count==boardSize*boardSize){
                if(p1_score>p2_score){ 
                    return P1_wins; 
                }
                else if(p1_score<p2_score){
                    return P2_wins;
                }
                else{
                        return DRAW; 
                    }
            }
            return INCOMPLETE;
        } else {
            System.out.println("Invalid move: No opponent pieces to capture.");
            return INVALID;
        }
    }

    private void calculateScores(){
        p1_score=0;
        p2_score=0;
        count=0;
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (board[i][j] == 'W') {
                    p1_score++;
                    count++;
                }
                else if(board[i][j]=='B'){
                    p2_score++;
                    count++;
                }
            }
        }
    }


    boolean validPlace(char symbol, int x, int y){
        if (x < 0 || x >= boardSize || y < 0 || y >= boardSize || board[x][y] != ' ') {
            return false;
        }
    
    
        // Iterate through all directions
        for (int i = 0; i < 8; i++) {
            int X = x + x_axix[i];
            int Y = y + y_axix[i];
            int c = 0;
    
            // Traverse in the current direction
            while (X >= 0 && X < boardSize && Y >= 0 && Y < boardSize && board[X][Y] != ' ' && board[X][Y] != symbol) {
                if (board[X][Y] == ' ' || board[X][Y] == symbol) {
                    break; // Stop traversal if out of bounds, empty cell, or same symbol
                }
                c++;
                X += x_axix[i];
                Y += y_axix[i];
            }
            if(c > 0 && X >= 0 && X < boardSize && Y >= 0 && Y < boardSize && board[X][Y] == symbol){
                return true;
            }
        }
        return false;
    }

    List<List<Integer>> validMoveForPlayer(char symbol){
        validMove = new ArrayList<>();
        for(int i=0;i<boardSize;i++){
            for(int j=0;j<boardSize;j++){
                if(validPlace(symbol, i, j))
                {
                    List<Integer> lst = new ArrayList<>();
                    lst.add(i);
                    lst.add(j);
                    validMove.add(lst);
                }
            }
        }
        return validMove;
    }

    void print(){
        System.out.println("<--------------------------------------------------------->");
        System.out.println();
        for(int i=0;i<boardSize;i++){
            for(int j=0;j<boardSize;j++){
                System.out.print(i+" | "+board[i][j]+" | "+j+"     ");
            }
            System.out.println();
            System.out.println();
        }
        System.out.println();
        System.out.println("<--------------------------------------------------------->");
    }
}
