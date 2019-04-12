package database;

import persons.Client;
import persons.Employee;
import sample.Globals;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    static final String SQL1 = "select employee.id as employeeId, employee.lname,employee.fname,loginemp.id as loginId,loginemp.login,loginemp.password, positions.id as positionId, positions.name as nameposition from employee inner join loginemp on employee.id=loginemp.id inner join positions on employee.position=positions.id where loginemp.login like ? and loginemp.password like ?;";
    static final String SQL2 = "select * from client group by client.id";

    private static Database database = new Database();
    private Database(){

    }

    public static Database getInstanceDatabase(){
        return database;
    }

    private Connection getConnection(){
        try {
            Class.forName(Globals.driver);
            Connection connect = DriverManager.getConnection(Globals.url,Globals.user,Globals.pass);
            System.out.println("Driver is runnig");
            return connect;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Employee getEmployee(String login, String password){
        Connection conn = getConnection();
        int employeeId=0;
        String lname="";
        String fname="";

        int loginId=0;

        int positionId=0;
        String position="";
        try{
            PreparedStatement statement = conn.prepareStatement(SQL1);
            statement.setString(1,login);
            statement.setString(2,password);
            ResultSet result=statement.executeQuery();

            while (result.next()){
                employeeId = result.getInt("employeeId");
                lname = result.getString("lname");
                fname = result.getString("fname");
                loginId = result.getInt("loginId");
                positionId = result.getInt("positionId");
                position = result.getString("nameposition");
            }
            if(fname.equals("") || lname.equals("") || position.equals(""))
                return null;
            else
                return new Employee(employeeId,fname,lname,loginId,login,password,positionId,position);
        }catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }


    public List<Client> getAllClients(){
        List<Client> listOfClients= new ArrayList<>();
        Connection conn = getConnection();

        try{
            PreparedStatement statement = conn.prepareStatement(SQL2);
            ResultSet result = statement.executeQuery();

            while (result.next()){
                int id = result.getInt("id");
                String fname = result.getString("fname");
                String lname = result.getString("lname");
                String email = result.getString("email");
                Client client = new Client(id,fname,lname,email);
                listOfClients.add(client);
            }
            return listOfClients;
        }catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }
}
