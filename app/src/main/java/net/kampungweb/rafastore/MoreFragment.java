package net.kampungweb.rafastore;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.beloo.widget.chipslayoutmanager.SpacingItemDecoration;

import net.kampungweb.rafastore.adapter.AdapterMoreProduct;
import net.kampungweb.rafastore.data.DataGenerator;
import net.kampungweb.rafastore.model.MoreProduct;
import net.kampungweb.rafastore.utils.Tools;

import java.util.List;
import java.util.Objects;


/**
 * Fragment Kelompok setiap produk yang terlaris, terbaru, dan yang semacamnya
 */
public class MoreFragment extends Fragment {

    public MoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_more, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.rv_more);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        // boolean still error
        //recyclerView.addItemDecoration(new SpacingItemDecoration(2, Tools.dpToPx(this, 8), true));
        // using this alt argument
        recyclerView.addItemDecoration(new SpacingItemDecoration(2, Tools.dpToPx(Objects.requireNonNull(getContext()), 8)));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);

        List<MoreProduct> items = DataGenerator.getShoppingProduct(getContext());

        //set data and list adapter
        AdapterMoreProduct adapterMoreProduct = new AdapterMoreProduct(getContext(), items);
        recyclerView.setAdapter(adapterMoreProduct);

        //View parent_view = view.findViewById(R.id.fm_more);

        // on item list clicked
        adapterMoreProduct.setOnItemClickListener(new AdapterMoreProduct.OnItemClickListener() {
            @Override
            public void onItemClick(View view, MoreProduct obj, int pos) {
                Toast.makeText(getActivity(), "item " + obj.title + " clicked", Toast.LENGTH_SHORT).show();
            }
        });

        adapterMoreProduct.setOnMoreButtonClickListener(new AdapterMoreProduct.OnMoreButtonClickListener() {
            @Override
            public void onItemClick(View view, MoreProduct obj, MenuItem item) {
                Toast.makeText(getActivity(), "item " + obj.title + " clicked", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

}
