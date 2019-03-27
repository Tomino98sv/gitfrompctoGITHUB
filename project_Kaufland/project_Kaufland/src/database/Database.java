package database;

import items.Item;
import main.Bill;
import main.Globals;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Database {

    private static Database db = new Database();
    private Database() {
    }

    public static Database getInstanceDB(){
        return db;
    }

    private Connection getConnection(){
        try {
            //Class.forName instancia classy a ten string su iba packages co si si pridaval do libarys
            Class.forName(Globals.driver);
            Connection conn = DriverManager.getConnection(Globals.url,Globals.user,Globals.pass);
            if (Globals.user.equals("root")){
                System.out.println("connected as ADMIN");
            }
            System.out.println("driver is running");
            return conn;
        }catch (SQLException | ClassNotFoundException e){
            System.out.println("DRIVER IS NOT RUNNING");
            e.printStackTrace();
        }
        return null;
    }

//    public void insertNewBill(List<Item> uctenka, java.util.Date date, double finalPriceOfBill){
//        Connection conn = getConnection();
//
//        try {
//            PreparedStatement stmtBill = conn.prepareStatement("INSERT INTO bill (date,time,totalPrice) values(?,?,?)");
//            stmtBill.setDate(1,new java.sql.Date(date.getTime()));
//            stmtBill.setTime(2,new java.sql.Time(date.getTime()));
//            stmtBill.setDouble(3,finalPriceOfBill);
//            stmtBill.executeUpdate();
//            conn.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//    }

    public void insertNewBill(Bill bill) throws SQLException {
        Connection conn = getConnection();

        try {
            conn.setAutoCommit(false);
            PreparedStatement stmtBill = conn.prepareStatement("INSERT INTO bill (date,time,totalPrice) values(?,?,?)",Statement.RETURN_GENERATED_KEYS);
            stmtBill.setDate(1,new java.sql.Date(bill.getDate().getTime()));
            stmtBill.setTime(2,new java.sql.Time(bill.getDate().getTime()));
            stmtBill.setDouble(3,bill.getFinalPriceOfBill());

            stmtBill.executeUpdate();
            conn.commit();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
            conn.rollback();
        }
    }

}
