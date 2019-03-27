package main;

import exception.BillException;
import items.Category;
import items.Goods;
import items.Item;
import items.drinks.Bottle;
import items.drinks.Drink;
import items.food.Fruit;
import items.food.Pastry;
import items.drinks.Draft;

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

        Item pizza = new Pastry("Salamova",1.10,280,2);
        bill.addItem(pizza);

        Fruit apple = new Fruit("Red apple",0.59,0.370);
        bill.addItem(apple);

        Goods pencil = new Goods("Pencil 0.5",0.60,1, Category.SCHOOL);
        bill.addItem(pencil);

        Draft vinea = new Draft("White Vinea",1.20,true,0.3);
        bill.addItem(vinea);

        Draft pivo = new Draft("Birell lemon",1,true,0.50);
        bill.removeItem(pivo);


        System.out.println(bill.getCountItem());
        bill.print();
        bill.end();
        bill.print();
        bill.end();
        bill.print();
    }
}
