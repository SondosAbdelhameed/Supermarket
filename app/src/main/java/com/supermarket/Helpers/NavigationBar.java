package com.supermarket.Helpers;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.supermarket.Items;
import com.supermarket.Navigation.*;
import com.supermarket.R;
import com.mikepenz.materialdrawer.*;
import com.mikepenz.materialdrawer.model.*;
import com.mikepenz.materialdrawer.model.interfaces.*;

import java.io.File;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by sam on 10/23/17.
 */

public class NavigationBar {

    Toolbar toolbar;
    Activity activity;
    Boolean CheckLoged;
    SaveLogin saveLogin;
    ImageView profileI;
    TextView name,mail;

    public NavigationBar(Toolbar toolbar, Activity activity){
        saveLogin = new SaveLogin(activity);
        this.toolbar = toolbar;
        this.activity = activity;
        this.CheckLoged = saveLogin.isLoged();
        profileI = (ImageView) activity.findViewById(R.id.profile_image);
        name = (TextView) activity.findViewById(R.id.nav_name);
        mail = (TextView) activity.findViewById(R.id.nav_mail);
    }

    @SuppressLint("ResourceAsColor")
    public void setNavegation(){
        //Items clicked
        SecondaryDrawerItem item3 = new SecondaryDrawerItem().withIdentifier(3).withName("About us");
        SecondaryDrawerItem item4 = new SecondaryDrawerItem().withIdentifier(4).withName("Contact us");
        SecondaryDrawerItem item5 = new SecondaryDrawerItem().withIdentifier(5).withName("Share");
        SecondaryDrawerItem item6,item7,item8;
        if(!CheckLoged) {
            item6 = null;
            item7 = null;
            item8 = new SecondaryDrawerItem().withIdentifier(8).withName("Login");
        }
        else {
            item6 = new SecondaryDrawerItem().withIdentifier(6).withName("Change Password");
            item7 = new SecondaryDrawerItem().withIdentifier(7).withName("Update Account");
            item8 = new SecondaryDrawerItem().withIdentifier(8).withName("Logout");
        }

        //Item show
        SecondaryDrawerItem category = new SecondaryDrawerItem().withName("Category")
                .withEnabled(false).withDisabledTextColorRes(R.color.primary).withIcon(R.drawable.ic_user_nav);
        SecondaryDrawerItem account = new SecondaryDrawerItem().withName("Account")
                .withEnabled(false).withDisabledTextColorRes(R.color.primary).withIcon(R.drawable.ic_user_nav);
        SecondaryDrawerItem setting = new SecondaryDrawerItem().withName("Settings")
                .withEnabled(false).withDisabledTextColorRes(R.color.primary).withIcon(R.drawable.ic_settings);

        //subCat
        SecondaryDrawerItem braS1 = new SecondaryDrawerItem().withIdentifier(16).withName("All").withLevel(2);
        SecondaryDrawerItem braS2 = new SecondaryDrawerItem().withIdentifier(16).withName("Toshiba").withLevel(2);
        SecondaryDrawerItem braS3 = new SecondaryDrawerItem().withIdentifier(17).withName("Samsung").withLevel(2);
        SecondaryDrawerItem braS4 = new SecondaryDrawerItem().withIdentifier(18).withName("Dell").withLevel(2);

        //Category
        SecondaryDrawerItem cat1 = new SecondaryDrawerItem().withIdentifier(9).withName("House");
        SecondaryDrawerItem cat2 = new SecondaryDrawerItem().withIdentifier(10).withName("Mobile");
        SecondaryDrawerItem cat3 = new SecondaryDrawerItem().withIdentifier(11).withName("Game");
        ExpandableDrawerItem cat4 = new ExpandableDrawerItem().withIdentifier(12).withName("Labtop")
               .withTextColorRes(R.color.light_gray).withSubItems(braS1,braS2,braS3,braS4);
        SecondaryDrawerItem cat5 = new SecondaryDrawerItem().withIdentifier(13).withName("Closses");
        ExpandableDrawerItem cat6 = new ExpandableDrawerItem().withIdentifier(14).withName("Beauty")
                .withTextColorRes(R.color.light_gray).withSubItems(braS1,braS2,braS3,braS4);;

        SharedPreferences userSP;
        userSP = activity.getSharedPreferences("user", MODE_PRIVATE);


        if(CheckLoged) {
            name.setText(userSP.getString("Name",""));
            mail.setText(userSP.getString("Mail",""));
           }


        DrawerBuilder DB = new DrawerBuilder();
        DB.addStickyDrawerItems(account);
        if(CheckLoged){
            DB.addStickyDrawerItems(item6,item7);
        }
        DB.withActivity(activity)
               // .withHeader(R.layout.header)
                .withToolbar(toolbar)
               .addStickyDrawerItems(item8)
                //.withShowDrawerOnFirstLaunch(true)
                .addDrawerItems(
                       /* item1,item2,new DividerDrawerItem(),*/category,
                        cat1,cat2,cat3,cat4,cat5,cat6,
                        new DividerDrawerItem(),setting,item3 ,item4,item5
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // do something with the clicked item :D
                        Intent intent = null;
                        switch ((int) drawerItem.getIdentifier()) {
                            case 3:
                                intent = new Intent(activity, AboutUs.class);
                                activity.startActivity(intent);
                                break;
                            case 4:
                                intent = new Intent(activity, ContactUs.class);
                                activity.startActivity(intent);
                                break;
                            case 5:

                                ApplicationInfo app = activity.getApplicationContext().getApplicationInfo();
                                String filePath = app.sourceDir;
                                intent = new Intent(Intent.ACTION_SEND);
                                // MIME of .apk is "application/vnd.android.package-archive".
                                // but Bluetooth does not accept this. Let's use "*/*" instead.
                                intent.setType("*/*");
                                // Append file and send Intent
                                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(filePath)));
                                activity.startActivity(Intent.createChooser(intent, "Share app via"));

                                break;
                            case 6:
                                intent = new Intent(activity, ChangePassword.class);
                                activity.startActivity(intent);
                                break;
                            case 7:
                                intent = new Intent(activity, UpdateProfile.class);
                                activity.startActivity(intent);
                                break;
                            case 8:
                                if(!CheckLoged) {
                                    intent = new Intent(activity, Login.class);
                                    activity.startActivity(intent);
                                }
                                else {
                                    saveLogin.logout();
                                    intent = new Intent(activity, activity.getClass());
                                    activity.startActivity(intent);
                                }
                                break;
                            case 9:
                                intent = new Intent(activity, Items.class);
                                activity.startActivity(intent);
                        }
                        return true;
                    }
                })
                .build();

    }

}
