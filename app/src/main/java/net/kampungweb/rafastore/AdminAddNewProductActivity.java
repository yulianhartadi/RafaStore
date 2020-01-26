package net.kampungweb.rafastore;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;


import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import net.kampungweb.rafastore.utils.Tools;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

public class AdminAddNewProductActivity extends AppCompatActivity {

    private String categoryName, nameProduct, priceProduct, descriptionProduct, stockProduct, saveCurrentDate, saveCurrentTime;
    private ImageView inputNewImageProduct;
    private AppCompatEditText inputProductDesc;
    private EditText inputNameProduct, inputPriceProduct, inputStockProduct;
    private AppCompatButton btnCancel, btnAddProduct;

    private static final int GalleryPick = 1;
    private Uri imageUri;
    private String productRandomKey, downloadImageUrl;
    private StorageReference productImageRef;
    private DatabaseReference productsRef;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_new_product);

        categoryName = getIntent().getExtras().get("Category").toString();

        // connect to storage Firebase
        productImageRef = FirebaseStorage.getInstance().getReference().child("Product Images");
        productsRef = FirebaseDatabase.getInstance().getReference().child("Products");

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

        progressDialog = new ProgressDialog(this);


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

        } else if (TextUtils.isEmpty(priceProduct)) {

            Toast.makeText(this, "Deskripsi produk belum diisi", Toast.LENGTH_SHORT).show();

        } else if (TextUtils.isEmpty(descriptionProduct)) {

            Toast.makeText(this, "Deskripsi produk belum diisi", Toast.LENGTH_SHORT).show();

        } else if (TextUtils.isEmpty(stockProduct)) {

            Toast.makeText(this, "Stock produk belum diisi", Toast.LENGTH_SHORT).show();

        } else {

            storeProductInformation();

        }


        // input option jasa kirim belum


    }

    // Image and product information
    private void storeProductInformation() {

        //progress dialog add new product
        progressDialog.setTitle("Tambah produk baru");
        progressDialog.setMessage("Admin di tunggu ya..., Sedang menambahkan produk");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        productRandomKey = saveCurrentDate + saveCurrentTime;

        final StorageReference filePath = productImageRef.child(imageUri.getLastPathSegment() + productRandomKey + ".jpg");

        final UploadTask uploadTask = filePath.putFile(imageUri);

        // Upload product process
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                String message = e.toString();
                Toast.makeText(AdminAddNewProductActivity.this, "Error : " + message, Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Toast.makeText(AdminAddNewProductActivity.this, "Produk sukses di upload", Toast.LENGTH_SHORT).show();

                Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }

                        downloadImageUrl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();

                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(AdminAddNewProductActivity.this, "Produk sukses tersimpan di database", Toast.LENGTH_SHORT).show();
                            saveProductInfoToDatabase();
                        }
                    }
                });
            }
        });

    }

    // upload produk ke Firebase
    private void saveProductInfoToDatabase() {

        HashMap<String, Object> productMap = new HashMap<>();
        productMap.put("pid", productRandomKey);
        productMap.put("date", saveCurrentDate);
        productMap.put("time", saveCurrentTime);
        productMap.put("category", categoryName);
        productMap.put("image", downloadImageUrl);
        productMap.put("product name", nameProduct);
        productMap.put("product price", priceProduct);
        productMap.put("product description", descriptionProduct);
        productMap.put("product stock", stockProduct);

        productsRef.child(productRandomKey).updateChildren(productMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {

                            progressDialog.dismiss();
                            Toast.makeText(AdminAddNewProductActivity.this, "Produk sukses ditambahkan..", Toast.LENGTH_SHORT).show();
                            Intent categoryIntent = new Intent(AdminAddNewProductActivity.this, AdminCategoryActivity.class);
                            startActivity(categoryIntent);

                        } else {

                            progressDialog.dismiss();
                            String message = task.getException().toString();
                            Toast.makeText(AdminAddNewProductActivity.this, "Produk error : " + message, Toast.LENGTH_SHORT).show();

                        }

                    }
                });
    }
}
