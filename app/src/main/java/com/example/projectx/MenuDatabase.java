package com.example.projectx;

import android.util.Pair;

import java.util.ArrayList;

public class MenuDatabase  {

    public ArrayList<Pair<String, Integer>  > cards=new ArrayList<>();

    public MenuDatabase( ArrayList<Pair<String, Integer> >P ){

        cards.addAll(P);
    }
    public void AddMendatabase(String s,Integer i){
          cards.add( new Pair <String,Integer> (s, i) );
    }

    public ArrayList<Pair<String, Integer>> getCards() {
        return cards;
    }
    public void setCards(ArrayList<Pair<String, Integer>> cards) {
        this.cards = cards;
    }

}
