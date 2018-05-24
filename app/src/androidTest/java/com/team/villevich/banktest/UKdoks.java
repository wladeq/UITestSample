package com.team.villevich.banktest;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

public class UKdoks {
    static String passportNumber;
    static int [] weight ={7,3,1,7,3,1,7,3,1};
    static int [] number = new int[9];
    static int count;
    static Random rand = new Random();

    public String countPassport(){
        do {
            count = 0;
            for (int i = 0; i < number.length; i++) {
                int a = rand.nextInt(10);
                number[i] = a;
            }
            for (int b = 0; b < 9; b++) {
                int localCount = weight[b] * number[b];
                count += localCount;
            }
        } while (count % 10 != number[number.length - 1]);
        passportNumber = (Arrays.toString(number));

        //passportNumber.replaceAll("")
        return passportNumber.replaceAll(", ", "").substring(1,10);
    }

    public String counterNum(){
        Random random = new Random();
        return "RW"+random.nextInt(9)+random.nextInt(9)+random.nextInt(9)+random.nextInt(9)+
        random.nextInt(9)+ random.nextInt(9) +"C";
    }
    public String counterNum(int lenghtNum){
        Random random = new Random();
        int compareNumber;
        do{compareNumber=random.nextInt(2100000000);
        } while (compareNumber<1000000000);

        return Integer.toString(compareNumber);
    }


}
