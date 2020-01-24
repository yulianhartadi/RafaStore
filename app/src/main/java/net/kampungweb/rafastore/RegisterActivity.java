package net.kampungweb.rafastore;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.kampungweb.rafastore.model.Users;
import net.kampungweb.rafastore.prevalent.Prevalent;
import net.kampungweb.rafastore.utils.NetworkStatus;

import java.util.HashMap;
import java.util.Objects;

import io.paperdb.Paper;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText tieFullName, tieUserName, tieEmail, tiePassword, tiePhone;
    private ProgressBar registerProgressBar;
    private String parentDbName = "Users";
    private TextView tvBackLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        tieFullName = findViewById(R.id.tie_full_name);
        tieUserName = findViewById(R.id.tie_username);
        tieEmail = findViewById(R.id.tie_email);
        tiePassword = findViewById(R.id.tie_password);
        tiePhone = findViewById(R.id.tie_phone);
        registerProgressBar = findViewById(R.id.pb_progress);
        tvBackLogin = findViewById(R.id.tv_back_login);

        tvBackLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backIntent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(backIntent);
            }
        });

        Button btnRegister = findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Cek Koneksi, kalau online, buat akun
                if (NetworkStatus.getInstance(getApplicationContext()).isOnline()){
                    createAccount();
                } else {
                    Toast.makeText(getApplicationContext(),"Tidak ada sambungan data",Toast.LENGTH_SHORT).show();
                    Log.v("Home", "############################You are not online!!!!");
                }
                //createAccount();
            }
        });

        Button btnLoginGoogle = findViewById(R.id.btn_login_google);
        btnLoginGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerProgressBar.setVisibility(View.VISIBLE);
                registerAccount();
            }
        });

        // Keep login if user already login and prevent the login activity page to that user
        Paper.init(this);
        String UserPhoneKey = Paper.book().read(Prevalent.userPhoneKey);
        String UserPasswordKey = Paper.book().read(Prevalent.userPasswordKey);

        if (UserPhoneKey != "" && UserPasswordKey != "") {
            if (!TextUtils.isEmpty(UserPhoneKey) && !TextUtils.isEmpty(UserPasswordKey)) {

                //Ijinkan login
                AllowAccess(UserPhoneKey, UserPasswordKey);

            }
        }
    }


    // Daftar Akun Baru
    private void createAccount() {
        String name = Objects.requireNonNull(tieFullName.getText()).toString();
        String userName = Objects.requireNonNull(tieUserName.getText()).toString();
        String email = Objects.requireNonNull(tieEmail.getText()).toString();
        String password = Objects.requireNonNull(tiePassword.getText()).toString();
        String phone = Objects.requireNonNull(tiePhone.getText()).toString();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(getApplicationContext(), "Nama Anda belum diisi", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(userName)) {
            Toast.makeText(getApplicationContext(), "Nama Pengguna Anda belum diisi", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Email Anda belum diisi", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Kata kunci Anda belum diisi", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(phone)) {
            Toast.makeText(getApplicationContext(), "No. HP Anda belum diisi", Toast.LENGTH_SHORT).show();
        } else {
            registerProgressBar.setVisibility(View.VISIBLE);
            //registerAccount();
            validatePhoneNumber(name, userName, email, password, phone);
        }
    }

    private void validatePhoneNumber(final String name, final String userName, final String email, final String password, final String phone) {
        // Connect Firebase Realtime Database
        final DatabaseReference rootRef;
        rootRef = FirebaseDatabase.getInstance().getReference();

        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //username validation
                if (!(dataSnapshot.child("Users").child(phone).exists())) {

                    HashMap<String, Object> userDataMap = new HashMap<>();
                    userDataMap.put("fullName", name);
                    userDataMap.put("userName", userName);
                    userDataMap.put("email", email);
                    userDataMap.put("password", password);
                    userDataMap.put("phoneNumber", phone);

                    rootRef.child("Users").child(phone).updateChildren(userDataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(RegisterActivity.this, "Selamat akun baru Anda telah sukses dibuat",
                                                Toast.LENGTH_SHORT).show();
                                        Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                                        startActivity(loginIntent);
                                        registerProgressBar.setVisibility(View.GONE);

                                    } else {
                                        registerProgressBar.setVisibility(View.GONE);
                                        Toast.makeText(RegisterActivity.this, "Tidak terkoneksi jaringan. Silahkan coba lagi", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    registerProgressBar.setVisibility(View.GONE);
                    Toast.makeText(RegisterActivity.this, "No HP : " + phone + " telah digunakan", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void registerAccount() {

        // Toast Login
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                registerProgressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Simulasi Login Sukses", Toast.LENGTH_SHORT).show();
            }
        }, 3000);
    }

    private void AllowAccess(final String userPhoneKey, final String userPasswordKey) {

        final DatabaseReference rootRef;
        rootRef = FirebaseDatabase.getInstance().getReference();

        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(parentDbName).child(userPhoneKey).exists()) {

                    //Login
                    Users userData = dataSnapshot.child(parentDbName).child(userPhoneKey).getValue(Users.class);

                    assert userData != null;
                    if (userData.getPhoneNumber().equals(userPhoneKey)) {

                        if (userData.getPassword().equals(userPasswordKey)) {
                            registerProgressBar.setVisibility(View.GONE);

                            // Home Sukses
                            Intent homeIntent = new Intent(RegisterActivity.this, HomeActivity.class);
                            startActivity(homeIntent);
                            Toast.makeText(getApplicationContext(), "Senang kamu login kembali kesini", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(getApplicationContext(), "Login kredensial salah", Toast.LENGTH_SHORT).show();
                            registerProgressBar.setVisibility(View.GONE);
                        }
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }
}
