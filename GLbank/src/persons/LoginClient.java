package persons;

public class LoginClient {
    private int id;
    private String login;
    private String password;
    private int idc;

    public LoginClient(int id, String login, String password, int idc) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.idc = idc;
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public int getIdc() {
        return idc;
    }
}
