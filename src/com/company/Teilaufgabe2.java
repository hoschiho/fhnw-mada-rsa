package com.company;


import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Teilaufgabe2 {

    //This class reads in the public key (pk.txt) and a text file. Each character from the text file will be encrypted
    //and the full text is saved as chiffre.txt

    public static void main(String[] args) throws IOException {


        //Teilaufgabe 2 (a)

        //Path to pk.txt file
        Path pathPK = Paths.get("/Users/pascal/Google Drive/01_FHNW/iCompetence/02_Semester 2/01_Mada/01_RSA Implementierung/pk.txt");

        //read pk.txt to string
        String pkfile = Files.readString(pathPK, StandardCharsets.US_ASCII);

        //remove parenthesis and split pk to get n and e
        String[] ne = pkfile.substring(1, pkfile.length() - 1).split(",");

        //save n & e as BigInteger
        BigInteger n = new BigInteger(ne[0]);
        BigInteger e = new BigInteger(ne[1]);

        System.out.println("n is: " + n);
        System.out.println("e is: " + e);


        //Teilaufgabe 2 (b)
        //path to text file
        Path pathText = Paths.get("/Users/pascal/Google Drive/01_FHNW/iCompetence/02_Semester 2/01_Mada/01_RSA Implementierung/text.txt");

        //read file into string
        String textTXT = Files.readString(pathText, StandardCharsets.US_ASCII);
        System.out.println("clear text is " + textTXT);

        //convert text into charArray
        char[] letters = textTXT.toCharArray();


        //create an bigInteger Array and save chars as ascii in it
        BigInteger[] xArray = new BigInteger[letters.length];
        for (int i = 0; i < xArray.length; i++) {
            xArray[i] = BigInteger.valueOf(letters[i]);
        }

        //Teilaufgabe 2 (c)

        //encrypt text with RSA (using square and multiply)
        String chiffre = "";
        for (int i = 0; i < xArray.length; i++) {
            BigInteger bigInteger = xArray[i];
            BigInteger h = SquareAndMultiply(bigInteger, e, n);
            chiffre += h;
            if (i < xArray.length - 1) chiffre += ",";

        }
        System.out.println("encrypted text is: " + chiffre);

        //Teilaufgabe 2 (d)

        //Save chiffre in chiffre.txt file.
        try (PrintWriter out = new PrintWriter("chiffre.txt")) {
            out.print(chiffre);
            System.out.println("encrypted text saved as chiffre.txt");
        }


    }

    //Implemented as described in script (1.32)
    public static BigInteger SquareAndMultiply(BigInteger x, BigInteger e, BigInteger m) {
        BigInteger one = BigInteger.valueOf(1);

        //Initialisierung
        int i = e.bitLength();
        BigInteger h = one;
        BigInteger k = x;

        //iteriertes Quadrieren
        while (i >= 0) {
            if (e.testBit(e.bitLength() - i)) {
                h = h.multiply(k).mod(m);
            }
            k = k.pow(2).mod(m);
            i--;
        }

        return h;
    }

}