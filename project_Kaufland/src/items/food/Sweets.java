package items.food;

import interfaces.Pce;

public class Sweets extends Food implements Pce {
    private int amount;

    public Sweets(String name, double price, int callories, int amount) {
        super(name, price, callories);
        this.amount=amount;
    }

    public Sweets(String name, double price,int amount) {
        this(name, price, -1, amount);
    }

    @Override
    public double getTotalPrice() {
        return Math.round((amount*super.getPrice())*100.0)/100.0;
    }

    @Override
    public String getUnit() {
        return "Pcs";
    }

    @Override
    public int getAmount() {
        return amount;
    }

    @Override
    public void setAmount(int amount){
        this.amount=amount;
    }
}
