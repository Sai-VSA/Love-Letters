package ui;


import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {

        try {
            new GameInterface();
        } catch (FileNotFoundException e) {
            System.out.println("Error running.");
        }
    }
}
