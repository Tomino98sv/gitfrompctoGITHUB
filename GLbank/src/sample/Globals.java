package sample;

import database.Database;

public class Globals {
    static public final String user;
    static public final String pass;
    static public final String url;
    static public final String driver;
    static public final Database db;

    static {
        user="root";
        pass="";
        url="jdbc:mysql://localhost:3306/glbank";
        driver="com.mysql.jdbc.Driver";
        db=Database.getInstanceDatabase();
    }


}
