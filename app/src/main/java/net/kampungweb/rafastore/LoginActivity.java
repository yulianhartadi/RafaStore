package net.kampungweb.rafastore;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.kampungweb.rafastore.model.Users;
import net.kampungweb.rafastore.prevalent.Prevalent;

import java.util.Objects;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {

    private ProgressBar progress_bar;
    private FloatingActionButton fabLogin;
    private View parent_view;
    private TextInputEditText tiePhoneNumber, tiePassword;
    private TextView adminLink, notAdmin;

    private String parentDbName = "Users";

    private AppCompatCheckBox accpRememberMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        parent_view = findViewById(android.R.id.content);
        progress_bar = findViewById(R.id.progress_bar);
        fabLogin = findViewById(R.id.fab_login);
        TextView tvRegister = findViewById(R.id.sign_up_for_account);
        tiePhoneNumber = findViewById(R.id.tie_phone);
        tiePassword = findViewById(R.id.tie_password);

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

        accpRememberMe = findViewById(R.id.accb_remember_me);
        adminLink = findViewById(R.id.admin);
        notAdmin = findViewById(R.id.not_admin);

        adminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adminLink.setVisibility(View.GONE);
                notAdmin.setVisibility(View.VISIBLE);
                parentDbName = "Admins";
            }
        });

        notAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adminLink.setVisibility(View.VISIBLE);
                notAdmin.setVisibility(View.GONE);
                parentDbName = "Users";
            }
        });

        fabLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                progress_bar.setVisibility(View.VISIBLE);
                loginAction();
            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(), "to register", Toast.LENGTH_SHORT).show();
                Intent regIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(regIntent);
            }
        });


    }

    private void loginAction() {
        progress_bar.setVisibility(View.VISIBLE);
        fabLogin.setAlpha(0f);

        final String phone = Objects.requireNonNull(tiePhoneNumber.getText()).toString();
        final String password = Objects.requireNonNull(tiePassword.getText()).toString();

        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "Silahkan isi dengan no hp. Anda", Toast.LENGTH_SHORT).show();
            fabLogin.setAlpha(0f);
            progress_bar.setVisibility(View.INVISIBLE);
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Silahkan isi dengan password Anda", Toast.LENGTH_SHORT).show();
            fabLogin.setAlpha(0f);
            progress_bar.setVisibility(View.INVISIBLE);
        } else {

            fabLogin.setAlpha(0f);
            allowAccessToAccount(phone, password);
        }


    }

    private void allowAccessToAccount(final String phone, final String password) {

        // Jika user/admin cek rememberMe radiobox
        if (accpRememberMe.isChecked()) {
            Paper.book().write(Prevalent.userPhoneKey, phone);
            Paper.book().write(Prevalent.userPasswordKey, password);
        }

        final DatabaseReference rootRef;
        rootRef = FirebaseDatabase.getInstance().getReference();

        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.child(parentDbName).child(phone).exists()) {

                    //Login
                    Users userData = dataSnapshot.child(parentDbName).child(phone).getValue(Users.class);

                    assert userData != null;
                    if (userData.getPhoneNumber().equals(phone)) {

                        if (userData.getPassword().equals(password)) {

                            if (parentDbName.equals("Users")) {
                                Toast.makeText(LoginActivity.this, "Login User", Toast.LENGTH_SHORT).show();
                                progress_bar.setVisibility(View.GONE);

                                // Home as users
                                Intent homeIntent = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(homeIntent);
                            } else if (parentDbName.equals("Admins")) {
                                Toast.makeText(LoginActivity.this, "Login Admin", Toast.LENGTH_SHORT).show();
                                progress_bar.setVisibility(View.GONE);

                                // Home as admin
                                Intent homeIntent = new Intent(LoginActivity.this, AdminCategoryActivity.class);
                                startActivity(homeIntent);
                            }

                        } else {
                            Toast.makeText(getApplicationContext(), "Login kredensial salah", Toast.LENGTH_SHORT).show();
                            fabLogin.setAlpha(1f);
                            progress_bar.setVisibility(View.GONE);
                        }
                    }

                } else {
                    Snackbar.make(parent_view, "Akun dengan No. HP : " + phone + " tidak ditemukan, silahkan daftar terlebih dahulu",
                            Snackbar.LENGTH_SHORT).show();
                    fabLogin.setAlpha(1f);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void alreadyLoginAllowAccess(final String phone, final String password) {
        final DatabaseReference rootRef;
        rootRef = FirebaseDatabase.getInstance().getReference();

        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("Users").child(phone).exists()) {

                    //Login
                    Users userData = dataSnapshot.child("Users").child(phone).getValue(Users.class);

                    assert userData != null;
                    if (userData.getPhoneNumber().equals(phone)) {

                        if (userData.getPassword().equals(password)) {

                            if (parentDbName.equals("Users")) {
                                Toast.makeText(LoginActivity.this, "Sebagai User", Toast.LENGTH_SHORT).show();
                                progress_bar.setVisibility(View.GONE);

                                // Home as users
                                Intent homeIntent = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(homeIntent);
                            } else if (parentDbName.equals("Admins")) {
                                Toast.makeText(LoginActivity.this, "Sebagai Admin", Toast.LENGTH_SHORT).show();
                                progress_bar.setVisibility(View.GONE);

                                // Home as admin
                                Intent homeIntent = new Intent(LoginActivity.this, AdminCategoryActivity.class);
                                startActivity(homeIntent);
                            }

                        } else {
                            Toast.makeText(getApplicationContext(), "Login kredensial salah", Toast.LENGTH_SHORT).show();
                            fabLogin.setAlpha(1f);
                            progress_bar.setVisibility(View.GONE);
                        }
                    }

                } else {
                    Snackbar.make(parent_view, "Akun dengan No. HP : " + phone + " tidak ditemukan, silahkan daftar terlebih dahulu",
                            Snackbar.LENGTH_SHORT).show();
                    fabLogin.setAlpha(1f);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}

