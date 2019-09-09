package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Numbers {


    public void generateNumbers() {
        Random rand = new Random();
        ArrayList<Integer> values =  new ArrayList<>();

        for (int a=0;a<20;a++){                         //VYGENEROVANIE CISIEL OD 1 DO 20 V NAHODNOM PORADI (NEOPAKUJU SA)
            while(true){
                int temp = rand.nextInt(20)+1;
                if (!values.contains(temp)){
                    values.add(temp);
                    break;
                }
            }
            System.out.println(values.get(a));
        }

        System.out.println("\n");                   //VYGENEROVANIE CISIEL OD 1 DO 20 V NAHODNOM PORADI (NEOPAKUJU SA)

        int[] pole = new int[20];                   //Z ARRAYLISTU SA PRESUNU DO POLA KVOLI TOMU ASI DALI PREPISOVAT NA POZICIACH
        for (int a=0;a<20;a++){
            pole[a]=values.get(a);
        }                                           //Z ARRAYLISTU SA PRESUNU DO POLA KVOLI TOMU ASI DALI PREPISOVAT NA POZICIACH

        int cycling=1;                              //PREMENNA NA RESTARTOVANIE CYKLU NA PREHADZOVANIE CISIEL

        for (cycling=1;cycling<20;cycling++){
            if (pole[cycling]+1 == pole[cycling-1] || pole[cycling]-1 == pole[cycling-1]){
                while(pole[cycling]+1 == pole[cycling-1] || pole[cycling]-1 == pole[cycling-1]){
                    int index = rand.nextInt(20);
                    if (index != cycling){
                        int temp = pole[cycling];
                        pole[cycling] = pole[index];
                        pole[index] = temp;

                    }
                }

            }

            if(cycling==19){                                        //PRED UKONCENIM CYKLU SKONTROLUJEM VSETKY CISLA CI SA NAHODOU NEPREHODILI TAK ZE VZNIKOL NOVY PAR
                for (int a=0;a<19;a++){
                    if (pole[a]+1 == pole[a+1] || pole[a]-1 == pole[a+1] || pole[a] == pole[a+1]+1 || pole[a] == pole[a+1]-1){
                        cycling=1;
                    }
                }
            }
        }

        System.out.println(" prehadzane \n");

        for (int a=0;a<20;a++){
            System.out.println(pole[a]);
        }

    }



}
