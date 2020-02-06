package net.kampungweb.rafastore;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mikhaellopez.circularimageview.CircularImageView;

import net.kampungweb.rafastore.prevalent.Prevalent;

import java.util.Objects;

import io.paperdb.Paper;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment {

    CircularImageView userImagePict;
    TextView userLocation;
    private TextView userFullName;
    private FloatingActionButton fabMessage;

    public UserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        //Paper.init(Objects.requireNonNull(getContext()));

        //fabButton
        fabMessage = view.findViewById(R.id.fab_message);
        FloatingActionButton fabDashboard = view.findViewById(R.id.fab_dashboard);
        FloatingActionButton fabCartStatus = view.findViewById(R.id.fab_cart_status);
        FloatingActionButton fabReview = view.findViewById(R.id.fab_review);
        FloatingActionButton fabFeed = view.findViewById(R.id.fab_feed_news);
        FloatingActionButton fabLogout = view.findViewById(R.id.fab_logout);

        fabMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "message", Toast.LENGTH_SHORT).show();
            }
        });

        // Logout fab button
        fabLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Log out", Toast.LENGTH_SHORT).show();

                //Paper.book().destroy();


                //alternative jump activity
                /*Intent logoutIntent = new Intent(getContext(), LoginActivity.class);
                logoutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(logoutIntent);*/

                //close app
                /*Objects.requireNonNull(getActivity()).moveTaskToBack(true);
                getActivity().finish();*/

            }
        });


        //user image profile pict
        //userImagePict = view.findViewById(R.id.civ_user_pict_profile);

        //user name
        //userFullName = view.findViewById(R.id.tv_user_fullname);
        /*if (userFullName != null){
            userFullName.setText(Prevalent.currentOnlineUsers.getFullName());
        }*/

        //userlocation
        //userLocation = view.findViewById(R.id.tv_user_location);
        return view;
    }

}
