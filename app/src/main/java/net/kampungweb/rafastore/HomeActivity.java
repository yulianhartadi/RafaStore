package net.kampungweb.rafastore;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.tabs.TabLayout;

import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity {

    private TabLayout tabBottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initMenuBottom();
        initMenuDrawer();

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

    private void initMenuDrawer() {
        final DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        ImageButton btnDrawer = findViewById(R.id.btn_drawer);
        btnDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
    }

    private void initMenuBottom() {
        tabBottomNav = findViewById(R.id.tab_bottom);

        // menu icon
        tabBottomNav.addTab(tabBottomNav.newTab().setIcon(R.drawable.ic_tab_bottom_home), 0);
        tabBottomNav.addTab(tabBottomNav.newTab().setIcon(R.drawable.ic_tab_bottom_category), 1);
        tabBottomNav.addTab(tabBottomNav.newTab().setIcon(R.drawable.ic_tab_bottom_search), 2);
        tabBottomNav.addTab(tabBottomNav.newTab().setIcon(R.drawable.ic_tab_bottom_cart), 3);
        tabBottomNav.addTab(tabBottomNav.newTab().setIcon(R.drawable.ic_tab_bottom_user), 4);

        // set icon color pre-selected
        tabBottomNav.getTabAt(0).getIcon().setColorFilter(getResources().getColor(R.color.cyan_400), PorterDuff.Mode.SRC_IN);
        tabBottomNav.getTabAt(1).getIcon().setColorFilter(getResources().getColor(R.color.grey_60), PorterDuff.Mode.SRC_IN);
        tabBottomNav.getTabAt(2).getIcon().setColorFilter(getResources().getColor(R.color.grey_60), PorterDuff.Mode.SRC_IN);
        tabBottomNav.getTabAt(3).getIcon().setColorFilter(getResources().getColor(R.color.grey_60), PorterDuff.Mode.SRC_IN);
        tabBottomNav.getTabAt(4).getIcon().setColorFilter(getResources().getColor(R.color.grey_60), PorterDuff.Mode.SRC_IN);

        tabBottomNav.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(getResources().getColor(R.color.cyan_400), PorterDuff.Mode.SRC_IN);
                switch (tab.getPosition()) {
                    case 0:
                        Toast.makeText(HomeActivity.this, "Selected", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Intent productCategoriesIntent = new Intent(HomeActivity.this, ProductCategoriesActivity.class);
                        startActivity(productCategoriesIntent);
                        break;
                    case 2:

                        break;
                    case 3:

                        break;
                    case 4:
                        // destroy data login user
                        Paper.book().destroy();

                        Intent logoutIntent = new Intent(HomeActivity.this, MainActivity.class);
                        startActivity(logoutIntent);
                        break;
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(getResources().getColor(R.color.grey_60), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    boolean backToExitPressedOnce = false;

    //click back button twice to exit or home app and prevent to login/register activity if user already login
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


}
