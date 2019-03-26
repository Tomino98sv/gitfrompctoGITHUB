package items.food;

import interfaces.Pce;

public class Pastry extends Food implements Pce {
    private int amount;

    public Pastry(String name, double price, int callories, int amount) {
        super(name, price, callories);
        this.amount=amount;
    }

    public Pastry(String name, double price,  int amount) {
        this(name, price, -1,amount);
    }


    @Override
    public double getTotalPrice() {
        return amount*super.getPrice();
    }

    @Override
    public int getAmount(){
        return amount;
    }
}
