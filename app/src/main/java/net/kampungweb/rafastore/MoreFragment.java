package net.kampungweb.rafastore;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.beloo.widget.chipslayoutmanager.SpacingItemDecoration;
import com.google.android.material.snackbar.Snackbar;

import net.kampungweb.rafastore.adapter.AdapterMoreProduct;
import net.kampungweb.rafastore.data.DataGenerator;
import net.kampungweb.rafastore.model.MoreProduct;
import net.kampungweb.rafastore.utils.Tools;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoreFragment extends Fragment {

    private RecyclerView recyclerView;
    private AdapterMoreProduct adapterMoreProduct;

    private View parent_view;

    public MoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_more, container, false);

        recyclerView = view.findViewById(R.id.rv_more);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        //sek iki error ra yo?
        recyclerView.addItemDecoration(new SpacingItemDecoration(2, Tools.dpToPx(getContext(), 8)));

        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);

        List<MoreProduct> items = DataGenerator.getShoppingProduct(getContext());

        //set data and list adapter
        adapterMoreProduct = new AdapterMoreProduct(getContext(), items);
        recyclerView.setAdapter(adapterMoreProduct);

        // on item list clicked
        adapterMoreProduct.setOnItemClickListener(new AdapterMoreProduct.OnItemClickListener() {
            @Override
            public void onItemClick(View view, MoreProduct obj, int pos) {
                Snackbar.make(parent_view, "Item " + obj.title + " clicked", Snackbar.LENGTH_SHORT).show();
            }
        });

        adapterMoreProduct.setOnMoreButtonClickListener(new AdapterMoreProduct.OnMoreButtonClickListener() {
            @Override
            public void onItemClick(View view, MoreProduct obj, MenuItem item) {
                Snackbar.make(parent_view, obj.title + " (" + item.getTitle() + ") clicked", Snackbar.LENGTH_SHORT).show();
            }
        });

        return view;
    }

}
