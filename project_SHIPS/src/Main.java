import java.util.Random;

public class Main {

    public static void main(String[] args) {
        Main meth = new Main();
        int[][] gameBoard = new int[10][10];
        meth.shipsDeployment(gameBoard);
	// write your code here
    }

    public void shipsDeployment(int [][] gameBoard){
        Random rand = new Random();
        int counter=0;
        getCleanBoard(gameBoard);

        int a;
        int b;
        while (counter!=1){
            a = rand.nextInt(10);
            b = rand.nextInt(10);
            if (checkForTwo(gameBoard,a,b)){
                counter++;
            }
        }

        renderBoard(gameBoard);
    }

    public boolean checkForTwo(int [][] gameBoard,int a, int b){
        if (b<=8){
            if (gameBoard[a][b]!=1 && gameBoard[a][b+1]!=1){
                if (checkForMargin(gameBoard,a,b,2,true,false)){
                    gameBoard[a][b]=1;
                    gameBoard[a][b+1]=1;
                    return true;
                }else{
                    //margin isn't free
                    return false;
                }
            }else{
                //Already someone there
                return false;
            }
            }else{
            //nemozem ist horizontalne s 2 lebo na konci sa nezmesti
        }
        if (a<=8){
            if (gameBoard[a][b]!=1 && gameBoard[a+1][b]!=1){
                if (checkForMargin(gameBoard,a,b,2,false,true)){
                    gameBoard[a][b]=1;
                    gameBoard[a+1][b]=1;
                    return true;
                }else{
                    //margin isn't free
                    return false;
                }

            }else{
                //Already someone there
                return false;
            }
        }else{
            //nemozem ist vertikalne s 2 lebo na konci sa nezmesti
        }

        return false;
    }

    public boolean checkForThree(int [][] gameBoard,int a, int b){
        if (b<=7){
        }else{
            //nemozem ist horizontalne s 3 lebo na konci sa nezmesti
        }
        if (a<=7){
        }else{
            //nemozem ist vertikalne s 3 lebo na konci sa nezmesti
        }
        return false;
    }

    public boolean checkForFour(int [][] gameBoard,int a, int b){
        if (b<=6){
        }else{
            //nemozem ist horizontalne s 4 lebo na konci sa nezmesti

        }
        if (a<=6){
        }else{
            //nemozem ist vertikalne s 4 lebo na konci sa nezmesti
        }
        return false;
    }

    public boolean checkForFive(int [][] gameBoard,int a, int b){
        if (b<=5){
        }else{
            //nemozem ist horizontalne s 5 lebo na konci sa nezmesti
        }
        if (a<=5){
        }else{
            //nemozem ist vertikalne s 5 lebo na konci sa nezmesti
        }
        return false;
    }

    public boolean checkForMargin(int [][]gameboard,int a,int b, int size, boolean horizontal, boolean vertical){
        gameboard[a][b]=1;
        gameboard[a][b+1]=1;
        System.out.println("a: "+a+"b: "+b);
        for(int c=0;c<3;c++){
            for (int d=0;d<size+2;d++){
                if ((c+a)-1<0 || (d+b)-1<0 || (c+a)-1>9 || (d+b)-1>9){ }else{
                    System.out.print(gameboard[(c+a)-1][(d+b)-1]+" ");

                }
            }
            System.out.println();
        }
        return true;
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
