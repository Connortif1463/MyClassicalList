package com.company;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;
import java.lang.Integer;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws IOException {

        ArrayList<Piece> pieces = new ArrayList<Piece>(0);
        Scanner keyboard = new Scanner(System.in);
        int repeat = 0;

        System.out.println("Enter the name of the file you want to save to: (no file extension, will use .txt)");
        String filename = keyboard.nextLine();
        File tfile = new File(filename+".txt");
        File cfile = new File(filename+".csv");
        if(cfile.exists()) {
            Scanner filereader = new Scanner(cfile);
            while (filereader.hasNext()) {
                String[] id = filereader.nextLine().split(",");
                pieces.add(new Piece(Integer.parseInt(id[0]), id[1], id[2]));
            }
        }
        FileWriter tfw = new FileWriter(tfile);
        FileWriter cfw = new FileWriter(cfile);
        PrintWriter tpw = new PrintWriter(tfw);
        PrintWriter cpw = new PrintWriter(cfw);

        while(repeat == 0){
            printList(pieces);

            int option = -1;
            String optionString = "";
            System.out.println("" +
                    "\nDo you want to... " +
                    "\n(1) add a piece, " +
                    "\n(2) remove a piece, " +
                    "\n(3) print the list again, " +
                    "\n(4) suggest a random piece, " +
                    "\n(0) or exit and save?");

            try{
                optionString = keyboard.nextLine();
                if(isNumeric(optionString)){
                    option = Integer.parseInt(optionString);
                }
            }
            catch (Exception e){
                System.out.println(e);
                break;
            }
            switch (option) {
                case 0:
                    repeat = 1;
                    break;

                case 1:
                    System.out.println("\nType the name of the composer wrote the piece:");
                    String piececomposer = keyboard.nextLine();

                    System.out.println("\nType the name of the piece:");
                    String piecename = keyboard.nextLine();

                    pieces.add(new Piece(pieces.size() + 1 ,piececomposer, piecename));
                    break;

                case 2:
                    System.out.println("\nType the id of the piece you want to remove:");
                    int pieceid2 = keyboard.nextInt();
                    pieces.remove(removepiece(pieceid2 , pieces));
                    break;

                case 3:
                    repeat = 0;
                    break;

                case 4:
                    repeat = 0;
                    suggestRandomPiece(pieces);
                    break;

                default:
                    repeat = 0;
                    break;
            }
        }

        for(Piece piece: pieces){
            tpw.println(piece.getPieceid() + ". " + piece.getComposer() + ": " + piece.getPiece());
            cpw.println(piece.getPieceid()+","+piece.getComposer()+","+piece.getPiece());
        }
        tpw.close();
        cpw.close();

    }

    public static ArrayList<Piece> removepiece(int removeid, ArrayList<Piece> pieces){
        for(int i = 0; i < pieces.size(); i++){
            if(pieces.get(i).getPieceid() == removeid){
                pieces.remove(i);
                for(int n = 0; n < pieces.size(); n++){
                    pieces.get(n).pieceid = n+1;
                }
            }
        }
        return pieces;
    }

    public static void printList(ArrayList<Piece> pieces){
        Collections.sort(pieces);
        for(int n = 0; n < pieces.size(); n++){
            pieces.get(n).pieceid = n+1;
        }

        System.out.println("");
        for (Piece piece : pieces) {
            System.out.println(piece.getPieceid() + ". " + piece.getComposer() + ": " + piece.getPiece());
        }

    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static void suggestRandomPiece(ArrayList<Piece> pieces){
        int max = pieces.size();
        try{
            int choice = (int) (Math.random() * (max - 0) + 0);
            if(pieces.size() > 1){
                Piece piece = pieces.get(choice);
                System.out.println("\nYour random suggestion is...");
                TimeUnit.SECONDS.sleep(1);
                System.out.println(piece.getPieceid() + ". " + piece.getComposer() + ": " + piece.getPiece());
                TimeUnit.SECONDS.sleep(2);
            }
            else{
                System.out.println("\nNot enough choices! Add more songs.");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            }
        }
        catch(Exception e){
            System.out.println(e);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }
    }

}

/*
todo:
- make an undo function perhaps?
- use imslp api to do searches after user enters the first time
  ~ maybe make pages of results with a temp arraylist and make the results have ids too
  ~ this time use the indexes of the arraylist tho
 */