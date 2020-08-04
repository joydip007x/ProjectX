package com.example.projectx;

import java.util.HashMap;

public class CartConfirmOrderModelClass {

    HashMap cc=new HashMap();

    public CartConfirmOrderModelClass(HashMap cc) {
        this.cc = cc;
    }
    public String getName(){
       return cc.get("Ordered_by").toString();
    }
    public String getAddress(){
       return cc.get("Address").toString();
    }

    public String getNumber(){
       return cc.get("Phone").toString();
    }

    public String getPromocode(){
       return cc.get("PromoCode").toString();
    }
     public String getPromoAmount(){
       return cc.get("Promotk").toString();
    }

}
