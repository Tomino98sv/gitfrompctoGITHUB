package items;

import interfaces.Pce;

public class Goods extends Item implements Pce {
    private int amount;
    private Category type;

    public Goods(String name, double price, int amount, Category type) {
        super(name, price);
        this.amount=amount;
        this.type=type;
    }

    public Goods(String name, double price, Category type){
        super(name,price);
        this.type=type;
    }

    @Override
    public double getTotalPrice() {
        return Math.round((amount*getPrice())*100.0)/100.0;
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
