package main;

public class Globals {
   static public final int MAXITEMS;
   static public final String user;
   static public final String pass;
   static public final String url;
   static public final String driver;

   static{
       MAXITEMS = 30;
       user = "klaud";
       pass = "";
       url ="jdbc:mysql://localhost:3306/kauflanddb";
       driver="com.mysql.jdbc.Driver";
   }



}
