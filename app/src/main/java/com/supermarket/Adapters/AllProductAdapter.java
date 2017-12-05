package com.supermarket.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.supermarket.Classes.Product;
import com.supermarket.R;

import java.util.List;

/**
 * Created by sam on 12/5/17.
 */

public class AllProductAdapter extends RecyclerView.Adapter<AllProductAdapter.ViewHolder> {
    private static final String TAG = AllProductAdapter.class.getSimpleName();
    private Context context;
    private List<Product> products;

    public AllProductAdapter(Context context, List<Product> products) {
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

        holder.item_name.setText(product.ProductName);
        holder.item_price.setText("EGP  "+product.Price);
        holder.item_Bprice.setText("EGP  "+product.Price);
        holder.item_rate.setRating(Float.parseFloat(product.AVGRating));
        Picasso.with(context).load(url).into(holder.item_image);
        holder.to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.lItem.setOnClickListener(new View.OnClickListener() {
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
        TextView item_name,item_price,item_discount,item_Bprice;
        ImageView item_image;
        RatingBar item_rate;
        Button to_cart;
        LinearLayout lItem;

        public ViewHolder(View itemView) {
            super(itemView);
            item_name = (TextView) itemView.findViewById(R.id.item_name);
            item_price = (TextView) itemView.findViewById(R.id.item_price);
            item_discount = (TextView) itemView.findViewById(R.id.item_discount);
            item_Bprice = (TextView) itemView.findViewById(R.id.item_Bprice);
            item_image = (ImageView) itemView.findViewById(R.id.item_image);
            item_rate = (RatingBar) itemView.findViewById(R.id.item_rate);
            to_cart = (Button) itemView.findViewById(R.id.to_cart);
            lItem = (LinearLayout) itemView.findViewById(R.id.l_item);
        }
    }

}