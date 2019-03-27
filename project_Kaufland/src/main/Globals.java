package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Globals {
   static public final int MAXITEMS;
   static public final String user;
   static public final String pass;
   static public final String url;
   static public final String driver;

   static{
       MAXITEMS = 7;
       user = "klaud";
       pass = "";
       url ="jdbc:mysql://localhost:3306/kauflanddb";
       driver="com.mysql.jdbc.Driver";
   }



}
