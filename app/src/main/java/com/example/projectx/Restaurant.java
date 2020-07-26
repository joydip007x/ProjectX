package com.example.projectx;

public class Restaurant {

     public String name,cover ;
     Class Menudatabase;

    public Restaurant(String name, String cover) {
        this.name = name;
        this.cover = cover;
    }
    public Restaurant(String name, Class menudatabase) {
        this.name = name;
        Menudatabase = menudatabase;
    }
    public Restaurant(Class menudatabase) {
        Menudatabase = menudatabase;
    }
    public Restaurant(String name) {
        this.name = name;
    }
    public Restaurant() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Class getMenudatabase() {
        return Menudatabase;
    }
    public void setMenudatabase(Class menudatabase) {
        Menudatabase = menudatabase;
    }
}
