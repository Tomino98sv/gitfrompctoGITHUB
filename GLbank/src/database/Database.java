package database;

import account.Account;
import com.sun.org.apache.regexp.internal.RE;
import jdk.nashorn.internal.ir.WhileNode;
import persons.*;
import sample.Globals;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.WeakHashMap;

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
    private static final String SQL12 = "INSERT into loginclient (login,password,idc) values (?,MD5(?),?)";
    private static final String SQL13 = "SELECT * from loginclient where id like ?";
    private static final String SQL14 = "SELECT * from loginclient where login like ?";
    private static final String SQL15 = "UPDATE card set PIN=? where id like ?";
    private static final String SQL16 = "SELECT * from loginclient where idc like ?";
    private static final String SQL17 = "UPDATE loginclient set password=MD5(?) where id like ?";
    private static final String SQL18 = "INSERT into loginhistory(logDate,idl) values (NOW(),?)";
    private static final String SQL19 = "INSERT into loginhistory(logDate,success,idl) values (NOW(),true,?)";
    private static final String SQL20 = "SELECT * from loginhistory where idl = ? order by UNIX_TIMESTAMP(logDate) desc limit 3";
    private static final String SQL21 = "UPDATE card set active=? where id like ?";
    private static final String SQL22 = "SELECT account.amount from account where id like ?";
    private static final String SQL23 = "SELECT * from account where AccNum like ?";
    private static final String SQL24 = "INSERT into transaction(idEmployee,transdate,recAccount,transAmount,idacc) values (?,NOW(),?,?,?)";
    private static final String SQL25 = "SELECT * from transaction where idacc = ? order by UNIX_TIMESTAMP(transdate) desc limit 1";
    private static final String SQL26 = "SELECT * from employee where id like ?";

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

    public boolean isChangeAmount(double amountToDep, int idAcc){
        System.out.println("amountToDep "+amountToDep+" "+" idAcc"+idAcc);
        try{
            int amount=0;
            PreparedStatement statement = conn.prepareStatement(SQL22);
            statement.setInt(1,idAcc);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                amount=resultSet.getInt("amount");
                System.out.println("AMOUNT: "+amount);
            }
            if (amount+amountToDep>=0){
                System.out.println("result number "+(amount+amountToDep));
                System.out.println("true");
                return true;
            }else{
                System.out.println("false");
                return false;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }


        return false;
    }

    public boolean changeAmount(double amountToDep,int idAcc){
        System.out.println("IDACC "+idAcc);
        if (isChangeAmount(amountToDep,idAcc)){
            try {
                PreparedStatement statement = conn.prepareStatement(SQL4);
                statement.setDouble(1,amountToDep);
                statement.setInt(2,idAcc);
                statement.executeUpdate();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }else{
            System.out.println("NEMOZEM UROBIT TEN VYBER");
            return false;
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
                idclient = resultSet.getInt(1);  //netreba menit na meno
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
                int id=resultSet.getInt("id");
                String login=resultSet.getString("login");
                String passw=resultSet.getString("password");
                int idcl=resultSet.getInt("idc");
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

    public void blockInternetBanking(int idl){
        try{
            PreparedStatement statement = conn.prepareStatement(SQL18);
            statement.setInt(1,idl);
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void unblockInternetBanking(int idl){
        try{
            PreparedStatement statement = conn.prepareStatement(SQL19);
            statement.setInt(1,idl);
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void blockingCard(int idc,boolean bool){
        try{
            PreparedStatement statement = conn.prepareStatement(SQL21);
            statement.setInt(2,idc);
            statement.setBoolean(1,bool);
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public boolean isIBblocked(int idl){
        int falsecount=0;
        try {
            PreparedStatement statement = conn.prepareStatement(SQL20);
            statement.setInt(1,idl);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                String bool = resultSet.getString("success");
                System.out.println(bool);
                try {
                    if (Integer.valueOf(bool)==1){
                        return false;
                    }
                }catch (NumberFormatException e){
                    System.out.println("to nic to len null sa neda premenit na cislo");
                }
                if (bool==null){
                    return true;
                }
                if (Integer.valueOf(bool)==0){
                    falsecount++;
                }
                if (falsecount==3){
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean isExistingAccount(String accNum){
        try {
            PreparedStatement statement = conn.prepareStatement(SQL23);
            statement.setString(1,accNum);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                return true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return false;
    }

    public boolean insertTransaction(int idEmp, double transAmount, String recAccount, int idAccFrom){
        try{
            if (isChangeAmount(transAmount*-1, idAccFrom)){
                PreparedStatement statement = conn.prepareStatement(SQL24);
                statement.setInt(1,idEmp);
                statement.setString(2,recAccount);
                statement.setDouble(3,transAmount);
                statement.setInt(4,idAccFrom);
                statement.executeUpdate();
                changeAmount(transAmount*-1,idAccFrom); //odcita od zasielatela
                changeAmount(transAmount,idAcc(recAccount));  //pricita k cielovemu
                return true;
            }else{
                System.out.println("not possible transaction");
                return false;
            }
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public void insertWithdrawTransactionOrDepo(int idEmp, double transAmount, String recAccount, int idAccFrom){
        try{
            PreparedStatement statement = conn.prepareStatement(SQL24);
            statement.setInt(1,idEmp);
            statement.setString(2,recAccount);
            statement.setDouble(3,transAmount);
            statement.setInt(4,idAccFrom);
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public int idAcc(String accNum){
        try {
            PreparedStatement statement = conn.prepareStatement(SQL7);
            statement.setString(1,accNum);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                int idAcc = resultSet.getInt(1);
                return idAcc;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    public LastTransactionData lastTransactionData(int currentAccountId){
        Employee employee;
        Client targetClient=null;

        try {
            PreparedStatement statement = conn.prepareStatement(SQL25);
            statement.setInt(1,currentAccountId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                int idEmp = resultSet.getInt("idEmployee");
                employee=getEmpById(idEmp);
                targetClient = getClientByAccnum(resultSet.getString("recAccount"));
                return new LastTransactionData(employee,targetClient,resultSet.getString("recAccount"),resultSet.getDate("transdate"),resultSet.getDouble("transAmount"));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public Employee getEmpById(int id){
        try {
            PreparedStatement statement = conn.prepareStatement(SQL26);
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
               int idEmp = resultSet.getInt(1);
               String lname = resultSet.getString(2);
               String fname = resultSet.getString(3);
               int positon = resultSet.getInt(4);
               String nameposition="";
               if (positon==1){
                   nameposition="common";
               }
               if (positon==2){
                   nameposition="boss";
               }
               return new Employee(idEmp,fname,lname,0,null,null,positon,nameposition);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public Client getClientByAccnum(String accnum){
        try{
            PreparedStatement statement = conn.prepareStatement(SQL7);
            statement.setString(1,accnum);
            ResultSet resultSet = statement.executeQuery();
            int idcl=0;
            while (resultSet.next()){
                idcl=resultSet.getInt("idc");
                PreparedStatement statement1 = conn.prepareStatement(SQL11);
                statement1.setInt(1,idcl);
                ResultSet resultSet1 = statement1.executeQuery();
                while (resultSet1.next()){
                    int id = resultSet1.getInt("id");
                    String fname = resultSet1.getString("fname");
                    String lname = resultSet1.getString("lname");
                    String email = resultSet1.getString("email");
                    return new Client(id,fname,lname,email,null);
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
