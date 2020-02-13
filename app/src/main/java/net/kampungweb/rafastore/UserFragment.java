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

        // button to user pesan
        FloatingActionButton fabUserMessage = view.findViewById(R.id.fab_user_message);
        fabUserMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "notif pesan", Toast.LENGTH_SHORT).show();
            }
        });

        // button to user favorite
        FloatingActionButton fabUserFavorite = view.findViewById(R.id.fab_user_favorite);
        fabUserFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "user favorit", Toast.LENGTH_SHORT).show();
            }
        });

        // button to user bookmark
        FloatingActionButton fabUserBookmark = view.findViewById(R.id.fab_user_bookmark);
        fabUserBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "user bookmark", Toast.LENGTH_SHORT).show();
            }
        });

        // button to user transaksi
        FloatingActionButton fabUserTransaksi = view.findViewById(R.id.fab_user_transaksi);
        fabUserTransaksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "user transaksi", Toast.LENGTH_SHORT).show();
            }
        });

        // button to user Balance
        FloatingActionButton fabUserBalance = view.findViewById(R.id.fab_user_balance);
        fabUserBalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "user balance", Toast.LENGTH_SHORT).show();
            }
        });

        // button to Articles
        FloatingActionButton fabUserArticles = view.findViewById(R.id.fab_articles);
        fabUserArticles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "articles", Toast.LENGTH_SHORT).show();
            }
        });

        // button to voucher
        FloatingActionButton fabUserVoucher = view.findViewById(R.id.fab_voucher);
        fabUserVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "voucher", Toast.LENGTH_SHORT).show();
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
