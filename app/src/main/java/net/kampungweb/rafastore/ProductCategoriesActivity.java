package net.kampungweb.rafastore;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import net.kampungweb.rafastore.adapter.AdapterProductCategories;
import net.kampungweb.rafastore.data.DataGenerator;
import net.kampungweb.rafastore.model.ShopCategory;
import net.kampungweb.rafastore.utils.Tools;

import java.util.List;
import java.util.Objects;

import io.paperdb.Paper;

public class ProductCategoriesActivity extends AppCompatActivity {

    private View parent_view;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_categories);
        parent_view = findViewById(R.id.parent_view);

        initToolbar();
        initContent();
        initMenuBottom();
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Categories");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this);
    }

    private void initContent() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);

        List<ShopCategory> items = DataGenerator.getShoppingCategory(this);

        //set data and list adapter
        AdapterProductCategories adapterProductCategories = new AdapterProductCategories(this, items);
        recyclerView.setAdapter(adapterProductCategories);

        // on item list clicked
        adapterProductCategories.setOnItemClickListener(new AdapterProductCategories.OnItemClickListener() {
            @Override
            public void onItemClick(View view, ShopCategory obj, int position) {
                Snackbar.make(parent_view, "Item " + obj.title + " clicked", Snackbar.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cart_setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent = new Intent(ProductCategoriesActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    private void initMenuBottom() {
        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
                        Intent intent = new Intent(ProductCategoriesActivity.this, HomeActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.nav_categories:
                        Toast.makeText(getApplicationContext(), "Anda sudah berada di halaman kategori", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.nav_search:
                        Toast.makeText(getApplicationContext(), "click", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.nav_cart:
                        Toast.makeText(getApplicationContext(), "click", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.nav_user:
                        // destroy data login user
                        Paper.book().destroy();
                        moveTaskToBack(true);
                        return true;
                }
                return false;
            }
        });

    }
}
