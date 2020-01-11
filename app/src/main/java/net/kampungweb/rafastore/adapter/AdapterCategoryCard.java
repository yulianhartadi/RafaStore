package net.kampungweb.rafastore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.kampungweb.rafastore.R;
import net.kampungweb.rafastore.model.ShopCategory;

import java.util.ArrayList;
import java.util.List;

public class AdapterCategoryCard extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ShopCategory> shopCategoryItems = new ArrayList<>();

    private Context ctx;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, ShopCategory obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public AdapterCategoryCard (Context context, List<ShopCategory> items) {
        this.shopCategoryItems = items;
        ctx = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView title;
        public View lyt_parent;

        public OriginalViewHolder(View v) {
            super(v);
            image =  v.findViewById(R.id.image);
            title = v.findViewById(R.id.title);
            lyt_parent = v.findViewById(R.id.lyt_parent);
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shop_category_card, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof OriginalViewHolder) {
            OriginalViewHolder view = (OriginalViewHolder) holder;
            ShopCategory p = shopCategoryItems.get(position);
            view.title.setText(p.title);
            view.image.setImageDrawable(p.imageDrw);

            view.lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null)
                        mOnItemClickListener.onItemClick(view, shopCategoryItems.get(position), position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return shopCategoryItems.size();
    }
}
