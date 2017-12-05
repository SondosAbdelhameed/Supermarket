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

import com.supermarket.*;
import com.supermarket.R;


import com.android.volley.*;
import com.android.volley.toolbox.*;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class ChangePassword extends AppCompatActivity {

    EditText oldPassT,newPassT,confNewPassT;
    String oldPass,newPass,confNewPass;
    Button change;
    SharedPreferences userSP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        init();
    }

    private void changePassword() {

        String url = "http://192.168.1.10/Supermarket/APIs/changePassword.php"; //in work
        //String url = "http://192.168.1.5/Roaya/APIs/setOrder.php"; //in home


        StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(ChangePassword.this, response, Toast.LENGTH_LONG).show();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String r = jsonObject.getString("Update");
                            if(r.equals("Success")) {
                                Toast.makeText(ChangePassword.this, r, Toast.LENGTH_LONG).show();
                                finish();
                                startActivity(new Intent(ChangePassword.this,Home.class));
                            }
                            else
                                Toast.makeText(ChangePassword.this, r, Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ChangePassword.this,error.toString(),Toast.LENGTH_LONG).show();
                        Log.d("error", error.toString());
                    }
                }){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("ID",userSP.getString("ID",""));
                params.put("Pass",oldPass);
                params.put("NewPass",newPass);

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void init() {
        oldPassT = (EditText) findViewById(R.id.oldPass);
        newPassT = (EditText) findViewById(R.id.newPass);
        confNewPassT = (EditText) findViewById(R.id.confNewPass);
        change = (Button) findViewById(R.id.change);
        userSP = getSharedPreferences("user", MODE_PRIVATE);
    }

    public Boolean checkData(){
        Boolean check = true;
        if (oldPass.isEmpty()){
            oldPassT.setError("Must fill data");
        }
        if (newPass.isEmpty()){
            newPassT.setError("Must fill data");
            check = false;
        }
        if (confNewPass.isEmpty()){
            confNewPassT.setError("Must fill data");
            check = false;
        }
        return check;
    }

    boolean conifarmPass(){
        Boolean c = true;
        if(!newPass.equals(confNewPass))
            c = false;
        return c;
    }

    public void Change(View view) {
        oldPass = oldPassT.getText().toString();
        newPass = newPassT.getText().toString();
        confNewPass = confNewPassT.getText().toString();
        if(checkData()){
            if(conifarmPass()){
                changePassword();
                startActivity(new Intent(ChangePassword.this, Home.class));
            }
            else
                confNewPassT.setError("Not Right");
        }
    }
}
