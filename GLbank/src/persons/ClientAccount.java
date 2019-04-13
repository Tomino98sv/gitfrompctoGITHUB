package persons;

public class ClientAccount {

    private int idAcc;
    private String accNum;
    private double amount;
    private int idC;

    public ClientAccount(int idAcc, String accNum, double amount, int idC) {
        this.idAcc = idAcc;
        this.accNum = accNum;
        this.amount = amount;
        this.idC = idC;
    }

    public int getIdAcc() {
        return idAcc;
    }

    public String getAccNum() {
        return accNum;
    }

    public double getAmount() {
        return amount;
    }

    public int getIdC() {
        return idC;
    }
}
