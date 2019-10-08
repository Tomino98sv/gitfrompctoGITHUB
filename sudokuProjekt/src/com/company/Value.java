package com.company;

import java.util.ArrayList;

public class Value {

    private int certainNumber;
    private ArrayList<Integer> chances;

    public Value(int certainNumber){
        this.certainNumber = certainNumber;
        this.chances = new ArrayList();
    }

    public void setChances(int chance){
        chances.add(chance);
    }

    public ArrayList<Integer> getChances(){
        return chances;
    }

    public void cleanChances(){
        chances.clear();
    }

    public int getCertainNumber(){
        return certainNumber;
    }

    public void setCertainNumber(int number){
        certainNumber = number;
    }
}
