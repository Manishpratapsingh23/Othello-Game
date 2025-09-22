package game.othello;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Othello {

    private Player player1,player2;
    private Board board;

    public static void main(String[] args) {
        Othello o=new Othello();
        o.startGame();
    }
    
    void startGame(){
        Scanner sc=new Scanner(System.in);
        player1=takeInput(1);
        player2=takeInput(2);
        board=new Board();
        board.print();
        boolean turn=true;
        int status=board.INCOMPLETE;
        while(status == board.INCOMPLETE || status == board.INVALID){
            if(turn){
                int x = -1, y = -1;
                System.out.println(player1.getName()+"'s chance");
                try {
                    System.out.print("Enter x: ");
                    x = sc.nextInt();
                    System.out.print("Enter y: ");
                    y = sc.nextInt();
                    if(x==-2 || y==-2) return;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input! Please enter integers.");
                    sc.nextLine(); // clear the invalid input
                    continue; // skip this turn and retry
                }
                status= board.move('W',x,y);
                if(status!=board.INVALID){
                    turn=false;
                    board.print();
                }else{
                    System.out.println("Invalid move! Try Agin!");
                }
            } else{
                int x=-1, y=-1;
                System.out.println(player2.getName()+"'s chance");
                try {
                    System.out.print("Enter x: ");
                    x = sc.nextInt();
                    System.out.print("Enter y: ");
                    y = sc.nextInt();
                    if(x==-2 || y==-2) return;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input! Please enter integers.");
                    sc.nextLine(); // clear the invalid input
                    continue;
                }
                status= board.move('B',x,y);
                if(status!=board.INVALID){
                    turn=true;
                    board.print();
                }else{
                    System.out.println("Invalid move! Try Agin!");
                }
            }
        }
        if(status==Board.P1_wins){
            System.out.println(player1.getName()+" Wins....!!");
        }
        if(status==Board.P2_wins){
            System.out.println(player2.getName()+" Wins....!!");
        }
        if(status==Board.DRAW){
            System.out.println("DRAW....!!");
        }
    }

    Player takeInput(int num){
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter Player "+num+" name : ");
        String name=sc.nextLine();
        Player p=new Player(name);
        return p;
    }
}
