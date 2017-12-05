package com.supermarket.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.supermarket.Classes.*;
import com.supermarket.R;

import java.util.List;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.ViewHolder> {
    private static final String TAG = PopularAdapter.class.getSimpleName();
    private Context context;
    private List<Product> products;

    public PopularAdapter(Context context, List<Product> products) {
        this.context = context;
        this.products = products;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.popular, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final Product product = products.get(position);
        String url = "http://192.168.1.10/Supermarket/Images/"+product.Image;

        holder.pop_name.setText(product.ProductName);
        holder.pop_price.setText("EGP  "+product.Price);
        Picasso.with(context).load(url).into(holder.pop_image);
        holder.lPop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView pop_name,pop_price;
        ImageView pop_image;
        LinearLayout lPop;

        public ViewHolder(View itemView) {
            super(itemView);
            pop_name = (TextView) itemView.findViewById(R.id.pop_name);
            pop_price = (TextView) itemView.findViewById(R.id.pop_price);
            pop_image = (ImageView) itemView.findViewById(R.id.pop_image);
            lPop = (LinearLayout) itemView.findViewById(R.id.l_pop);
        }
    }

}