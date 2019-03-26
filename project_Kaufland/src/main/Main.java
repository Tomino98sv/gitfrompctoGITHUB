package main;

import exception.BillException;
import items.Category;
import items.Goods;
import items.Item;

public class Main {

    public static void main(String[] args) throws BillException {

        Application app = Application.getApp();
        app.example();
    }
}
