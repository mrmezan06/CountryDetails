package com.mezan.worldpopulation;

public class CountryObject {

    public CountryObject(){

    }
    String cName;
    public CountryObject(String cName){
        this.cName = cName;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }
}
