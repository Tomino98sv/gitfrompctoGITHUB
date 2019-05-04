package persons;

import java.util.Date;

public class LastTransactionData {

    private Employee employee;
    private Client targetClient;
    private String targetAccount;

    private Date dateOfTrans;
    private double amountOfTrans;

    public LastTransactionData(persons.Employee employee, Client targetClient, String targetAccount, Date dateOfTrans, double amountOfTrans) {
        this.employee = employee;
        this.targetClient = targetClient;
        this.targetAccount = targetAccount;
        this.dateOfTrans = dateOfTrans;
        this.amountOfTrans = amountOfTrans;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Client getTargetClient() {
        return targetClient;
    }

    public String getTargetAccount() {
        return targetAccount;
    }

    public Date getDateOfTrans() {
        return dateOfTrans;
    }

    public double getAmountOfTrans() {
        return amountOfTrans;
    }
}
