public class Matrix {
    private int riadky;
    private int stlpce;
    private int[] pole;
    private int[][] doubleArray;

    public Matrix(int riadky, int stlpce, int[] pole){
        this.riadky=riadky;
        this.stlpce=stlpce;
        this.pole=pole;
        initializeDoubleArray();
    }

    public Matrix(int [][] doubleArray){
        this.doubleArray=doubleArray;
    }

    public int[][] getDoubleArray() {
        return doubleArray;
    }

    private void initializeDoubleArray(){
        this.doubleArray=new int[riadky][stlpce];
        int counter=0;
        for (int a=0;a<riadky;a++){
            for (int b=0;b<stlpce;b++){
                doubleArray[a][b]=pole[counter];
                counter++;
            }
        }
    }

    public void renderMatrix(){
        System.out.println("     0. 1. 2. 3.");
        System.out.println("    _____________");
        for (int a=0;a<doubleArray.length;a++){
            System.out.print(a+".|  ");
            for (int b=0;b<doubleArray[a].length;b++){
                System.out.print(doubleArray[a][b]+"  ");
            }
            System.out.print("|");
            System.out.println();
        }
        System.out.println("    _____________");
        System.out.println();

    }

    public int[][] multiplyDoubleArray(int [][] second){
        //pocet stlpcov v prvej sa musi rovnat s poctom riadkov v druhej
        //kolko riadkov ma prva matica tolko riadkov bude mat vysledna matica
        //kolko stlpcov ma druha matica tolko stlpcov bude mat vysledna matica
        if (doubleArray[0].length!=second.length){
            return null;
        }
        System.out.println("doubleArray.length="+doubleArray.length);
        System.out.println("second[0].length="+second[0].length);
        int [][] result = new int [doubleArray.length][second[0].length];
        System.out.println("result.length="+result.length);
        for (int a=0;a<result.length;a++){
            int sum=0;
            for (int b=0;b<result[a].length;b++){
                for (int c=0;c<second.length;c++){
                    sum+=doubleArray[a][c]*second[c][b];
                }
                result[a][b]=sum;
                sum=0;
            }
        }

        return result;
    }

}
