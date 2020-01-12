package net.kampungweb.rafastore;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity {

    private View searchBar;
    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Paper.init(this);

        //initContent();
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

    // Navigation
    /*private void initContent() {
        searchBar = findViewById(R.id.search_bar);
        NestedScrollView nestedContent = findViewById(R.id.nested_content);
        nestedContent.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY < scrollY) { //scroll naik
                    hideNavigation(false);
                    hideBottomMenu(false);
                }
                if (scrollY > scrollY) { //turun
                    hideNavigation(true);
                    hideBottomMenu(true);
                }
            }
        });
    }*/

    private void initBottomMenu() {
        bottomNavigation = findViewById(R.id.bottom_nav);
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment fragment;
            switch (menuItem.getItemId()) {
                case R.id.nav_home:
                    Toast.makeText(getApplicationContext(), "home clicked", Toast.LENGTH_SHORT).show();
                    fragment = new HomeFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.nav_categories:
                    Toast.makeText(getApplicationContext(), "categories clicked", Toast.LENGTH_SHORT).show();
                    fragment = new CategoriesFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.nav_search:
                    Toast.makeText(getApplicationContext(), "search clicked", Toast.LENGTH_SHORT).show();
                    fragment = new SearchFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.nav_cart:
                    Toast.makeText(getApplicationContext(), "cart clicked", Toast.LENGTH_SHORT).show();
                    fragment = new CartFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.nav_user:
                    Toast.makeText(getApplicationContext(), "user clicked", Toast.LENGTH_SHORT).show();
                    fragment = new UserFragment();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };

    /*private void initMenuDrawer() {
        final DrawerLayout drawerLayout = findViewById(R.id.nav_view);
        ImageButton btnDrawer = findViewById(R.id.btn_drawer);
        btnDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
    }*/

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
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                backToExitPressedOnce = false;
            }
        }, 2000);
    }


    // hide searchBar and bottomNavigationMenu
    boolean isNavigationHide = false;

   /* private void hideNavigation(final boolean hide) {
        if (isNavigationHide && hide || !isNavigationHide && !hide) return;
        isNavigationHide = hide;
        int moveY = hide ? (2 * bottomNavigationView.getHeight()) : 0;
        bottomNavigationView.animate().translationY(moveY).setStartDelay(100).setDuration(300).start();
    }*/

    boolean isSearchBarHide = false;

    private void hideBottomMenu(final boolean hide) {
        if (isSearchBarHide && hide || !isSearchBarHide && !hide) return;
        isSearchBarHide = hide;
        int moveY = hide ? (2 * searchBar.getHeight()) : 0;
        searchBar.animate().translationY(moveY).setStartDelay(100).setDuration(300).start();
    }
}
