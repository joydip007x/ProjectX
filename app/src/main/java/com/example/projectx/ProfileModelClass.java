package com.example.projectx;

import java.util.HashMap;

public class ProfileModelClass {

    public HashMap myprf=new HashMap();

    public ProfileModelClass(HashMap myprf) {
        this.myprf = myprf;
    }

    public ProfileModelClass() {

        myprf=new HashMap();
    }

    public  int getSize() {
        return this.myprf.size();
    }
    public void setOwnerName(String s){
          myprf.put("OwnerName",s);
    }
    public void setNumber(String s){
          myprf.put("Number",s);
    }
    public void setTime(String s){
          myprf.put("Email",s);
    }
    public void setDes(String s){
          myprf.put("des",s);
    }
    public void setAddress(String s){
          myprf.put("Address",s);
    }
    public void setDP(String s){
          myprf.put("DP",s);
    }
    public  void  setResName(String s){
         myprf.put("Resname",s);
    }
    public  String getResName(){return  myprf.get("Resname").toString();}
    public  String getDP(){return  myprf.get("DP").toString(); }
    public String getOwnerName( ){

        return   myprf.get("OwnerName").toString();
    }
    public String getNumber(){
        return myprf.get("Number").toString();
    }
    public String getTime( ){
         return myprf.get("Email").toString();
    }
    public String getDes( ){
        return   myprf.get("des").toString();
    }
    public String getAddress( ){
          return  myprf.get("Address").toString();
    }

    public HashMap getMyprf() {
        return myprf;
    }

    public void setMyprf(HashMap myprf) {
        this.myprf = myprf;
    }
}
