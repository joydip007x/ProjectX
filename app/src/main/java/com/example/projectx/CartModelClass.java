package com.example.projectx;

import java.util.HashMap;

public class CartModelClass {


    public HashMap A= new HashMap();

    public CartModelClass(HashMap A) {
        this.A=A;
    }

    public String getFoodName(){

          return  this.A.get("first").toString().split("[|]")[0];
    }
    public String getUnitPrice(){

        return this.A.get("second").toString();
    }
    public String getunit(){

        return  this.A.get("first").toString().split("[|]")[1];

    }
    public Integer getPayable() {

       //System.out.println("LAAL "+Integer.parseInt(getunit().split("x")[0])+ " X "+Integer.parseInt(getUnitPrice()));
      // System.out.println("LAAL2 "+Integer.parseInt(getunit().split("x")[0])*Integer.parseInt(getUnitPrice()));
      return Integer.parseInt(getunit().split("x")[0])*Integer.parseInt(getUnitPrice());
    }
}
