package items.food;

import static java.lang.Math.round;

public class Fruit extends Food{
    public double weight;

    public Fruit(String name, double price, int callories, double weight) {
        super(name, price, callories);
        this.weight = weight;
    }

    public Fruit(String name, double price, double weight) {
        this(name, price, -1, weight);
    }

    public double getWeight(){
        return weight;
    }

    @Override
    public double getTotalPrice() {
        return (weight*super.getPrice()*100)/100;
    }

    @Override
    public String getUnit() {
        return "Kg";
    }
}
