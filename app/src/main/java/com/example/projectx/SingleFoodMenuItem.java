package com.example.projectx;

import java.util.HashMap;
import java.util.Map;

public class SingleFoodMenuItem {

    public HashMap menucard;

    public  SingleFoodMenuItem(HashMap value) { this.menucard=new HashMap();  this.menucard=value; }
    public  int getSize(){return  this.menucard.size();}
    public SingleFoodMenuItem() {
        menucard=new HashMap();
    }
    public void addSingleItem(Object A, Object B){
         this.menucard.put((String)A,(String)B);
    }
    public Map<String, Object> getMenucard() {
        return this.menucard;
    }

    public String toString(){
        return this.menucard.toString();
    }
    public String getName(){

        return  menucard.get("Food").toString().concat("|").concat( menucard.get("Price").toString() );
    }
    public String getCat(){ return  menucard.get("Category").toString();}
    public String getUID(){ return  menucard.get("postUID").toString();}




}
