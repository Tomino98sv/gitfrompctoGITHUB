package persons;

import java.util.ArrayList;

public class Client {

    private int id;
    private String fname;
    private String lname;
    private String email;
    private ArrayList<ClientAccount> listAccount;

    public Client(int id, String fname, String lname, String email, ArrayList<ClientAccount> listAccount){
        this.id=id;
        this.fname=fname;
        this.lname=lname;
        this.email=email;
        this.listAccount=listAccount;
    }

    public int getId() {
        return id;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getEmail() {
        return email;
    }

    public ArrayList<ClientAccount> getListAccount() {
        return listAccount;
    }
}
