package main;

import database.Database;
import exception.BillException;
import interfaces.DraftInterface;
import interfaces.Pce;
import items.Item;
import items.food.Fruit;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
            try {
                finalPriceOfBill = Math.round((getFinalPrice()*Internet.getUSDrate())*100.0)/100.0;
            } catch (BillException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Database db = Database.getInstanceDB();
            XML xml = XML.getInstanceXML();
            try {
                db.insertNewBill(this);
                xml.CreateXML(this);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (TransformerException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
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
