package com.company;

import java.util.ArrayList;

public class Value {

    private int certainNumber;
    private ArrayList chances;

    public Value(int certainNumber){
        this.certainNumber = certainNumber;
    }

    public void setChances(ArrayList chances){
        this.chances = chances;
    }

    public int getCertainNumber(){
        return certainNumber;
    }
}
