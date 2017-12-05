package com.supermarket.Helpers;

import android.app.Activity;
import android.content.SharedPreferences;

import com.supermarket.Classes.*;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by sam on 10/31/17.
 */

public class SaveLogin {
    SharedPreferences userSP;
    User user;

    public SaveLogin (Activity activity){
        userSP = activity.getSharedPreferences("user", MODE_PRIVATE);
    }

    public SaveLogin (Activity activity,User user){
        userSP = activity.getSharedPreferences("user", MODE_PRIVATE);
        this.user = user;
    }

    public void Loged(){
        userSP.edit().putString("ID",user.ID).apply();
        userSP.edit().putString("Name",user.Name).apply();
        userSP.edit().putString("Mail",user.Mail).apply();
        userSP.edit().putString("Phone",user.Phone).apply();
        userSP.edit().putString("Address",user.Address).apply();

    }

    public Boolean isLoged(){
        Boolean c = true;
        if (userSP.getAll().size()==0)
            c=false;
        return c;
    }

    public void logout (){
        SharedPreferences.Editor editor = userSP.edit();
        editor.clear();
        editor.commit();
    }
}
