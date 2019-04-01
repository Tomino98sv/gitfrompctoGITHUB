package items.food;

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

    public void setWeight(double weight){
        this.weight = weight;
    }

    @Override
    public double getTotalPrice() {
        return Math.round((weight*super.getPrice())*100.0)/100.0;
    }

    @Override
    public String getUnit() {
        return "Kg";
    }


}
