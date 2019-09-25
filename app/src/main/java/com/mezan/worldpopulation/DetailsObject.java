package com.mezan.worldpopulation;

public class DetailsObject {
    private String keys,vals;


    public DetailsObject(){

    }
    public DetailsObject(String keys,String vals){
        this.keys = keys;
        this.vals = vals;

    }

    public String getKeys() {
        return keys;
    }

    public String getVals() {
        return vals;
    }

    public void setKeys(String keys) {
        this.keys = keys;
    }

    public void setVals(String vals) {
        this.vals = vals;
    }

}
