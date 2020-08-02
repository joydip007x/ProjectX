package com.example.projectx;

import android.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class OrderReceiver {

    public HashMap serve;

    public OrderReceiver() {
        serve = new HashMap();
    }
    public OrderReceiver(HashMap serve) {
        this.serve = serve;
    }
    public String getName(){

        String p=null;
        ArrayList<Pair<String,Integer >>A= (ArrayList<Pair<String, Integer>>) serve.get("Foodlist");
        System.out.println("LAAL "+ " LOL "+A.toString());
        Iterator i = A.iterator();
        int cnt=0;
        while (i.hasNext()) {

            HashMap q= (HashMap)  i.next();
            System.out.println("LAAL "+q+ (++cnt));
            if(p==null) p=(q.get("first").toString());
            else p=p.concat( ",").concat(q.get("first").toString()).concat(" and...");
        }
        System.out.println("LAAL "+p);
        int len=17
        if(p.length()>len)p=p.substring(0,len-1).concat("...");
        return p;
    }

    public String Money(){

        int r=0;
        ArrayList<Pair<String,Integer >>A= (ArrayList<Pair<String, Integer>>) serve.get("Foodlist");
        Iterator i = A.iterator();
        while (i.hasNext()) {
            HashMap q= (HashMap)  i.next();
           r= r+Integer.parseInt((q.get("second").toString()));
        }

        return String.valueOf(r);
    }
    public int orderType(){

         if(this.serve.get("STATUS").toString().equals("Awaiting"))return  0;
         else  if(this.serve.get("STATUS").toString().equals("Received"))return  1;
         return  2;
    }

    public int getSize() {
        return this.serve.size();
    }
    public String titleDisplay(){

        return this.getName().concat("|").concat( this.Money()).concat("|").concat(this.serve.get("PostUID").toString());
    }


}