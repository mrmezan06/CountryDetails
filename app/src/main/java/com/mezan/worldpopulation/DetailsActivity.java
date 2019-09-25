package com.mezan.worldpopulation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class DetailsActivity extends AppCompatActivity {

    ArrayList<DetailsObject> detailsList = new ArrayList<>();

    RecyclerView detailRC;
    DetailsAdapter adapter;
    ConstraintLayout root;
    String cname;
    String SudanSP;
    FloatingActionButton btnUpdate;
    static Map<String,String> JSONFILE =  new HashMap<String, String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);




        detailRC = findViewById(R.id.detailsCountry);
        root = findViewById(R.id.root);
        btnUpdate = findViewById(R.id.Updater);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isInternetConnection()){
                    Snackbar snackbar = Snackbar
                            .make(root, "No Internet Connection!", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }else {
                    try {


                        new MyTask().execute("https://confutable-colon.000webhostapp.com/apibycountry.php?country="+cname).get();
                        Snackbar snackbar = Snackbar
                                .make(root, "Updated...", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        adapter = new DetailsAdapter(detailsList);
        RecyclerView.LayoutManager manager=new LinearLayoutManager(this);
        detailRC.setLayoutManager(manager);
        detailRC.setItemAnimator(new DefaultItemAnimator());
        detailRC.addItemDecoration(new DividerItemDecoration(DetailsActivity.this, DividerItemDecoration.VERTICAL));
        detailRC.setAdapter(adapter);


        Intent it = getIntent();
        Bundle bundle=it.getExtras();
        if(bundle != null){
            cname = bundle.getString("name");
            Log.d("NameOfCountry", cname);
        }
        SudanSP = cname;

        adapter.notifyDataSetChanged();

        if(!isInternetConnection()){
            Snackbar snackbar = Snackbar
                    .make(root, "No Internet Connection!", Snackbar.LENGTH_LONG);
            snackbar.show();
        }else {
            try {
                if(JSONFILE.get(cname) == null || JSONFILE.get(cname).isEmpty()){
                    if(SudanSP.equals("South Sudan")){
                        cname = "Sudan";
                    }
                    new MyTask().execute("https://confutable-colon.000webhostapp.com/apibycountry.php?country="+cname).get();
                }else {
                    JSONFORMAT(JSONFILE.get(cname));
                }

            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }



    }

    public boolean isInternetConnection() {

        ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        // ARE WE CONNECTED TO THE NET
        if (conMgr.getActiveNetworkInfo() != null
                && conMgr.getActiveNetworkInfo().isAvailable()
                && conMgr.getActiveNetworkInfo().isConnected()) {

            return true;

        } else {
            return false;
        }


    }
    private class MyTask extends AsyncTask<String,String,String> {


        @Override
        protected void onPostExecute(String result) {

            JSONFORMAT(result);


                if(JSONFILE.containsKey(cname)){
                    JSONFILE.remove(cname);
                    JSONFILE.put(cname,result);
                }else {
                    JSONFILE.put(cname,result);
                }

                Toast.makeText(getApplicationContext(),"Data Loaded",Toast.LENGTH_LONG).show();



            Log.d("Population JSON",result);


        }

        @Override
        protected String doInBackground(String... strings) {

            StringBuilder result=new StringBuilder();
            HttpURLConnection urlConnection=null;
            try{
                URL url=new URL(strings[0]);
                urlConnection=(HttpURLConnection)url.openConnection();
                InputStream in=new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader reader=new BufferedReader(new InputStreamReader(in));
                String line;
                while((line=reader.readLine())!=null){

                    result.append(line);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                urlConnection.disconnect();
            }

            return result.toString();
        }
    }

    private void JSONFORMAT(String populationJSON) {

        try {
            JSONArray jsonArray = new JSONArray(populationJSON);
            int i = 0;
            if(SudanSP.equals("Sudan") || cname.equals("KOR")){
                i = 1;
            }else {
                i = 0;
            }
                String Name="";
                String TLD="";
                String A3C="";
                String CC="";
                String Cap="";
                String AS="";
                String Reg="";
                String SubR="";
                String Popul="";
                String latlng="";
                String Area="";
                String TZ="";
                String border="";
                String NN="";
                String NC="";
                String Cur="";
                String Lan="";
                String TL="";
                JSONObject root = jsonArray.getJSONObject(i);
                Name = root.getString("name");


                JSONArray domArr=root.getJSONArray("topLevelDomain");
                for (int j=0;j<domArr.length();j++){
                    TLD += domArr.getString(j);
                    TLD += "\n";
                }
                A3C = root.getString("alpha3Code");
                JSONArray cellArr = root.getJSONArray("callingCodes");
                for (int k=0;k<cellArr.length();k++){
                    CC += cellArr.getString(k)+"\n";
                }
                Cap = root.getString("capital");

                JSONArray ASArr = root.getJSONArray("altSpellings");
                for (int k=0;k<ASArr.length();k++){
                    AS += ASArr.getString(k)+"\n";
                }
                Reg = root.getString("region");
                SubR = root.getString("subregion");
                Popul = root.getString("population");
                JSONArray llArr = root.getJSONArray("latlng");
                latlng = "("+llArr.getString(0)+","+llArr.getString(1)+")";
                Area = root.getString("area");
                TZ = root.getJSONArray("timezones").getString(0);

                JSONArray borArr=root.getJSONArray("borders");
                for (int l=0;l<borArr.length();l++){
                    border += borArr.getString(l)+"\n";
                }
                NN = root.getString("nativeName");
                NC = root.getString("numericCode");
                JSONArray curArr = root.getJSONArray("currencies");
                for (int m=0;m<curArr.length();m++){
                    Cur += curArr.getString(m)+"\n";
                }
                JSONArray lanArr = root.getJSONArray("languages");
                for (int n=0;n<lanArr.length();n++){
                    Lan += lanArr.getString(n)+"\n";
                }
                JSONObject trObject = root.getJSONObject("translations");
                TL = "de:"+trObject.getString("de")+"\n"+
                        "es:"+trObject.getString("es")+"\n"+
                        "fr:"+trObject.getString("fr")+"\n"+
                        "ja:"+trObject.getString("ja")+"\n"+
                        "it:"+trObject.getString("it");

                DetailsObject detailsObject = new DetailsObject("Name",Name);
                detailsList.add(detailsObject);
                detailsObject = new DetailsObject("Top Level Domain",TLD);
                detailsList.add(detailsObject);
                detailsObject = new DetailsObject("Alpha 3 Code",A3C);
                detailsList.add(detailsObject);
                detailsObject = new DetailsObject("Celling Code",CC);
                detailsList.add(detailsObject);
                detailsObject = new DetailsObject("Capital",Cap);
                detailsList.add(detailsObject);
                detailsObject = new DetailsObject("Alter Spelling",AS);
                detailsList.add(detailsObject);
                detailsObject = new DetailsObject("Region",Reg);
                detailsList.add(detailsObject);
                detailsObject = new DetailsObject("Sub Region",SubR);
                detailsList.add(detailsObject);
                detailsObject = new DetailsObject("Population",Popul);
                detailsList.add(detailsObject);
                detailsObject = new DetailsObject("Lat-Lng",latlng);
                detailsList.add(detailsObject);
                detailsObject = new DetailsObject("Area",Area);
                detailsList.add(detailsObject);
                detailsObject = new DetailsObject("Timezone",TZ);
                detailsList.add(detailsObject);
                detailsObject = new DetailsObject("Borders",border);
                detailsList.add(detailsObject);
                detailsObject = new DetailsObject("Native Name",NN);
                detailsList.add(detailsObject);
                detailsObject = new DetailsObject("Numeric Code",NC);
                detailsList.add(detailsObject);
                detailsObject = new DetailsObject("Currencies",Cur);
                detailsList.add(detailsObject);
                detailsObject = new DetailsObject("Languages",Lan);
                detailsList.add(detailsObject);
                detailsObject = new DetailsObject("Translations",TL);
                detailsList.add(detailsObject);



        } catch (JSONException e) {
            e.printStackTrace();
        }
        adapter.notifyDataSetChanged();
    }


}
