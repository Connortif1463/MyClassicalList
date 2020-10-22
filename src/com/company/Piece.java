package com.company;

public class Piece implements Comparable<Piece>{
    public int pieceid;
    private String composer;
    private String piece;

    public Piece(int pieceid, String composer, String piece){
        this.pieceid = pieceid;
        this.composer = composer;
        this.piece = piece;
    }

    public int getPieceid(){
        return pieceid;
    }
    public String getComposer(){
        return composer;
    }
    public String getPiece(){
        return piece;
    }

    public int compareTo(Piece comparedPiece){
        int compareEquals = composer.compareTo(comparedPiece.getComposer());
        if(compareEquals != 0){
            return compareEquals;
        }
        else{
            return piece.compareTo(comparedPiece.getPiece());
        }
    }
}
