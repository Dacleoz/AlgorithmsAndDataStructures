package StringPeriod;

import java.util.Scanner;

public class MainPeriodNaive {

    public static int periodNaive(String s) {
        int len = s.length();
        int k;
        for (int p = 1; p < len; p++) {
            k = p;
            while (k < len && s.charAt(k) == s.charAt(k % p)) {
                k++;
            }
            if (k == len) {
                return p;
            }
        }
        return len;
    }


    public static void main(String args[]) {

        System.out.print("Inserisci stringa:");
        Scanner in = new Scanner(System.in);
        String s = in.nextLine();

        System.out.print("Periodo frazionario minimo" + periodNaive(s));


    }

}

