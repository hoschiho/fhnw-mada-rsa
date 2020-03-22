package ch.hoschiho;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static ch.hoschiho.Teilaufgabe2.SquareAndMultiply;

public class Teilaufgabe3 {

    //This class lets you read in the secret key (sk.txt) and an encrypted message (chiffre.txt). The message
    //will be decrypted and saved as text-d.txt.

    public static void main(String[] args) throws IOException {

        //Path to sk.txt file
        Path pathSK = Paths.get("sk.txt");


        //read sk.txt to string
        String skfile = Files.readString(pathSK, StandardCharsets.US_ASCII);

        //remove parenthesis and split sk to get n and d
        String[] nd = skfile.substring(1, skfile.length() - 1).split(",");

        //save n & d as BigInteger
        BigInteger n = new BigInteger(nd[0]);
        BigInteger d = new BigInteger(nd[1]);

        System.out.println("n is: " + n);
        System.out.println("d is: " + d);



        //path to chiffre file
        Path pathToChiffre = Paths.get("chiffre.txt");

        //read file into string
        String chiffreTXT = Files.readString(pathToChiffre, StandardCharsets.US_ASCII);
        System.out.println("encrypted text is: " + chiffreTXT);

        //Save string to a big integer array
        String[] chiffreArray = chiffreTXT.split(",");
        BigInteger[] yArray = new BigInteger[chiffreArray.length];


        //decrypt text with RSA (using square and multiply) and save it as String.
        String textD = "";
        for (int i = 0; i < yArray.length; i++) {
            yArray[i] = new BigInteger(chiffreArray[i]);
            BigInteger h = SquareAndMultiply(yArray[i], d, n);
            char c = (char) h.intValue();
            textD += c;
        }
        System.out.println("decrypted text is: " + textD);

        //Save textD in text-d.txt file.
        try (PrintWriter out = new PrintWriter("text-d.txt")) {
            out.print(textD);
            System.out.println("decrypted text saved as text-d.txt");

        }

    }


}
