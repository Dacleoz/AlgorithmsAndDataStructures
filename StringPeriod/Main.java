package com.esercizi;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class Main {


    public static int periodSmart(String s) {

        if (s.equals("")) {

            return 0;
        }

        int n = s.length();
        int[] r = new int[n];

        int z;

        r[0] = 0;
        // ciclo i prefissi da 0 a n-1(n posizioni)
        for (int i = 0; i < n - 1; i++) {
            z = r[i];

            while (s.charAt(i + 1) != s.charAt(z) && (z > 0)) {
                z = r[z - 1];

            }
            if (s.charAt(i + 1) == s.charAt(z)) {
                r[i + 1] = z + 1;    //CASO 1
            } else {
                r[i + 1] = 0; //CASO2
            }
        }
        return n - r[n - 1]; // periodo frazionario minimo
    }


    public static void main(String[] args) {

        System.out.println("Inserisci stringa: ");
        Scanner in = new Scanner(System.in);


        String s = in.nextLine();

        System.out.println("Periodo frazionario minimo: "+ periodSmart(s));


        }

}




