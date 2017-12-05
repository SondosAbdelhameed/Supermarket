package com.supermarket.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//import com.supermarket.Helpers.GPSTracker;
import com.supermarket.Home;
import com.supermarket.R;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


public class Registration extends AppCompatActivity {

    EditText userNameT, mailT, passT, confPassT, phoneT, addressT;
    String userName, mail, pass, confPass, phone, address;
    //ImageButton location;
    //GPSTracker gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        init();
        /*location.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(Registration.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Registration.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Registration.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                    return;
                } else {
                    // Write you code here if permission already given.
                    String locationProviders = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
                    if (locationProviders == null || locationProviders.equals("")) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                    gps = new GPSTracker(Registration.this);

                    if (gps.canGetLocation()) {
                        double latitude = gps.getLatitude();
                        double longitude = gps.getLongitude();

                        Toast.makeText(
                                getApplicationContext(),
                                "Your Location is -\nLat: " + latitude + "\nLong: "
                                        + longitude, Toast.LENGTH_LONG).show();
                    } else {
                        gps.showSettingsAlert();
                    }
                }
            }
        });*/

    }

    private void init() {
        userNameT = (EditText) findViewById(R.id.name);
        mailT = (EditText) findViewById(R.id.mailR);
        passT = (EditText) findViewById(R.id.passR);
        confPassT = (EditText) findViewById(R.id.confPass);
        phoneT = (EditText) findViewById(R.id.phone);
        addressT = (EditText) findViewById(R.id.address);
        //location = (ImageButton) findViewById(R.id.location);
        //locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
    }

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public Boolean checkData(){
        Boolean check = true;
        if (userName.isEmpty()){
            userNameT.setError("Must fill data");
            check = false;
        }
        if (mail.isEmpty()){
            mailT.setError("Must fill data");
            check = false;
        }
        if (phone.isEmpty()){
            phoneT.setError("Must fill data");
            check = false;
        }
        if(pass.isEmpty()){
            passT.setError("Must fill data");
            check = false;
        }
        if(confPass.isEmpty()){
            confPassT.setError("Must fill data");
            check = false;
        }
        if(address.isEmpty()){
            addressT.setError("Must fill data");
            check = false;
        }
        if(!(isValidEmail(mail))){
            mailT.setError("Text not like an email");
            check = false;
        }

        return check;
    }

    boolean conifarmPass(){
        Boolean c = true;
        if(!pass.equals(confPass))
            c = false;
        return c;
    }

    private void register() {
        String url = "http://192.168.1.10/Supermarket/APIs/registration.php"; //in work
        //String url = "http://192.168.1.5/Roaya/APIs/setOrder.php"; //in home

        StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Registration.this, response, Toast.LENGTH_LONG).show();
                        Log.d("testjson", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Registration.this,error.toString(),Toast.LENGTH_LONG).show();
                        Log.d("error", error.toString());
                    }
                }){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("Name",userName);
                params.put("Pass",pass);
                params.put("Mail",mail);
                params.put("Phone",phone);
                params.put("Address",address);

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void Regist(View view) {
        userName = userNameT.getText().toString();
        mail = mailT.getText().toString();
        pass = passT.getText().toString();
        confPass = confPassT.getText().toString();
        phone = phoneT.getText().toString();
        address = addressT.getText().toString();
        if (checkData()) {
            if (conifarmPass()) {
                register();
                startActivity(new Intent(Registration.this, Home.class));
            } else
                confPassT.setError("Not Right");
        }
    }
}
