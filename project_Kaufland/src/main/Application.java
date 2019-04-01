package main;

import exception.BillException;
import items.Category;
import items.Goods;
import items.Item;
import items.drinks.Bottle;
import items.drinks.Draft;
import items.food.Fruit;
import items.food.Pastry;

public class Application {
    private static Application app = new Application();
    private Application() {
    }

    public static Application getApp(){
        return app;
    }

    public void example() throws BillException {
        Bill bill = new Bill();

        Bottle milk = new Bottle("milk 1,5 %",0.56,4);
        bill.addItem(milk);

        Item pizza = new Pastry("Penne",2.10,280,8);
        bill.addItem(pizza);

        Fruit apple = new Fruit("Orange",0.75,0.560);
        bill.addItem(apple);

        Goods pencil = new Goods("Pencil 0.5",0.60,1, Category.SCHOOL);
        bill.addItem(pencil);

        Draft vinea = new Draft("Beer",1.50,true,1.3);
        bill.addItem(vinea);

        Draft pivo = new Draft("Birell lemon",1,true,0.50);
        bill.removeItem(pivo);

        Bottle milkzabudolsom = new Bottle("milk 1,5 %",0.56,4);
        bill.addItem(milkzabudolsom);

        bill.end();
    }
}
