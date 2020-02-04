package net.kampungweb.rafastore;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment {

    AppCompatButton btnSelesaiBelanja;

    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        //btn selesai belanja
        btnSelesaiBelanja = view.findViewById(R.id.acb_btn_selesai_belanja);
        btnSelesaiBelanja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent checkOutIntent = new Intent(getActivity(), CheckOutActivity.class);
                startActivity(checkOutIntent);
            }
        });

        return view;
    }

}
