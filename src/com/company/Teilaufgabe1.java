package com.company;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Random;


public class Teilaufgabe1 {

//This class generates a RSA KeyPair and saves it as sk.txt and pk.txt

    public static void main(String[] args) throws FileNotFoundException {

        //gets you a primeNumber
        BigInteger p = getProbablePrime();
        BigInteger q;

        //make sure to get two different PrimeNumbers
        do {
            q = getProbablePrime();
        } while (p.equals(q));

        System.out.println("p is: " + p);
        System.out.println("q is: " + q);


        //Multiply two big primenumbers to get n
        BigInteger n = p.multiply(q);

        System.out.println("n is: " + n);


        //get Phi from n
        BigInteger PhiN = calculatePhiN(p, q);

        System.out.println("PhiN is: " + PhiN);


        //all prime numbers smaller than PhiN are possible for e.
        BigInteger e;
        do {
            e = getProbablePrime();

        } while (PhiN.compareTo(e) <= 0);

        System.out.println("e is: " + e);


        //Calculate d from e with the ExtendedEuclideanAlgorithm
        BigInteger d = ExtendedEuclideanAlgorithm(e, PhiN);

        System.out.println("d is: " + d);


        //Save private key in the format (n,d) in sk.txt file.
        try (PrintWriter out = new PrintWriter("sk.txt")) {
            out.print("(" + n + "," + d + ")");
            System.out.println("secret key (n,d) saved as sk.txt");

        }

        //Save public key in the format (n,e) in pk.txt file.
        try (PrintWriter out = new PrintWriter("pk.txt")) {
            out.print("(" + n + "," + e + ")");
            System.out.println("public key (n,e) saved as pk.txt");

        }


    }

    //Implemented as described in script (1.26)
    public static BigInteger ExtendedEuclideanAlgorithm(BigInteger e, BigInteger PhiN) {

        BigInteger one = BigInteger.valueOf(1);
        BigInteger zero = BigInteger.valueOf(0);

        BigInteger q;
        BigInteger r;
        BigInteger x0alt;
        BigInteger y0alt;

        // Initialisierte schleife

        BigInteger aStrich = PhiN;
        BigInteger bStrich = e;
        BigInteger x0 = one;
        BigInteger y0 = zero;
        BigInteger x1 = zero;
        BigInteger y1 = one;


        //Schleife

        while (!bStrich.equals(zero)) {
            q = aStrich.divide(bStrich);
            r = aStrich.mod(bStrich);
            aStrich = bStrich;
            bStrich = r;

            x0alt = x0;
            y0alt = y0;
            x0 = x1;
            y0 = y1;
            x1 = x0alt.subtract(q.multiply(x1));
            y1 = y0alt.subtract(q.multiply(y1));
        }

        if (y0.compareTo(zero) > 0) {
            return y0;
        } else {
            return y0.add(PhiN);
        }

    }

    public static BigInteger getProbablePrime() {
        //Create a BigInteger object
        BigInteger bi;

        // create an assign value to bitLength
        int bitLength = 2048;

        // create a random object
        Random rnd = new Random();

        bi = BigInteger.probablePrime(bitLength, rnd);


        return bi;
    }

    public static BigInteger calculatePhiN(BigInteger p, BigInteger q) {
        BigInteger one = BigInteger.valueOf(1);
        return p.subtract(one).multiply(q.subtract(one));
    }


}
