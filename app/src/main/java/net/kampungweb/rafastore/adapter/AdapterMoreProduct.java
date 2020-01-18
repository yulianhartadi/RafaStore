package net.kampungweb.rafastore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import net.kampungweb.rafastore.R;
import net.kampungweb.rafastore.model.MoreProduct;
import net.kampungweb.rafastore.utils.Tools;

import java.util.ArrayList;
import java.util.List;

public class AdapterMoreProduct extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MoreProduct> items = new ArrayList<>();
    private Context ctx;
    private OnItemClickListener mOnItemClickListener;
    private OnMoreButtonClickListener onMoreButtonClickListener;

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public void setOnMoreButtonClickListener(final OnMoreButtonClickListener onMoreButtonClickListener) {
        this.onMoreButtonClickListener = onMoreButtonClickListener;
    }

    public AdapterMoreProduct(Context context, List<MoreProduct> items) {
        this.items = items;
        ctx = context;
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView title;
        public TextView price;
        public ImageButton more;
        public View lyt_parent;

        public OriginalViewHolder(View v) {
            super(v);
            image = v.findViewById(R.id.iv_image_product);
            title = v.findViewById(R.id.tv_title);
            price = v.findViewById(R.id.tv_price);
            more = v.findViewById(R.id.ib_more);
            lyt_parent = v.findViewById(R.id.lyt_parent);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_more_product, parent, false);
        viewHolder = new OriginalViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof OriginalViewHolder) {
            OriginalViewHolder viewHolder = (OriginalViewHolder) holder;

            final MoreProduct product = items.get(position);
            viewHolder.title.setText(product.title);
            viewHolder.price.setText(product.price);
            Tools.displayImageOriginal(ctx, viewHolder.image, product.image);
            viewHolder.lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //mOnItemClickListener
                }
            });

            viewHolder.more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }

    private void onMoreButtonClick(final View view, final MoreProduct p) {
        PopupMenu popupMenu = new PopupMenu(ctx, view);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                onMoreButtonClickListener.onItemClick(view, p, item);
                return true;
            }
        });
        //popupMenu.inflate(R.menu.menu_product_more);
        //popupMenu.show();
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, MoreProduct obj, int pos);
    }

    public interface OnMoreButtonClickListener {
        void onItemClick(View view, MoreProduct obj, MenuItem item);
    }

}
