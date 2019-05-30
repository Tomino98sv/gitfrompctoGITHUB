import javafx.scene.control.CheckBox;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	// write your code here

        Scanner input = new Scanner(System.in);
        CheckBoard checkBoard;
        System.out.println("Enter starting position");
        String position = input.nextLine();

        char znak = position.toUpperCase().charAt(0);
        int number=0;
        try{
            number = Integer.parseInt(String.valueOf(position.charAt(1)));
            if (number<1 || number>8){
                System.out.println("Not valid number");
            }else if(znak<'A' || znak>'H'){
                System.out.println("Not valid char");
            }else{
                System.out.println("Sending position "+znak+""+number);
                checkBoard = new CheckBoard((Integer.valueOf(znak)-65),number-1);
                checkBoard.oneMove(checkBoard.getStartingZnak(),checkBoard.getStartingNumber());
                checkBoard.renderBoard();
            }
        }catch (NumberFormatException e){
            e.printStackTrace();
            System.out.println("Non a number");
        }

    }
}
