package net.kampungweb.rafastore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import net.kampungweb.rafastore.adapter.AdapterProductCategories;
import net.kampungweb.rafastore.data.DataGenerator;
import net.kampungweb.rafastore.model.ShopCategory;
import net.kampungweb.rafastore.utils.Tools;

import java.util.List;

public class ProductCategoriesActivity extends AppCompatActivity {

    private View parent_view;

    private RecyclerView recyclerView;
    private AdapterProductCategories adapterProductCategories;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_categories);
        parent_view = findViewById(R.id.parent_view);

        initToolbar();
        initContent();
    }

    private void initToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Categories");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this);
    }

    private void initContent() {
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);

        List<ShopCategory> items = DataGenerator.getShoppingCategory(this);

        //set data and list adapter
        adapterProductCategories = new AdapterProductCategories(this, items);
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

        super.onBackPressed();
    }
}
