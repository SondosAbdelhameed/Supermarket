package com.supermarket;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarFinalValueListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.supermarket.Adapters.*;
import com.supermarket.Classes.*;
import com.supermarket.Helpers.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Items extends AppCompatActivity{
    CrystalRangeSeekbar rangeSeekbar;
    TextView min , max;
    RecyclerView BrandList,AllProductList;
    BrandAdapter navAdapter;
    AllProductAdapter adapter;
    AllHelper allHelper;
    List<Brand> brands;
    List<Product> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        init();
        new FetchBrands().execute();
        final Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Intent data = getIntent();
            new FetchAllProducts().execute(data.getStringExtra("ID"));
        }
        rangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                min.setText(String.valueOf(minValue));
                max.setText(String.valueOf(maxValue));
            }
        });

        rangeSeekbar.setOnRangeSeekbarFinalValueListener(new OnRangeSeekbarFinalValueListener() {
            @Override
            public void finalValue(Number minValue, Number maxValue) {
                Log.d("CRS=>", String.valueOf(minValue) + " : " + String.valueOf(maxValue));
            }
        });
    }

    void init(){
        rangeSeekbar = (CrystalRangeSeekbar) findViewById(R.id.rangeSeekbar);
        min = (TextView) findViewById(R.id.min);
        max = (TextView) findViewById(R.id.max);
        BrandList = (RecyclerView) findViewById(R.id.brand_recycl);
        BrandList.setLayoutManager(new LinearLayoutManager(Items.this));
        allHelper = new AllHelper(Items.this);
        brands = new ArrayList<>();
        AllProductList = (RecyclerView) findViewById(R.id.item_recycl);
        AllProductList.setLayoutManager(new GridLayoutManager(Items.this,2));
        products = new ArrayList<>();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public class FetchBrands extends AsyncTask<Void,Void,List<Brand>> {

        @Override
        protected List<Brand> doInBackground(Void... voids) {
            try {
                String url = "http://192.168.1.10/Supermarket/APIs/getBrands.php";
                String response = allHelper.getJSON(url);
                if (response != null) {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonmovies = jsonArray.getJSONObject(i);
                        String id = jsonmovies.getString("ID");
                        String name = jsonmovies.getString("BrandName");
                        Brand brand = new Brand(id, name);
                        Log.d("brand",brand.BrandName);
                        brands.add(brand);}}
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("brand",e.toString());
            }
            return brands;
        }

        @Override
        protected void onPostExecute(List<Brand> brands) {
            navAdapter = new BrandAdapter(Items.this, brands);
            BrandList.setAdapter(navAdapter);

        }
    }

    public class FetchAllProducts extends AsyncTask<String,Void,List<Product>>{
        @Override
        protected List<Product> doInBackground(final String... strings) {

            String url = "http://192.168.1.10/Supermarket/APIs/getItems.php"; //in work
            //String url = "http://192.168.1.5/Roaya/APIs/setOrder.php"; //in home

            StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("testjson", response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("error", error.toString());
                        }
                    }){
                @Override
                protected Map<String, String> getParams(){
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("SCID",strings[0]);
                    return params;
                }

            };

            RequestQueue requestQueue = Volley.newRequestQueue(Items.this);
            requestQueue.add(stringRequest);



            return products;
        }

        @Override
        protected void onPostExecute(List<Product> products) {
            adapter = new AllProductAdapter(Items.this, products);
            AllProductList.setAdapter(adapter);
        }
    }
}
