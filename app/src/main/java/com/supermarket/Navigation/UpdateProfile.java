package com.supermarket.Navigation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.supermarket.Classes.User;
import com.supermarket.Helpers.SaveLogin;
import com.supermarket.Home;
import com.supermarket.R;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UpdateProfile extends AppCompatActivity {

    EditText nameU,phoneU,addressU;
    String name,phone,address;
    Button update;
    SharedPreferences userSP;
    SaveLogin saveLogin;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        init();
        setData();
    }

    void setData(){
        nameU.setText(userSP.getString("Name",""));
        phoneU.setText(userSP.getString("Phone",""));
        addressU.setText(userSP.getString("Address",""));
    }

    private void init() {
        nameU = (EditText) findViewById(R.id.userNameU);
        phoneU = (EditText) findViewById(R.id.phoneU);
        addressU = (EditText) findViewById(R.id.addressU);
        update = (Button) findViewById(R.id.update);
        userSP = getSharedPreferences("user", MODE_PRIVATE);

    }

    public Boolean checkData(){
        Boolean check = true;
        if (name.isEmpty()){
            nameU.setError("Must fill data");
            check = false;
        }
        if (phone.isEmpty()){
            phoneU.setError("Must fill data");
            check = false;
        }
        if(address.isEmpty()){
            addressU.setError("Must fill data");
            check = false;
        }
        return check;
    }

    public void Update(View view) {
        name = nameU.getText().toString();
        phone = phoneU.getText().toString();
        address = addressU.getText().toString();
        if(checkData()){
            updateProfile();
        }
    }

    void updateProfile(){
        String url = "http://192.168.1.10/Supermarket/APIs/updateProfile.php"; //in work
        //String url = "http://192.168.1.5/Roaya/APIs/setOrder.php"; //in home


        StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(UpdateProfile.this, response, Toast.LENGTH_LONG).show();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String r = jsonObject.getString("Response");
                            if(r.equals("Success")) {
                                Toast.makeText(UpdateProfile.this, r, Toast.LENGTH_LONG).show();
                                user = new User(userSP.getString("ID",""), name, userSP.getString("Mail",""), phone, address);
                                saveLogin = new SaveLogin(UpdateProfile.this,user);
                                saveLogin.Loged();
                                finish();
                                startActivity(new Intent(UpdateProfile.this, Home.class));
                            }
                            else
                                Toast.makeText(UpdateProfile.this, r, Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UpdateProfile.this,error.toString(),Toast.LENGTH_LONG).show();
                        Log.d("error", error.toString());
                    }
                }){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("ID",userSP.getString("ID",""));
                params.put("Name",name);
                params.put("Phone",phone);
                params.put("Address",address);

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}
