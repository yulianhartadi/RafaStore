package net.kampungweb.rafastore.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.kampungweb.rafastore.Interface.ItemClickListener;
import net.kampungweb.rafastore.R;

public class AdapterItemProduct extends RecyclerView.ViewHolder implements View.OnClickListener {


    public ImageView imgMainItemProduct;
    public TextView tvProductName;
    public TextView tvProductPrice;

    public ItemClickListener listener;


    public AdapterItemProduct(@NonNull View itemView) {
        super(itemView);

        imgMainItemProduct = itemView.findViewById(R.id.iv_item_product_main);
        tvProductName = itemView.findViewById(R.id.tv_item_product_name);
        tvProductPrice = itemView.findViewById(R.id.tv_item_product_price);
    }

    public void setItemClickListener(ItemClickListener listener){

        this.listener = listener;

    }

    @Override
    public void onClick(View v) {

        listener.onClick(v, getAdapterPosition(), false);

    }
}
