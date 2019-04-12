package persons;

public class Employee {
    private int employeeId;
    private String fname;
    private String lname;

    private int loginId;
    private String login;
    private String password;

    private int positionId;
    private String nameposit;

    public Employee(int employeeId, String fname, String lname, int loginId, String login, String password, int positionId, String nameposit){
        this.employeeId=employeeId;
        this.fname=fname;
        this.lname=lname;

        this.loginId=loginId;
        this.login=login;
        this.password=password;

        this.positionId=positionId;
        this.nameposit=nameposit;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getNameposit(){
        return nameposit;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public int getLoginId() {
        return loginId;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public int getPositionId() {
        return positionId;
    }
}
