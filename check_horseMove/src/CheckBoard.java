public class CheckBoard {

    private int startingZnak;
    private int startingNumber;
    private char [][] gameBoard;


    private int counter=0;
    public int opakovania=0;
    public int pocetTahov=0;

    public CheckBoard(int znak, int number){
        startingZnak=znak;
        startingNumber=number;
        gameBoard=new char [8][8];
        for (int a=0;a<gameBoard.length;a++){
            for (int b=0;b<gameBoard[a].length;b++){
                gameBoard[a][b]='O';
                if (a==startingZnak && b == startingNumber){
                    gameBoard[a][b]='X';
                }
            }
        }
    }

    public int getStartingZnak(){
        return startingZnak;
    }

    public int getStartingNumber(){
        return startingNumber;
    }

    public void oneMove(int startZnak, int startNumb){
        opakovania++;
        if (startZnak+1<8 && startNumb+2<8 && counter==2){ gameBoard[startZnak+1][startNumb+2]='I'; pocetTahov++;}
        if (startZnak-1>=0 && startNumb+2<8 && counter==2){ gameBoard[startZnak-1][startNumb+2]='I'; pocetTahov++;}
        if (startZnak+1<8 && startNumb-2>=0 && counter==2){ gameBoard[startZnak+1][startNumb-2]='I'; pocetTahov++;}
        if (startZnak-1>=0 && startNumb-2>=0 && counter==2){ gameBoard[startZnak-1][startNumb-2]='I'; pocetTahov++;}
        if (startZnak-2>=0 && startNumb-1>=0 && counter==2){ gameBoard[startZnak-2][startNumb-1]='I'; pocetTahov++;}
        if (startZnak-2>=0 && startNumb+1<8 && counter==2){ gameBoard[startZnak-2][startNumb+1]='I'; pocetTahov++;}
        if (startZnak+2<8 && startNumb-1>=0 && counter==2){ gameBoard[startZnak+2][startNumb-1]='I'; pocetTahov++;}
        if(startZnak+2<8 && startNumb+1<8 && counter==2){ gameBoard[startZnak+2][startNumb+1]='I'; pocetTahov++;}

        if (startZnak+1<8 && startNumb+2<8){if (counter<2){counter++;oneMove(startZnak+1,startNumb+2);}}
        if (startZnak-1>=0 && startNumb+2<8){if (counter<2){counter++;oneMove(startZnak-1,startNumb+2);}}
        if (startZnak+1<8 && startNumb-2>=0){if (counter<2){counter++;oneMove(startZnak+1,startNumb-2);}}
        if (startZnak-1>=0 && startNumb-2>=0){if (counter<2){counter++;oneMove(startZnak-1,startNumb-2);}}
        if (startZnak-2>=0 && startNumb-1>=0){if (counter<2){counter++;oneMove(startZnak-2,startNumb-1);}}
        if (startZnak-2>=0 && startNumb+1<8){if (counter<2){counter++;oneMove(startZnak-2,startNumb+1);}}
        if (startZnak+2<8 && startNumb-1>=0){if (counter<2){counter++;oneMove(startZnak+2,startNumb-1);}}
        if(startZnak+2<8 && startNumb+1<8){if (counter<2){counter++;oneMove(startZnak+2,startNumb+1);}}

        counter--;
        gameBoard[startingZnak][startingNumber]='X';
    }

    public void renderBoard(){
        char znak;
        System.out.println("   1  2  3  4  5  6  7  8");
        System.out.println("_________________________");
        for (int a=0;a<gameBoard.length;a++){
            znak= (char) (65+a);
            System.out.print(znak+"| ");
            for (int b=0;b<gameBoard[a].length;b++){
                System.out.print(gameBoard[a][b]+"  ");
            }
            System.out.println();
        }
        System.out.println("Pocet opakovani: "+opakovania);
        System.out.println("Pocet tahov: "+pocetTahov);
    }




}
