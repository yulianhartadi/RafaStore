package net.kampungweb.rafastore;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import net.kampungweb.rafastore.utils.Tools;

public class AdminCategoryActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private LinearLayout cvClothing, cvHerbal, cvBuku, cvSepatu, cvSport, cvAksesoris, cvHobi, cvOutdoor,
            cvJamTangan, cvTransaksi, cvPromo, cvSetting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);

        initToolbar();

        cvClothing = findViewById(R.id.cv_clothing);
        cvClothing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent categoryIntent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                categoryIntent.putExtra("Category", "Clothing");
                startActivity(categoryIntent);
            }
        });

        cvHerbal = findViewById(R.id.cv_herbal);
        cvHerbal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent categoryIntent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                categoryIntent.putExtra("Category", "Herbal");
                startActivity(categoryIntent);
            }
        });


        cvBuku = findViewById(R.id.cv_buku);
        cvBuku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent categoryIntent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                categoryIntent.putExtra("Category", "Buku");
                startActivity(categoryIntent);
            }
        });


        cvSepatu = findViewById(R.id.cv_sepatu);
        cvSepatu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent categoryIntent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                categoryIntent.putExtra("Category", "Sepatu");
                startActivity(categoryIntent);
            }
        });


        cvSport = findViewById(R.id.cv_sport);
        cvSport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent categoryIntent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                categoryIntent.putExtra("Category", "Sport");
                startActivity(categoryIntent);
            }
        });


        cvAksesoris = findViewById(R.id.cv_aksesoris);
        cvAksesoris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent categoryIntent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                categoryIntent.putExtra("Category", "Aksesoris");
                startActivity(categoryIntent);
            }
        });


        cvHobi = findViewById(R.id.cv_hobi);
        cvHobi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent categoryIntent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                categoryIntent.putExtra("Category", "Hobi");
                startActivity(categoryIntent);
            }
        });


        cvOutdoor = findViewById(R.id.cv_outdoor);
        cvOutdoor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent categoryIntent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                categoryIntent.putExtra("Category", "Outdoor");
                startActivity(categoryIntent);
            }
        });


        cvJamTangan = findViewById(R.id.cv_jam_tangan);
        cvJamTangan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent categoryIntent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                categoryIntent.putExtra("Category", "JamTangan");
                startActivity(categoryIntent);
            }
        });


        cvTransaksi = findViewById(R.id.cv_transaksi);
        cvTransaksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent categoryIntent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                categoryIntent.putExtra("Category", "Transaksi");
                startActivity(categoryIntent);
            }
        });


        cvPromo = findViewById(R.id.cv_promo);
        cvPromo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent categoryIntent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                categoryIntent.putExtra("Category", "Promo");
                startActivity(categoryIntent);
            }
        });


        cvSetting = findViewById(R.id.cv_setting);
        cvSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent categoryIntent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                categoryIntent.putExtra("Category", "Setting");
                startActivity(categoryIntent);
            }
        });


    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.indigo_500), PorterDuff.Mode.SRC_ATOP);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this, android.R.color.white);
        Tools.setSystemBarLight(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_setting, menu);
        Tools.changeMenuIconColor(menu, getResources().getColor(R.color.indigo_500));
        Tools.changeOverflowMenuIconColor(toolbar, getResources().getColor(R.color.indigo_500));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    boolean doubleBackToExitPressedOnce = false;

    //click back button twice to exit or home app
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        //Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
