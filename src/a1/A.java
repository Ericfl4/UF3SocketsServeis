package a1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class A {
    static Scanner scanner = new Scanner(System.in);
    private static void printContent(URL url, String etiqueta){

        InputStream in;

        try {
            in = url.openStream();
            InputStreamReader inr = new InputStreamReader(in);
            BufferedReader buff = new BufferedReader(inr);
            String line = buff.readLine();
            while(line != null){
                if (line.contains(etiqueta)){
                    System.out.println(line);
                }
                line = buff.readLine();
            }
            System.out.println();
        } catch (IOException ex) {
            Logger.getLogger(A.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        try {

            A.printContent(new URL(args[0]), args[1]);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}