package items.drinks;

import interfaces.Pce;

public class Bottle extends Drink implements Pce {
    private int amount;

    public Bottle(String name, double price, boolean sweet, int amount) {
        super(name,price,sweet);
        this.amount = amount;
    }

    public Bottle(String name, double price, int amount){
        this(name,price,false,amount);
    }

    public Bottle(String name, double price, boolean sweet){
        this(name,price,sweet,1);
    }

    @Override
    public int getAmount(){
        return amount;
    }

    @Override
    public void setAmount(int amountnew) {
        this.amount = amountnew;
    }

    @Override
    public double getTotalPrice() {
        return Math.round((amount*getPrice())*100.0)/100.0;
    }

    @Override
    public String getUnit() {
        return "Pcs";
    }
}
