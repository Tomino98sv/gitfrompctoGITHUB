
public class Methods {


    public double trianglePerimeter(double a, double b, double c){
        if (a==0||b==0||c==0){
            return -1;
        }
        if (a+b>c){
            if (b+c>a){
                if (a+c>b){
                    return a+b+c;
                }else{
                    return -1;
                }
            }else{
                return -1;
            }
        }else {
            return -1;
        }
    }

    public String formText(String text){
        String resTest="";
        if (text==null){ return null; }
        for (int i=0;i<text.length();i++) {
            if (i==0){ resTest+=text.toUpperCase().charAt(0); }
        else{ resTest+=text.toLowerCase().charAt(i); }
        }return resTest;
    }




}
