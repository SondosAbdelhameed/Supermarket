package com.supermarket.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.supermarket.Classes.Category;
import com.supermarket.Items;
import com.supermarket.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private static final String TAG = CategoryAdapter.class.getSimpleName();
    private Context context;
    private List<Category> categories;

    public CategoryAdapter(Context context, List<Category> categories) {
        this.context = context;
        this.categories = categories;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_cat, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final Category category = categories.get(position);
        final Bundle args =new Bundle();

        holder.oneCat.setText(category.CategoryName);
        holder.oneCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent= new Intent(v.getContext(), Items.class);
                args .putString("ID",category.ID);
                args .putString("CategoryName", category.CategoryName);
                intent.putExtras(args);
                context.startActivity(intent);

            }

        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView oneCat;

        public ViewHolder(View itemView) {
            super(itemView);
                oneCat = (TextView) itemView.findViewById(R.id.one_cat);
        }
    }

}