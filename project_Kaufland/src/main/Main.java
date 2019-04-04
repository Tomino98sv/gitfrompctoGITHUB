package main;

import exception.BillException;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws BillException {

        Application app = Application.getApp();
        app.example();

        Internet kurz = new Internet();
        try {
            kurz.getUSDrate();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
