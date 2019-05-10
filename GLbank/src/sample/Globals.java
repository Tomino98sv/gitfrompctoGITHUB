package sample;

import database.Database;

public class Globals {
    static public final String user;
    static public final String pass;
    static public final String url;
    static public final String driver;
    static public final Database db;

    static {
        user="glbank";
        pass="password";
        url="jdbc:mysql://itsovy.sk:3306/glbank";
        driver="com.mysql.jdbc.Driver";
        db=Database.getInstanceDatabase();
    }


}
