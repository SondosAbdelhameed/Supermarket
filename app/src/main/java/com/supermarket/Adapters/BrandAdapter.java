package com.supermarket.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.supermarket.Classes.*;
import com.supermarket.R;

import java.util.List;

public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.ViewHolder> {
    private static final String TAG = BrandAdapter.class.getSimpleName();
    private Context context;
    private List<Brand> brands;

    public BrandAdapter(Context context, List<Brand> brands) {
        this.context = context;
        this.brands = brands;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_brand, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final Brand brand = brands.get(position);

        holder.oneBrand.setText(brand.BrandName);
        holder.lBrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if (holder.checked.isChecked())
                    holder.checked.setChecked(false);
                else
                    holder.checked.setChecked(true);
            }
        });
    }

    @Override
    public int getItemCount() {
        return brands.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView oneBrand;
        CheckBox checked;
        LinearLayout lBrand;

        public ViewHolder(View itemView) {
            super(itemView);
            oneBrand = (TextView) itemView.findViewById(R.id.one_brand);
            checked = (CheckBox) itemView.findViewById(R.id.checked);
            lBrand = (LinearLayout) itemView.findViewById(R.id.l_brand);
        }
    }

}