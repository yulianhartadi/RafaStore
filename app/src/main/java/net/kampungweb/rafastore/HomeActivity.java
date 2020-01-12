package net.kampungweb.rafastore;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity {


    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Paper.init(this);

        //initMenuDrawer();
        loadFragment(new HomeFragment());
        initBottomMenu();


        /*btnLogout = findViewById(R.id.btn_logout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // destroy data login user
                Paper.book().destroy();

                Intent logoutIntent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(logoutIntent);
            }
        });*/
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }



    private void initBottomMenu() {
        bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment fragment;
            switch (menuItem.getItemId()) {
                case R.id.nav_home:
                    fragment = new HomeFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.nav_categories:
                    fragment = new CategoriesFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.nav_search:
                    fragment = new SearchFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.nav_cart:
                    fragment = new CartFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.nav_user:
                    fragment = new UserFragment();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };


    //click back button twice to exit or home app and prevent to login/register activity if user already login
    boolean backToExitPressedOnce = false;

    @Override
    public void onBackPressed() {

        if (backToExitPressedOnce) {
            //super.onBackPressed();
            moveTaskToBack(true);
            return;
        }

        this.backToExitPressedOnce = true;
        Toast.makeText(this, "Klik BACK sekali lagi untuk keluar", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                backToExitPressedOnce = false;
            }
        }, 2000);
    }


}
