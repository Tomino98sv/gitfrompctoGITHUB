package database;

import account.Account;
import com.sun.org.apache.regexp.internal.RE;
import persons.*;
import sample.Globals;

import java.sql.*;
import java.util.ArrayList;

public class Database {
    private static final String SQL1 = "SELECT employee.id as employeeId, employee.lname,employee.fname,loginemp.id as loginId,loginemp.login,loginemp.password, positions.id as positionId, positions.name as nameposition from employee inner join loginemp on employee.id=loginemp.id inner join positions on employee.position=positions.id where loginemp.login like ? and loginemp.password like ?;";
    private static final String SQL2 = "SELECT * from client group by client.id";
    private static final String SQL3 = "SELECT * from account where account.idc like ?";
    private static final String SQL4 = "UPDATE account set account.amount = account.amount + ? where id like ?";
    private static final String SQL5 = "INSERT into client(fname,lname,email) values (?,?,?)";
    private static final String SQL6 = "INSERT into account(AccNum,amount,idc) values (?,?,?)";
    private static final String SQL7 = "SELECT * from account where AccNum like ?";
    private static final String SQL8 = "SELECT * from card where ida like ?";
    private static final String SQL9 = "INSERT into card (PIN,active,expireM,expireY,ida) values(?,?,?,?,?)";
    private static final String SQL10 = "SELECT * from card where ida like ?";
    private static final String SQL11 = "SELECT * from client where id like ?";
    private static final String SQL12 = "INSERT into loginClient (login,password,idc) values (?,?,?)";
    private static final String SQL13 = "SELECT * from loginClient where id like ?";
    private static final String SQL14 = "SELECT * from loginClient where login like ?";
    private static final String SQL15 = "UPDATE card set PIN=? where id like ?";
    private static final String SQL16 = "SELECT * from loginClient where idc like ?";
    private static final String SQL17 = "UPDATE loginClient set password=? where id like ?";
    private static final String SQL18 = "SELECT count(*) from loginhistory where success=false and idl like ?";
    private static final String SQL19 = "DELETE from loginhistory where idl like ?";

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

                ClientAccount account = new ClientAccount(idAcc,accNum,amount,idC,getAllCards(idAcc));
                lisOfAccounts.add(account);
            }

            return lisOfAccounts;
        }catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<Card> getAllCards(int ida){
        ArrayList<Card> listOfCards = new ArrayList<>();

        try {
            PreparedStatement statement = conn.prepareStatement(SQL8);
            statement.setInt(1,ida);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String pin = resultSet.getString("PIN");
                boolean active = resultSet.getBoolean("active");
                int expireM = resultSet.getInt("expireM");
                int expireY = resultSet.getInt("expireY");
                int idaccount = resultSet.getInt("ida");
                listOfCards.add(new Card(id,pin,active,expireM,expireY,idaccount));
            }
            return listOfCards;
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

    public Client insertNewClient(String fname,String lname,String email){
        Client newClientToGo=null;
        try {
            PreparedStatement statement = conn.prepareStatement(SQL5,Statement.RETURN_GENERATED_KEYS);
            statement.setString(1,fname);
            statement.setString(2,lname);
            statement.setString(3,email);
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            PreparedStatement newClient = conn.prepareStatement(SQL11);

            int idclient=0;
            while (resultSet.next()){
                idclient = resultSet.getInt(1);
            }
            newClient.setInt(1,idclient);

            ResultSet newClientResult = newClient.executeQuery();
            while (newClientResult.next()){
                int id=        newClientResult.getInt("id");
                String firstname=  newClientResult.getString("fname");
                String lastname=  newClientResult.getString("lname");
                String emailed=  newClientResult.getString("email");
                newClientToGo = new Client(id,firstname,lastname,emailed,null);
            }
            return newClientToGo;
        }catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    public LoginClient insertNewLogin(String login,String password, int idc){
        LoginClient loginClient=null;

        try {
            PreparedStatement statement = conn.prepareStatement(SQL12,Statement.RETURN_GENERATED_KEYS);
            statement.setString(1,login);
            statement.setString(2,password);
            statement.setInt(3,idc);
            statement.executeUpdate();
            ResultSet newLoginClient = statement.getGeneratedKeys();
            PreparedStatement newLogin = conn.prepareStatement(SQL13);

            int idLogin = 0;
            while (newLoginClient.next()){
                idLogin = newLoginClient.getInt(1);
            }
            newLogin.setInt(1,idLogin);
            ResultSet resultSet = newLogin.executeQuery();
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String loginStr = resultSet.getString("login");
                String passStr = resultSet.getString("password");
                int idcl = resultSet.getInt("idc");
                loginClient = new LoginClient(id,loginStr,passStr,idcl);
            }
            return loginClient;
        }catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    public ClientAccount insertNewAccount(String accNum, double startingAmount, int idc){
        int id=0;
        String AccNum="";
        double amount=0;
        int idcl=0;
        try {
            PreparedStatement statement = conn.prepareStatement(SQL6);
            statement.setString(1,accNum);
            statement.setDouble(2,startingAmount);
            statement.setDouble(3,idc);
            statement.executeUpdate();
            statement = conn.prepareStatement(SQL3);
            statement.setInt(1,idc);
            ResultSet createdAccount = statement.executeQuery();
            while (createdAccount.next()){
                id = createdAccount.getInt("id");
                AccNum = createdAccount.getString("AccNum");
                amount = createdAccount.getDouble("amount");
                idcl = createdAccount.getInt("idc");
            }
            return new ClientAccount(id,AccNum,amount,idcl,null);
        }catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    public Card insertNewCard(String pin, boolean active, int expireM, int expireY, int ida){
        int id = 0;
        String PIN = "";
        boolean activ = true;
        int m = 0;
        int y = 0;
        int idA = 0;
        try {
            PreparedStatement statement = conn.prepareStatement(SQL9);
            statement.setString(1,pin);
            statement.setBoolean(2,active);
            statement.setInt(3,expireM);
            statement.setInt(4,expireY);
            statement.setInt(5,ida);
            statement.executeUpdate();
            statement = conn.prepareStatement(SQL10);
            statement.setInt(1,ida);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                id = resultSet.getInt("id");
                PIN = resultSet.getString("PIN");
                activ = resultSet.getBoolean("active");
                m = resultSet.getInt("expireM");
                y = resultSet.getInt("expireY");
                idA = resultSet.getInt("ida");
            }
            return new Card(id,PIN,activ,m,y,idA);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public LoginClient getLoginClient(int idc){
        LoginClient loginClient=null;
        try {
            PreparedStatement statement = conn.prepareStatement(SQL16);
            statement.setInt(1,idc);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                int id=resultSet.getInt(1);
                String login=resultSet.getString(2);
                String passw=resultSet.getString(3);
                int idcl=resultSet.getInt(4);
                loginClient = new LoginClient(id,login,passw,idcl);
            }
            return loginClient;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public void changePIN(int idCard, String newPIN){
        try{
            PreparedStatement statement = conn.prepareStatement(SQL15);
            statement.setString(1,newPIN);
            statement.setInt(2,idCard);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void changePasswordLogin(String newPassword,int id){
        try {
            PreparedStatement statement = conn.prepareStatement(SQL17);
            statement.setString(1,newPassword);
            statement.setInt(2,id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isAccountNumberNotAlreadyUsed(String accNumb){
        try {
            PreparedStatement statement = conn.prepareStatement(SQL7);
            statement.setString(1,accNumb);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                return false;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }

    public boolean isLoginClientNameNotAlreadyUsed(String login){
        try {
            PreparedStatement statement = conn.prepareStatement(SQL14);
            statement.setString(1,login);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public int failedLoginCount(int idLogin){
        try {
            int count=0;
            PreparedStatement statement = conn.prepareStatement(SQL18);
            statement.setInt(1,idLogin);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                count=resultSet.getInt(1);
            }
            return count;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    public void resetFailedLogin(int idLogin){
        try {
            PreparedStatement statement = conn.prepareStatement(SQL19);
            statement.setInt(1,idLogin);
            statement.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}
