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
    public double getTotalPrice() {
        return (volume*super.getPrice()*100)/100;
    }

    @Override
    public String getUnit() {
        return "l";
    }
}
