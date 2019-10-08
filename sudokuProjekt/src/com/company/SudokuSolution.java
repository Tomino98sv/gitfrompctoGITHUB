package com.company;

public class SudokuSolution {

    Value[][] initalValues = new Value[9][9];

    public SudokuSolution(int[] enterValues){
        int count=0;
        for(int a=0;a<initalValues.length;a++){
            for (int b=0;b<initalValues[a].length;b++){
                initalValues[a][b] = new Value(enterValues[count]);
                count++;
            }
        }
    }

    public SudokuSolution(String enterValue){
        int count=0;
        for(int a=0;a<initalValues.length;a++){
            for (int b=0;b<initalValues[a].length;b++){
                initalValues[a][b] = new Value(Integer.parseInt(String.valueOf(enterValue.charAt(count))));
                count++;
            }
        }

    }

    private boolean checkLine(int line, int numberLooking, int column){
        for(int a=0; a<initalValues.length;a++){
            if (column != a) {
                if (initalValues[line][a].getCertainNumber() == numberLooking){
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkColumn(int column, int numberLooking, int line) {
        for(int a=0; a<initalValues.length;a++) {
            if (line != a){
                if (initalValues[a][column].getCertainNumber() == numberLooking) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean chechSquare(int linePos,int colPos, int numberLooking){
        int startLinPos = (linePos/3)*3;
        int startColPos = (colPos/3)*3;
        for(int a=startLinPos;a<startLinPos+3;a++){
            for(int b=startColPos;b<startColPos+3;b++){
                if (a==linePos && b==colPos){
                }else{
                    if (initalValues[a][b].getCertainNumber()==numberLooking){
                        return true;
                    }
                }
            }
        }
        return false;
    }


    public void solveSudoku(){

        int countEmpty;
        do{
            countEmpty=0;
            for(int a=0;a<initalValues.length;a++){
                for (int b=0;b<initalValues[a].length;b++){
                    if (initalValues[a][b].getCertainNumber()==0){
                        for (int number = 1; number<10; number++){
                            if (checkLine(a,number,b)){
                            }else if(checkColumn(b,number,a)){
                            }else if(chechSquare(a,b,number)){
                            }else{
                                initalValues[a][b].setChances(number);
                            }
                        }
                        if (initalValues[a][b].getChances().size() == 1){
                            initalValues[a][b].setCertainNumber(initalValues[a][b].getChances().get(0));
                        }else{
                            countEmpty++;
                            initalValues[a][b].cleanChances();
                        }
                    }
                }
            }
        }while(countEmpty!=0);
    }

    public void printSudoku(){
        System.out.println("\n\n");
        for(int a=0;a<initalValues.length;a++){
            for (int b=0;b<initalValues[a].length;b++){
                System.out.print("  "+initalValues[a][b].getCertainNumber());
                if ((b+1)%3 == 0){
                    System.out.print("  ");
                }
            }
            System.out.println();
            if ((a+1)%3 == 0){
                System.out.println();
            }
        }
        System.out.println("\n\n");
    }

}
