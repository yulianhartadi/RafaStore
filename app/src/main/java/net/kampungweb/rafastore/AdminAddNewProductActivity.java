package net.kampungweb.rafastore;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;

import net.kampungweb.rafastore.utils.Tools;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

public class AdminAddNewProductActivity extends AppCompatActivity {

    private String categoryName, nameProduct, priceProduct, descriptionProduct, stockProduct, saveCurrentDate, saveCurrentTime;
    private ImageView inputNewImageProduct;
    private AppCompatEditText inputProductDesc;
    private EditText inputNameProduct, inputPriceProduct, inputStockProduct;
    private AppCompatButton btnCancel, btnAddProduct;

    private static final int GalleryPick = 1;
    private Uri imageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_new_product);

        categoryName = getIntent().getExtras().get("Category").toString();
        Toast.makeText(getApplicationContext(), categoryName, Toast.LENGTH_SHORT).show();

        initToolbar();
        initContent();
    }

    private void initContent() {
        inputNewImageProduct = findViewById(R.id.iv_upload_image);
        inputNameProduct = findViewById(R.id.et_name_product);
        inputPriceProduct = findViewById(R.id.et_price_product);
        inputProductDesc = findViewById(R.id.acet_deskripsi_product);
        inputStockProduct = findViewById(R.id.et_stock_product);
        btnCancel = findViewById(R.id.acb_btn_cancel);
        btnAddProduct = findViewById(R.id.acb_btn_add_product);


        // add new product
        inputNewImageProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // open image on user device
                openImageGallery();

            }
        });

        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validateProductData();

            }
        });


    }


    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(categoryName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this);

    }

    private void openImageGallery() {

        // ambil image dari device pengguna
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GalleryPick);

    }

    // Update Activity if image product successfull updated
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GalleryPick && resultCode == RESULT_OK && data != null) {

            imageUri = data.getData();
            inputNewImageProduct.setImageURI(imageUri);
        }
    }

    // Validasi data product yang diupload
    private void validateProductData() {

        nameProduct = inputNameProduct.getText().toString();
        priceProduct = inputPriceProduct.getText().toString();
        descriptionProduct = inputProductDesc.getText().toString();
        stockProduct = inputStockProduct.getText().toString();

        if (imageUri == null) {

            Toast.makeText(this, "Product image is mandatory...", Toast.LENGTH_SHORT).show();

        } else if (TextUtils.isEmpty(nameProduct)) {

            Toast.makeText(this, "Nama produk belum diisi", Toast.LENGTH_SHORT).show();

        } else if (TextUtils.isEmpty(priceProduct)){

            Toast.makeText(this, "Deskripsi produk belum diisi", Toast.LENGTH_SHORT).show();

        } else if (TextUtils.isEmpty(descriptionProduct)){

            Toast.makeText(this, "Deskripsi produk belum diisi", Toast.LENGTH_SHORT).show();

        } else if (TextUtils.isEmpty(stockProduct)){

            Toast.makeText(this, "Stock produk belum diisi", Toast.LENGTH_SHORT).show();

        } else {

            storeImageInformation();

        }


        // input option jasa kirim belum


    }

    // Image and product information
    private void storeImageInformation() {

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());


    }
}
