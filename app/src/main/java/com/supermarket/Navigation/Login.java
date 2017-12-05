package com.supermarket.Navigation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.supermarket.Classes.*;
import com.supermarket.Helpers.*;
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


public class Login extends AppCompatActivity {

    EditText mailT,passT;
    String mail,pass;
    SaveLogin saveLogin;
    SharedPreferences userSP;
    String Fresponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init() {
        mailT = (EditText) findViewById(R.id.mail);
        passT = (EditText) findViewById(R.id.pass);
    }

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public Boolean checkData(){
        Boolean check = true;
        if (mail.isEmpty()){
            mailT.setError("Must fill data");
            check = false;
        }

        if(pass.isEmpty()){
            passT.setError("Must fill data");
            check = false;
        }
        if(!(isValidEmail(mail))){
            mailT.setError("Text not like an email");
            check = false;
        }

        return check;
    }

    public void SignUp(View view) {
        startActivity(new Intent(Login.this,Registration.class));
    }

    void setLogin(){
        String url = "http://192.168.1.10/Supermarket/APIs/logIn.php"; //in work
        //String url = "http://192.168.1.5/Roaya/APIs/setOrder.php"; //in home

        StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);

                            Fresponse = jsonObject.getString("Response");
                            if ( Fresponse.equals("Success") ){
                                User user;
                                Toast.makeText(Login.this, "Login Success", Toast.LENGTH_LONG).show();
                                JSONObject data = new JSONObject(jsonObject.getString("data"));
                                String ID = data.getString("id");
                                String Name = data.getString("name");
                                String Phone = data.getString("phone");
                                String Address = data.getString("address");
                                user = new User(ID, Name, mail, Phone, Address);
                                saveLogin = new SaveLogin(Login.this,user);
                                saveLogin.Loged();
                                finish();
                                Intent i = getBaseContext().getPackageManager()
                                        .getLaunchIntentForPackage( getBaseContext().getPackageName() );
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                i = new Intent(Login.this, Home.class);
                                startActivity(i);

                            }
                            else
                                Toast.makeText(Login.this, "Wrong Mail or Password", Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Login.this,error.toString(),Toast.LENGTH_LONG).show();
                        Log.d("error", error.toString());
                    }
                }){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("Pass",pass);
                params.put("Mail",mail);

                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void SignIn(View view) {
        mail = mailT.getText().toString();
        pass = passT.getText().toString();
        if(checkData()) {
            setLogin();
            userSP = getSharedPreferences("user", MODE_PRIVATE);
            Toast.makeText(Login.this, userSP.getString("Name",""), Toast.LENGTH_LONG).show();
        }
    }
}
