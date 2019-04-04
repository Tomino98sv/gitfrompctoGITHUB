package main;

public class Globals {
   static public final int MAXITEMS;
   static public final String user;
   static public final String pass;
   static public final String url;
   static public final String driver;

   static public final String mongoPort;
   static public final String mongoClientUri;
   static public final String userMongo;
   static public final String passwMongo;
   static public final String dbnameMongo;

   static{
       MAXITEMS = 30;
       user = "klaud";
       pass = "";
       url ="jdbc:mysql://localhost:3306/kauflanddb";
       driver="com.mysql.jdbc.Driver";
       //mongo variables
       mongoPort="27017";
       mongoClientUri = "mongodb://localhost:"+mongoPort;
       userMongo = "adminCreated";
       passwMongo = "0000";
       dbnameMongo = "kauflanddbMongo";
   }



}
