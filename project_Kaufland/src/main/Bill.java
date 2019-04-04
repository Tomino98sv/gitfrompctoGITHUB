package main;

import database.Database;
import database.MongoDB;
import exception.BillException;
import interfaces.DraftInterface;
import interfaces.Pce;
import items.Item;
import items.food.Fruit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Bill {
    private List<Item> list;
    static int countItem;
    private double finalPriceOfBill;
    private boolean open;
    private Date date;
    private static int count=0;

    public int getId() {
        return id;
    }

    private int id;


    public Bill(){
        this.list=new ArrayList<>();
        countItem=0;
        finalPriceOfBill =0;
        open=true;
        count++;
        id=count;
    }

    public void addItem(Item item) throws BillException {
        Item itemTemp = checkItems(item);
        if (itemTemp==item)  {
            if(open == false){
                String message = "Bill is closed Is not allowed to add any item ";
                throw new BillException(message);
            }
            if (countItem==Globals.MAXITEMS) {
                String mess = "Bill is full, max is " + Globals.MAXITEMS + " items bitch";
                throw new BillException(mess);
            }else{
                list.add(item);
                countItem++;
            }
        }
    }

    public Item checkItems(Item item){
        for (Item checkingItem: list) {

            if (item.getName().toLowerCase().equals(checkingItem.getName().toLowerCase())
                    && item.getClass().getName().equals(checkingItem.getClass().getName())){
                System.out.println("PRESLA PODMIENKA");
                updateItem(item,checkingItem);
                return null;
            }
        }
        return item;
    }

    public void updateItem(Item item, Item oldItem){
        if (oldItem instanceof DraftInterface && item instanceof DraftInterface){
            ((DraftInterface) oldItem).setVolume(((DraftInterface) oldItem).getVolume()+((DraftInterface) item).getVolume());
        }else if(oldItem instanceof Fruit && item instanceof Fruit){
            ((Fruit) oldItem).setWeight(((Fruit) oldItem).getWeight()+((Fruit) item).getWeight());
        }else if(oldItem instanceof Pce && item instanceof Pce){
           ((Pce) oldItem).setAmount(((Pce) oldItem).getAmount()+((Pce) item).getAmount());
        }
    }

    public void removeItem(Item item){
        if (list.contains(item)){
            list.remove(item);
            countItem--;
        }
    }

    public double getFinalPrice()throws BillException{
        if (list==null){
            throw new BillException("Bill is empty");
        }else{
            for (Item item: list){
                finalPriceOfBill+= item.getTotalPrice();
            }
            return finalPriceOfBill;
        }
    }

    public int getCountItem(){
        return countItem;
    }

    public List<Item> getList() {
        return list;
    }

    public double getFinalPriceOfBill() {
        return finalPriceOfBill;
    }

    public Date getDate() {
        return date;
    }

    public void end(){
        if(open){
            date= new Date();
            try {
                finalPriceOfBill = Math.round((getFinalPrice()*Internet.getUSDrate())*100.0)/100.0;
            } catch (BillException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Database db = Database.getInstanceDB();
            XML xml = XML.getInstanceXML();
            MongoDB mdb = MongoDB.getInstanceMongoDB();
            try {
                db.insertNewBill(this);
                xml.CreateXML(this);
                mdb.addBillToMongoDB(this);
            } catch (Exception e){
                e.printStackTrace();
            }
//            db.insertNewBill(list, (java.util.Date) date,finalPriceOfBill);
        }else{
            System.out.println("Date: "+date);
        }
        open=false;
    }

    public void print(){
        if (countItem==0){
            System.out.println("Nothing to print Bill is empty");
        }else{
            if (open){
                System.out.println("Blocek je otvoreny");


            }else{
                System.out.println("blocek zatvoreny");
            }
            for (Item item: list){
                System.out.println("\n");
                if (item instanceof DraftInterface){
                    System.out.println("ItemName: "+item.getName()+"\nVolume: "+((DraftInterface) item).getVolume());
                    System.out.println("Price for unit: "+item.getPrice()+"\nTotal price for product: "+item.getTotalPrice());
                }else if(item instanceof Fruit){
                    System.out.println("ItemName: "+item.getName()+"\nWeight: "+((Fruit) item).weight);
                    System.out.println("Price for unit: "+item.getPrice()+"\nTotal price for product: "+item.getTotalPrice());
                }else if(item instanceof Pce){
                    System.out.println("ItemName: "+item.getName()+"\nAmount: "+((Pce) item).getAmount());
                    System.out.println("Price for unit: "+item.getPrice()+"\nTotal price for product: "+item.getTotalPrice());
                }
            }
        }

    }

}
