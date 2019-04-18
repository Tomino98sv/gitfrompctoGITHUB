package persons;

import java.util.ArrayList;

public class ClientAccount {

    private int idAcc;
    private String accNum;
    private double amount;
    private int idC;
    private ArrayList<Card> listOfCards;

    public ClientAccount(int idAcc, String accNum, double amount, int idC, ArrayList<Card> listOfCards) {
        this.idAcc = idAcc;
        this.accNum = accNum;
        this.amount = amount;
        this.idC = idC;
        this.listOfCards = listOfCards;
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

    public ArrayList<Card> getListOfCards() {
        return listOfCards;
    }
}
