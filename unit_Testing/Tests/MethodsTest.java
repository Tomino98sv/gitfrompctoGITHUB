import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;

import static org.junit.Assert.*;

public class MethodsTest {

    String[] persons=new String[3];
    MongoDB mongoDB=MongoDB.getInstanceMongoDB();

    @Before
    public void setUp() throws Exception {
        //Insert skusobnych ziakov ci co to su
        persons[0]=mongoDB.addPidToMongoDB("Boromir","Jaroslav","0907117343");
        persons[1]=mongoDB.addPidToMongoDB("Jano","Petrovsky","9661017344");
        persons[2]=mongoDB.addPidToMongoDB("Marek","Novak","9104137339");
    }

    @After
    public void tearDown() throws Exception {
        //Remove skusobnych debilov
        for (int a=0;a<persons.length;a++){
            mongoDB.removeDocFromUser_Pid(persons[a]);
        }
    }

    @Test
    public void trianglePerimeter() {
        Methods meth = new Methods();
        assertEquals(-1,meth.trianglePerimeter(5.0,2.0,1.0),0);
    }

    @Test
    public void formText() {
    }
}