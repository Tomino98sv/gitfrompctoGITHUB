package database;

import persons.ClientAccount;
import persons.Client;
import persons.Employee;
import sample.Globals;

import java.sql.*;
import java.util.ArrayList;

public class Database {
    private static final String SQL1 = "select employee.id as employeeId, employee.lname,employee.fname,loginemp.id as loginId,loginemp.login,loginemp.password, positions.id as positionId, positions.name as nameposition from employee inner join loginemp on employee.id=loginemp.id inner join positions on employee.position=positions.id where loginemp.login like ? and loginemp.password like ?;";
    private static final String SQL2 = "select * from client group by client.id";
    private static final String SQL3 = "select * from account where account.idc like ?";
    private static final String SQL4 = "update account set account.amount = account.amount + ? where id like ?";
    private static final String SQL5 = "insert into client(fname,lname,email) values (?,?,?)";

    private Connection conn;
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
        conn = getConnection();
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


    public ArrayList<Client> getAllClients(){
        ArrayList<Client> listOfClients= new ArrayList<>();
        conn = getConnection();

        try{
            PreparedStatement statement = conn.prepareStatement(SQL2);
            ResultSet result = statement.executeQuery();

            while (result.next()){
                int id = result.getInt("id");
                String fname = result.getString("fname");
                String lname = result.getString("lname");
                String email = result.getString("email");
                Client client = new Client(id,fname,lname,email,getAllAccounts(id));
                listOfClients.add(client);
            }
            return listOfClients;
        }catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<ClientAccount> getAllAccounts(int idc){
        ArrayList<ClientAccount> lisOfAccounts = new ArrayList<>();
        try {
            PreparedStatement statement = conn.prepareStatement(SQL3);
            statement.setInt(1,idc);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                int idAcc = resultSet.getInt("id");
                String accNum = resultSet.getString("AccNum");
                double amount = resultSet.getDouble("amount");
                int idC = resultSet.getInt("idc");

                ClientAccount account = new ClientAccount(idAcc,accNum,amount,idC);
                lisOfAccounts.add(account);
            }

            return lisOfAccounts;
        }catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    public void changeAmount(double amountToDep,int idAcc){
        try {
            PreparedStatement statement = conn.prepareStatement(SQL4);
            statement.setDouble(1,amountToDep);
            statement.setInt(2,idAcc);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertNewClient(String fname,String lname,String email){

        try {
            PreparedStatement statement = conn.prepareStatement(SQL5);
            statement.setString(1,fname);
            statement.setString(2,lname);
            statement.setString(3,email);
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
