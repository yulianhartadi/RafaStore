package net.kampungweb.rafastore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.kampungweb.rafastore.R;
import net.kampungweb.rafastore.model.ShopCategory;

import java.util.ArrayList;
import java.util.List;

public class AdapterProductCategories extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ShopCategory> items = new ArrayList<>();
    private Context ctx;
    private AdapterProductCategories.OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, ShopCategory obj, int position);
    }
    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public AdapterProductCategories(Context context, List<ShopCategory> items) {
        this.items = items;
        ctx = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public ImageView image_bg;
        public TextView title;
        public Button brief;
        public View lyt_parent;

        public OriginalViewHolder(View view) {
            super(view);
            image_bg = view.findViewById(R.id.image_bg);
            title = view.findViewById(R.id.title);
            brief = view.findViewById(R.id.brief);
            lyt_parent = view.findViewById(R.id.lyt_parent);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_categories, parent, false);
        viewHolder = new OriginalViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof OriginalViewHolder) {
            OriginalViewHolder originalViewHolder = (OriginalViewHolder) holder;

            ShopCategory shopCategory = items.get(position);
            originalViewHolder.title.setText(shopCategory.title);
            originalViewHolder.brief.setText(shopCategory.brief);
            originalViewHolder.image_bg.setImageResource(shopCategory.image_bg);
            originalViewHolder.lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(view, items.get(position), position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
