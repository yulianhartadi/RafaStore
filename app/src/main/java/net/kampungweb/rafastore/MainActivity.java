package net.kampungweb.rafastore;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.kampungweb.rafastore.model.Users;
import net.kampungweb.rafastore.prevalent.Prevalent;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    private String parentDbName = "Users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initLogin();

        // Keep login if user already login and prevent the login activity page to that user
        Paper.init(this);
        String UserPhoneKey = Paper.book().read(Prevalent.userPhoneKey);
        String UserPasswordKey = Paper.book().read(Prevalent.userPasswordKey);

        if (UserPhoneKey != "" && UserPasswordKey != "") {
            if (!TextUtils.isEmpty(UserPhoneKey) && !TextUtils.isEmpty(UserPasswordKey)) {

                //Ijinkan login dan selalu login
                alreadyLoginAllowAccess(UserPhoneKey, UserPasswordKey);

            }
        }
    }

    private void alreadyLoginAllowAccess(final String userPhoneKey, final String userPasswordKey) {

        final DatabaseReference rootRef;
        rootRef = FirebaseDatabase.getInstance().getReference();

        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("Users").child(userPhoneKey).exists()) {

                    //Login
                    Users userData = dataSnapshot.child(parentDbName).child(userPhoneKey).getValue(Users.class);

                    assert userData != null;
                    if (userData.getPhoneNumber().equals(userPhoneKey)) {

                        if (userData.getPassword().equals(userPasswordKey)) {

                            if (parentDbName.equals("Admins")) {
                                Toast.makeText(MainActivity.this, "Sebagai Admin", Toast.LENGTH_SHORT).show();

                                // Home as users
                                Intent intent = new Intent(MainActivity.this, AdminCategoryActivity.class);
                                startActivity(intent);
                            } else if (parentDbName.equals("Users")) {
                                Toast.makeText(MainActivity.this, "Sebagai User", Toast.LENGTH_SHORT).show();

                                // Home as admin
                                Intent homeIntent = new Intent(MainActivity.this, HomeActivity.class);
                                startActivity(homeIntent);
                            }

                        } else {
                            Toast.makeText(getApplicationContext(), "Login kredensial salah", Toast.LENGTH_SHORT).show();

                        }
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Masuk otomatis error silahkan update app Anda", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void initLogin() {
        Button btnLogin = findViewById(R.id.btn_login);
        Button btnRegister = findViewById(R.id.btn_register);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLogin = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intentLogin);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentRegister = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intentRegister);
            }
        });
    }

    // to pretend the video background from stop playing when user click button bar
    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

}
