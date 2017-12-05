package com.supermarket.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.supermarket.Classes.*;
import com.supermarket.R;

import java.util.List;

/**
 * Created by sam on 12/3/17.
 */

public class NavCategoryAdapter extends BaseExpandableListAdapter {
    Context context;
    List<Category> categories;

    public NavCategoryAdapter(Context context, List<Category> categories) {
        this.context = context;
        this.categories = categories;
    }

    @Override
    public int getGroupCount() {
        return categories.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return categories.get(i).subCategories.size();
    }

    @Override
    public Object getGroup(int i) {
        return categories.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return categories.get(i).subCategories.get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       // view = inflater.inflate(R.layout.nav_one_cat,null);
        view = inflater.inflate(R.layout.nav_one_cat, viewGroup, false);
        TextView one_cat = (TextView) view.findViewById(R.id.nav_one_cat);
        String value = ((Category)getGroup(i)).CategoryName;
        one_cat.setText(value);
        Log.d("Test",value + "  header");
        Log.d("Test",view.toString() + "  header");
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.nav_one_subcat,null);

        TextView one_cat = (TextView) view.findViewById(R.id.nav_one_subcat);
        String value = ((SubCategory)getChild(i,i1)).SubCategoryName;
        one_cat.setText(value);
        Log.d("Test",value + "  child");
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
