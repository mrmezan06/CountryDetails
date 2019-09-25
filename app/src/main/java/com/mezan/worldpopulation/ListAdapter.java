package com.mezan.worldpopulation;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;



public class ListAdapter extends BaseAdapter {

    //List<CountryObject> Country = new ArrayList<>();
    String []Country = new String[250];
    Context context;
    public  ListAdapter(){

    }
    public ListAdapter(String []Country ,Context context){
        this.Country = Country;
        this.context = context;
    }

    @Override
    public int getCount() {
        return Country.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup root) {
        view= LayoutInflater.from(context).inflate(R.layout.listlayout,root,false);
        TextView Name;
        Name = view.findViewById(R.id.CNAME);
        Name.setText(Country[i]);
        return view;
    }
}

