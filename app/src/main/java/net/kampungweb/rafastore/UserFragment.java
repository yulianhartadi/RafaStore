package net.kampungweb.rafastore;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

import io.paperdb.Paper;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment {

    FloatingActionButton fabLogout;

    public UserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_user, container, false);
        Paper.init(Objects.requireNonNull(getActivity()));
        fabLogout = view.findViewById(R.id.fab_logout);
        fabLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Log out", Toast.LENGTH_SHORT).show();
                Paper.book().destroy();
                Objects.requireNonNull(getActivity()).moveTaskToBack(true);
            }
        });

        return view;
    }

}
