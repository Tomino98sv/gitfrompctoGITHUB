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
        int counter5=0;
        int counter4=0;
        int counter3=0;
        int counter2=0;
        getCleanBoard(gameBoard);

        int a;
        int b;
        while (true){
            a = rand.nextInt(10);
            b = rand.nextInt(10);
            if (counter5<1){
                if (checkIfIsConstructable(gameBoard,a,b,5)){
                    counter5++;
                }
            }
            if (counter5==1){
                if (counter4<2){
                    if (checkIfIsConstructable(gameBoard,a,b,4)){
                        counter4++;
                    }
                }
                if (counter4==2){
                    if (counter3<3){
                        if (checkIfIsConstructable(gameBoard,a,b,3)){
                            counter3++;
                        }
                    }
                    if (counter3==3){
                        if (counter2<4){
                            if (checkIfIsConstructable(gameBoard,a,b,2)){
                                counter2++;
                            }
                        }
                        if (counter2==4){
                            break;
                        }
                    }
                }
            }
        }

        renderBoard(gameBoard);
    }

    public boolean checkIfIsConstructable(int [][] gameBoard,int a, int b,int size){
        if (b<=10-size){   //zlava do prava sa zmesti
                if (checkForMargin(gameBoard,a,b,size,true,false)){ //kontroluje sa margin do prava
                    for (int c =0;c<size;c++){
                        gameBoard[a][b+c]=1;
                    }
                    return true;
                }else{    //do prava neni volne pozrem na lavo
                    if (b+1>=size){   // pozrem ci sa vobec zmesti na na lavo
                        if (checkForMarginLeftUp(gameBoard,a,b,size,true,false)){
                            for (int c =0;c<size;c++){
                                gameBoard[a][b-c]=1;
                            }
                            return true;
                        }
                    }
                }
            }else{
            if (b+1>=size){   // pozrem ci sa vobec zmesti na na lavo
                if (checkForMarginLeftUp(gameBoard,a,b,size,true,false)){
                    for (int c =0;c<size;c++){
                        gameBoard[a][b-c]=1;
                    }
                    return true;
                }
            }
        }   //HORIZONT


        if (a<=10-size){    //zhora dole ci sa zmesti
                if (checkForMargin(gameBoard,a,b,size,false,true)){  // pozrem ci margin dole je volny
                    for (int c=0;c<size;c++){
                        gameBoard[a+c][b]=1;
                    }
                    return true;
                }else{
                    if (a+1>=size){   // pozrem ci sa vobec zmesti na hor
                        if (checkForMarginLeftUp(gameBoard,a,b,size,false,true)){  //pozrem ci margin zdola na hor je volny
                            for (int c =0;c<size;c++){
                                gameBoard[a-c][b]=1;
                            }
                            return true;
                        }
                    }
                }
        }else{   //zhora dole sa nezmesti
            if (a+1>=size){   // pozrem ci sa vobec zmesti hore
                if (checkForMarginLeftUp(gameBoard,a,b,size,false,true)){   // pozrem ci margin hore je volny
                    for (int c =0;c<size;c++){
                        gameBoard[a-c][b]=1;
                    }
                    return true;
                }
            }
        }   //VERTICAL
        return false;
    }

    public boolean checkForMargin(int [][]gameboard,int a,int b, int size, boolean horizontal, boolean vertical){
        if (horizontal){
            System.out.println("a: "+a+"b: "+b);
            for(int c=0;c<3;c++){           // na riadky okolo
                for (int d=0;d<size+2;d++){   // na stlpce okolo
                    if ((c+a)-1<0 || (d+b)-1<0 || (c+a)-1>9 || (d+b)-1>9){ }else{  //aby neslo za pole hodnot
                        System.out.print(gameboard[(c+a)-1][(d+b)-1]+" ");
                        if (gameboard[(c+a)-1][(d+b)-1]==1){    // ci je na kontrolovanom mieste 1
                            System.out.println("FALSE");
                            return false;
                        }
                    }
                }
                System.out.println();
            }
            System.out.println("True");
            return true;
        }else if(vertical){
            System.out.println("Vertical margin");
            System.out.println("a: "+a+"b: "+b);
            for(int c=0;c<size+2;c++){           // na riadky okolo
                for (int d=0;d<3;d++){   // na stlpce okolo
                    if ((c+a)-1<0 || (d+b)-1<0 || (c+a)-1>9 || (d+b)-1>9){ }else{  //aby neslo za pole hodnot
                        System.out.print(gameboard[(c+a)-1][(d+b)-1]+" ");
                        if (gameboard[(c+a)-1][(d+b)-1]==1){    // ci je na kontrolovanom mieste 1
                            System.out.println("FALSE");
                            return false;
                        }
                    }
                }
                System.out.println();
            }
            System.out.println("True");

            return true;
        }else{
            return false;
        }
    }

    public boolean checkForMarginLeftUp(int [][]gameboard,int a,int b, int size, boolean horizontal, boolean vertical){

        if (horizontal){
            System.out.println("Horizontal left margin");
            System.out.println("a: "+a+"b: "+b);
            for(int c=0;c<3;c++){           // na riadky okolo
                for (int d=b+1;d>(b+1)-size-2;d--){   // na stlpce okolo
                    System.out.println("["+((c+a)-1)+"]["+d+"]");
                    if ((a+c)-1<0 || d<0 || (c+a)-1>9 || d>9){ }else{  //aby neslo za pole hodnot
                        if (gameboard[(c+a)-1][d]==1){    // ci je na kontrolovanom mieste 1
                            System.out.println("FALSE");
                            return false;
                        }
                    }
                }
                System.out.println();
            }
            System.out.println("True");
            return true;
        }else if(vertical){
            //SKOPIROVANE ZATIAL LEN CHECKFORMARGIN
            System.out.println("Vertical up margin");
            System.out.println("a: "+a+"b: "+b);
            for(int c=a+1;c>(a+1)-size-2;c--){           // na riadky okolo
                for (int d=0;d<3;d++){   // na stlpce okolo
                    System.out.println("["+c+"]["+((d+b)-1)+"]");
                    if (c<0 || (d+b)-1<0 || c>9 || (d+b)-1>9){ }else{  //aby neslo za pole hodnot
                        if (gameboard[c][(d+b)-1]==1){    // ci je na kontrolovanom mieste 1
                            System.out.println("FALSE");
                            return false;
                        }
                    }
                }
                System.out.println();
            }
            System.out.println("True");
            return true;
        }else{
            return false;
        }
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
