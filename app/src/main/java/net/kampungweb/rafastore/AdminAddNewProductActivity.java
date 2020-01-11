package net.kampungweb.rafastore;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import net.kampungweb.rafastore.utils.Tools;

import java.util.Objects;

public class AdminAddNewProductActivity extends AppCompatActivity {

    private String categoryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_new_product);

        categoryName = getIntent().getExtras().get("Category").toString();
        Toast.makeText(getApplicationContext(), categoryName, Toast.LENGTH_SHORT).show();

        initToolbar();
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(categoryName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this);

    }


}
