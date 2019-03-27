package main;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import database.Database;
import exception.BillException;
import interfaces.DraftInterface;
import items.Item;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import interfaces.Pce;
import items.food.Fruit;

public class Bill {
    private List<Item> list;
    static int countItem;
    private double finalPriceOfBill;
    private boolean open;
    private Date date;

    public Bill(){
        this.list=new ArrayList<>();
        countItem=0;
        finalPriceOfBill =0;
        open=true;
    }

    public void addItem(Item item) throws BillException {
        if (item!=null)  {
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
            System.out.println("Date: "+date);
            Database db = Database.getInstanceDB();
            db.insertNewBill(this);
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