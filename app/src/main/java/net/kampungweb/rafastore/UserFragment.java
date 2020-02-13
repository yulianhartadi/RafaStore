package net.kampungweb.rafastore;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

import io.paperdb.Paper;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment {


    public UserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        Paper.init(Objects.requireNonNull(getContext()));

        // button to user profile
        FloatingActionButton fabUserProfile = view.findViewById(R.id.fab_user_profile);
        fabUserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "setting profile", Toast.LENGTH_SHORT).show();
            }
        });

        // button to user whishlist
        FloatingActionButton fabUserWhishlist = view.findViewById(R.id.fab_user_whishlist);
        fabUserWhishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "user whishlist", Toast.LENGTH_SHORT).show();
            }
        });

        // button to user whishlist
       /* FloatingActionButton fabFavorites = view.findViewById(R.id.fab_user_reviews);
        fabFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "user reviews", Toast.LENGTH_SHORT).show();
            }
        });*/

        // button to favorites
        /*FloatingActionButton fabUserFavorites = view.findViewById(R.id.fab_user_favorites);
        fabUserFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "favorites product", Toast.LENGTH_SHORT).show();
            }
        });*/


        //user name
        //userFullName = view.findViewById(R.id.tv_user_fullname);
        /*if (userFullName != null) {
            userFullName.setText(Prevalent.currentOnlineUsers.getFullName());
        }*/

        //user message
        //FloatingActionButton fabUserMessage = view.findViewById(R.id.fab_user_message);
        /*fabUserMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"user message", Toast.LENGTH_SHORT).show();
                Objects.requireNonNull(getActivity()).finish();
            }
        });*/

        // Logout fab button
       /* FloatingActionButton fabLogout = view.findViewById(R.id.fab_logout);
        fabLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getActivity(), "Log out", Toast.LENGTH_SHORT).show();
                Paper.book().destroy();

                //alternative jump activity
                Intent logoutIntent = new Intent(getContext(), LoginActivity.class);
                logoutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(logoutIntent);

                //close app
                Objects.requireNonNull(getActivity()).moveTaskToBack(true);
                getActivity().finish();

            }
        });*/

        return view;
    }

}
