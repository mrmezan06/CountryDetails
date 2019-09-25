package com.mezan.worldpopulation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //ArrayList<CountryObject> countryList = new ArrayList<>();
    ListView CountryRec;
    ListAdapter adapter;
    String[] countryList;
    ConstraintLayout mainRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CountryRec = findViewById(R.id.countryList);
        mainRoot = findViewById(R.id.mainroot);

        countryList = getResources().getStringArray(R.array.countryList);


        adapter = new ListAdapter(countryList, this);
        CountryRec.setAdapter(adapter);

        CountryRec.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);

                String Name = CheckSpecial(i);
                if (!isInternetConnection()) {
                    Snackbar snackbar = Snackbar
                            .make(mainRoot, "No Internet Connection!", Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else {
                    Snackbar snackbar = Snackbar
                            .make(mainRoot, "Intent ready to go...", Snackbar.LENGTH_LONG);
                    snackbar.show();
                    intent.putExtra("name", Name);
                    startActivity(intent);
                }

            }
        });


    }

    private String CheckSpecial(int i) {

        String urlKey = countryList[i];
        if (urlKey.equals("Costa Rica")) {
            return "Costa";
        }
        if (urlKey.equals("Hong Kong")) {
            return "Hong";
        }
        if (urlKey.equals("Ivory Coast")) {
            return "Ivory";
        }
        if (urlKey.equals("New Caledonia")) {
            return "Caledonia";
        }
        if (urlKey.equals("New Zealand")) {
            return "Zealand";
        }
        if (urlKey.equals("North Korea")) {
            return "Korea";
        }
        if (urlKey.equals("Saudi Arabia")) {
            return "Arabia";
        }
        if (urlKey.equals("South Africa")) {
            return "RSA";
        }
        if (urlKey.equals("South Korea")) {
            return "KOR";
            /* Special Case Read 2nd Object & First Object North Korea */
        }
        if (urlKey.equals("South Sudan")) {
            return "South Sudan";
        }
        if (urlKey.equals("Sri Lanka")) {
            return "Lanka";
        }
        if (urlKey.equals("United Arab Emirates")) {
            return "Emirates";
        }
        if (urlKey.equals("United Kingdom")) {

            return "Kingdom";
        }
        if (urlKey.equals("United States")) {
            return "USA";
        }

        return urlKey;
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
}
