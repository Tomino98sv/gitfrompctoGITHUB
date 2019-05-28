public class Main {

    public static void main(String[] args) {
//        int pole[]={2,3,1,8,7,4,5,2,8,2,3,9,0,2,4,6};
//        Matrix matrix = new Matrix(4,4,pole);
//        matrix.renderMatrix();
//
//        Matrix matrix1= new Matrix(matrix.getDoubleArray());
//        matrix1.renderMatrix();
        int pole[]={1,2,3,4};
        Matrix matrix = new Matrix(1,4,pole);
        matrix.renderMatrix();

        int pole1[]={1,2,3,4,5,1,2,3,4,5,1,2,3,4,5,1,2,3,4,5};
        Matrix matrix1 = new Matrix(4,5,pole1);
        matrix1.renderMatrix();

        int [][] result = matrix.multiplyDoubleArray(matrix1.getDoubleArray());
        Matrix matrix2 = new Matrix(result);
        matrix2.renderMatrix();

    }
}
