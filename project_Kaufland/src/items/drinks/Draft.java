package items.drinks;

import interfaces.DraftInterface;

public class Draft extends Drink implements DraftInterface {
    private double volume;

    public Draft(String name, double price, boolean sweet, double volume) {
        super(name, price, sweet);
        this.volume=volume;
    }

    @Override
    public double getVolume() {
        return volume;
    }

    @Override
    public void setVolume(double volume){
        this.volume=volume;
    }

    @Override
    public double getTotalPrice() {
        return Math.round((volume*super.getPrice())*100.0)/100.0;
    }

    @Override
    public String getUnit() {
        return "l";
    }
}
