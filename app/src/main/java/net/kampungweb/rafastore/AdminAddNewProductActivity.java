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

    private String categoryName;
    private String nameProduct;
    private String priceProduct;
    private String descriptionProduct;
    private String stockProduct;
    private String saveCurrentDate;
    private String saveCurrentTime;
    private ImageView ivMainImageProduct;
    private ImageView ivAddImageProduct;
    private ImageView ivThumbImg1;
    private ImageView ivThumbImg2;
    private ImageView ivThumbImg3;
    private AppCompatEditText inputProductDesc;
    private EditText inputNameProduct, inputPriceProduct, inputStockProduct;

    private static final int GalleryPick = 1;
    private static final int GalleryThumb1 = 2;
    private static final int GalleryThumb2 = 3;
    private static final int GalleryThumb3 = 4;

    private Uri imageUri;
    private String productRandomKey;
    private String downloadImageUrl;
    private String imageThumb1;
    private String imageThumb2;
    private String imageThumb3;
    private StorageReference productImageRef;
    private DatabaseReference productsRef;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_new_product);

        categoryName = Objects.requireNonNull(Objects.requireNonNull(getIntent().getExtras()).get("Category")).toString();

        // connect to storage Firebase
        productImageRef = FirebaseStorage.getInstance().getReference().child("ProductImages");
        productsRef = FirebaseDatabase.getInstance().getReference().child("Products");

        //panggil content
        initToolbar();

        //panggil content
        initContent();
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(categoryName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this);

    }

    //initial content
    private void initContent() {
        ivMainImageProduct = findViewById(R.id.iv_main_product_image);
        ivAddImageProduct = findViewById(R.id.iv_add_image);
        inputNameProduct = findViewById(R.id.et_name_product);
        inputPriceProduct = findViewById(R.id.et_price_product);
        inputProductDesc = findViewById(R.id.acet_deskripsi_product);
        inputStockProduct = findViewById(R.id.et_stock_product);
        AppCompatButton btnCancel = findViewById(R.id.acb_btn_cancel);
        AppCompatButton btnAddProduct = findViewById(R.id.acb_btn_add_product);

        ivThumbImg1 = findViewById(R.id.iv_add_thumb_img1);
        ivThumbImg2 = findViewById(R.id.iv_add_thumb_img2);
        ivThumbImg3 = findViewById(R.id.iv_add_thumb_img3);

        progressDialog = new ProgressDialog(this);


        // add new product
        ivAddImageProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open image on user device
                addMainImage();
            }
        });

        // add new tumb product
        ivThumbImg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // add image from gallery
                Toast.makeText(AdminAddNewProductActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
                // ambil image dari gallery device pengguna
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GalleryThumb1);
            }
        });

        ivThumbImg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // add image from gallery
                Toast.makeText(AdminAddNewProductActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
                // ambil image dari gallery device pengguna
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GalleryThumb2);
            }
        });

        ivThumbImg3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // add image from gallery
                Toast.makeText(AdminAddNewProductActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
                // ambil image dari gallery device pengguna
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GalleryThumb3);
            }
        });

        // add product image and all description
        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validateProductData();

            }
        });

        // batal upload
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cancelAddProductIntent = new Intent(AdminAddNewProductActivity.this, AdminCategoryActivity.class);
                startActivity(cancelAddProductIntent);
            }
        });


    }

    // add first thumb image and main image
    private void addMainImage() {

        // ambil image dari gallery device pengguna
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
            ivAddImageProduct.setImageURI(imageUri);
            ivMainImageProduct.setImageURI(imageUri);

        }

        if (requestCode == GalleryThumb1 && resultCode == RESULT_OK && data != null) {

            imageUri = data.getData();
            ivThumbImg1.setImageURI(imageUri);

        }

        if (requestCode == GalleryThumb2 && resultCode == RESULT_OK && data != null) {

            imageUri = data.getData();
            ivThumbImg2.setImageURI(imageUri);

        }

        if (requestCode == GalleryThumb3 && resultCode == RESULT_OK && data != null) {

            imageUri = data.getData();
            ivThumbImg3.setImageURI(imageUri);

        }

    }

    // Validasi data product yang diupload
    private void validateProductData() {

        nameProduct = inputNameProduct.getText().toString();
        priceProduct = inputPriceProduct.getText().toString();
        descriptionProduct = Objects.requireNonNull(inputProductDesc.getText()).toString();
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

        SimpleDateFormat currentDate = new SimpleDateFormat("dd MM, yyyy");
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
                progressDialog.dismiss();

                Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }

                        downloadImageUrl = filePath.getDownloadUrl().toString();
                        imageThumb1 = filePath.getDownloadUrl().toString();
                        imageThumb2 = filePath.getDownloadUrl().toString();
                        imageThumb3 = filePath.getDownloadUrl().toString();

                        return filePath.getDownloadUrl();

                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(AdminAddNewProductActivity.this, "Produk sukses tersimpan di database", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();

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
        productMap.put("imageAlt1", imageThumb1);
        productMap.put("imageAlt2", imageThumb2);
        productMap.put("imageAlt3", imageThumb3);
        productMap.put("productName", nameProduct);
        productMap.put("productPrice", priceProduct);
        productMap.put("productDescription", descriptionProduct);
        productMap.put("productStock", stockProduct);

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
                            String message = Objects.requireNonNull(task.getException()).toString();
                            Toast.makeText(AdminAddNewProductActivity.this, "Produk error : " + message, Toast.LENGTH_SHORT).show();

                        }

                    }
                });
    }
}
