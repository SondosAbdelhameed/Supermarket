package com.supermarket;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.supermarket.Adapters.*;
import com.supermarket.Classes.*;
import com.supermarket.Helpers.*;
import com.supermarket.Navigation.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class Home extends AppCompatActivity{

    private static ViewPager mPager;
    private static int currentPage = 0;
    private static final Integer[] XMEN= {R.drawable.slider1,R.drawable.slider2,R.drawable.slider3};
    private ArrayList<Integer> XMENArray = new ArrayList<Integer>();
    SaveLogin saveLogin;
    TextView changePass,updateProfile,logout,login,nav_name,nav_mail;
    SharedPreferences userSP;
    List<Category> categories = new ArrayList<>();
    RecyclerView CategoryList,PopularList;
    AllHelper allHelper;
    CategoryAdapter adapter;
    List<SubCategory> subCategories = new ArrayList<>();
    ExpandableListView NavCategoryList;
    NavCategoryAdapter navAdapter;
    List<Product> products = new ArrayList<>();
    PopularAdapter popAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        Slider();
        init();
        if(saveLogin.isLoged()){
            loged();
        }
        new FetchSubCategories().execute();
        new FetchCategories().execute();
        new FetchPopular().execute();
    }

    void init(){
        saveLogin = new SaveLogin(Home.this);
        changePass = (TextView) findViewById(R.id.changePass);
        updateProfile = (TextView) findViewById(R.id.updateProfile);
        logout = (TextView) findViewById(R.id.logout);
        login = (TextView) findViewById(R.id.login);
        nav_name = (TextView) findViewById(R.id.nav_name);
        nav_mail = (TextView) findViewById(R.id.nav_mail);
        userSP = getSharedPreferences("user", MODE_PRIVATE);
        CategoryList = (RecyclerView) findViewById(R.id.cat_recycl);
        CategoryList.setLayoutManager(new LinearLayoutManager(Home.this,LinearLayoutManager.HORIZONTAL,true));
        PopularList = (RecyclerView) findViewById(R.id.pop_recycl);
        PopularList.setLayoutManager(new LinearLayoutManager(Home.this,LinearLayoutManager.HORIZONTAL,true));
        NavCategoryList = (ExpandableListView) findViewById(R.id.nav_cat_expand);
        allHelper = new AllHelper(Home.this);
    }

    void loged(){
        changePass.setVisibility(View.VISIBLE);
        updateProfile.setVisibility(View.VISIBLE);
        logout.setVisibility(View.VISIBLE);
        login.setVisibility(View.GONE);
        nav_name.setText(userSP.getString("Name",""));
        nav_mail.setText(userSP.getString("Mail",""));
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home, menu);
        MenuItem item = menu.findItem(R.id.cart);
        item.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this,Order.class));
            }
        });
        return true;
    }

    private void Slider() {
        for(int i=0;i<XMEN.length;i++)
            XMENArray.add(XMEN[i]);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new SlideAdapter(Home.this,XMENArray));
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(mPager);

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == XMEN.length) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 2500, 2500);
    }

    public void Login(View view) {
        startActivity(new Intent(Home.this, Login.class));
    }

    public void ChangePassword(View view) {
        startActivity(new Intent(Home.this, ChangePassword.class));
    }

    public void UpdateProfile(View view) {
        startActivity(new Intent(Home.this, UpdateProfile.class));}

    public void Logout(View view) {
        SaveLogin saveLogin = new SaveLogin(Home.this);
        saveLogin.logout();
        startActivity(new Intent(Home.this,Home.class));
    }

    public void AboutUs(View view) {
        startActivity(new Intent(Home.this, AboutUs.class));
    }

    public void ContactUS(View view) {
        startActivity(new Intent(Home.this, ContactUs.class));
    }

    public void Share(View view) {
        Intent intent;
        ApplicationInfo app = getApplicationContext().getApplicationInfo();
        String filePath = app.sourceDir;
        intent = new Intent(Intent.ACTION_SEND);
        // MIME of .apk is "application/vnd.android.package-archive".
        // but Bluetooth does not accept this. Let's use "*/*" instead.
        intent.setType("*/*");
        // Append file and send Intent
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(filePath)));
        startActivity(Intent.createChooser(intent, "Share app via"));
    }

    public List<SubCategory> Customization(String catID , List<SubCategory> subCategoryList){
        List<SubCategory> subCatList = new ArrayList<>();
        for (SubCategory list: subCategoryList) {

            if (list.CategoryID.equals(catID))
                subCatList.add(list);
        }
        return subCatList;
    }

    public class FetchCategories extends AsyncTask<Void,Void,List<Category>>{

        @Override
        protected List<Category> doInBackground(Void... voids) {
                try {
                    String url = "http://192.168.1.10/Supermarket/APIs/getCategories.php";
                    String response = allHelper.getJSON(url);
                    if (response != null) {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonmovies = jsonArray.getJSONObject(i);
                            String id = jsonmovies.getString("ID");
                            String name = jsonmovies.getString("CategoryName");
                            Category category = new Category(id, name,Customization(id,subCategories));
                            categories.add(category);}}
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            return categories;
        }

        @Override
        protected void onPostExecute(List<Category> categories) {
            adapter = new CategoryAdapter(Home.this, categories);
            CategoryList.setAdapter(adapter);
            navAdapter = new NavCategoryAdapter(Home.this,categories);
            NavCategoryList.setAdapter(navAdapter);
        }
    }

    public class FetchSubCategories extends AsyncTask<Void,Void,List<SubCategory>>{
        @Override
        protected List<SubCategory> doInBackground(Void... voids) {
            try {
                String url = "http://192.168.1.10/Supermarket/APIs/getSubCategories.php";
                String response = allHelper.getJSON(url);
                if (response != null) {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonmovies = jsonArray.getJSONObject(i);
                        String id = jsonmovies.getString("ID");
                        String name = jsonmovies.getString("SubCategoryName");
                        String catID = jsonmovies.getString("CategoryID");
                        SubCategory subCategory = new SubCategory(id, name,catID);
                        subCategories.add(subCategory);}}
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return subCategories;

        }
    }

    public class FetchPopular extends AsyncTask<Void,Void,List<Product>>{

        @Override
        protected List<Product> doInBackground(Void... voids) {
            try {
                String url = "http://192.168.1.10/Supermarket/APIs/getPopular.php";
                String response = allHelper.getJSON(url);
                if (response != null) {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonmovies = jsonArray.getJSONObject(i);
                        String id = jsonmovies.getString("ID");
                        String ProductName = jsonmovies.getString("ProductName");
                        String Size = jsonmovies.getString("Size");
                        String Price = jsonmovies.getString("Price");
                        String Image = jsonmovies.getString("Image");
                        String Details = jsonmovies.getString("Details");
                        String SubCategoryID = jsonmovies.getString("SubCategoryID");
                        String BrandID = jsonmovies.getString("BrandID");
                        String AVGRating = jsonmovies.getString("AVGRating");
                        Product product = new Product(id, ProductName,Size,Price,Image,Details,SubCategoryID,BrandID,AVGRating);
                        products.add(product);}}
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return products;
        }

        @Override
        protected void onPostExecute(List<Product> products) {
            popAdapter = new PopularAdapter(Home.this, products);
            PopularList.setAdapter(popAdapter);

        }

    }
}
