public class Main {

    public static void main(String[] args) {
        Main meth = new Main();
        int[][] gameBoard = new int[10][10];
        meth.getCleanBoard(gameBoard);
        meth.renderBoard(gameBoard);
	// write your code here
    }

    public void getCleanBoard(int [][] gameBoard){
        for (int a=0;a<gameBoard.length;a++){
            for (int b=0;b<gameBoard[a].length;b++){
                gameBoard[a][b]=0;
            }
        }
    }

    public void renderBoard(int [][] gameBoard){
        System.out.println("    1. 2. 3. 4. 5. 6. 7. 8. 9. 10.");
        System.out.println("    ______________________________");
        for (int a=0;a<gameBoard.length;a++){
            if (a==9){
                System.out.print(a+1+". |");
            }else{
                System.out.print(a+1+".  |");
            }

            for (int b=0;b<gameBoard[a].length;b++){
                System.out.print(gameBoard[a][b]+"  ");
            }
            System.out.print("|");
            System.out.println();
        }
        System.out.println("    ______________________________");
    }


}
