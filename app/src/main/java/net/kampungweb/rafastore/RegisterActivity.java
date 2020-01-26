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
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
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

    private static final String TAG = "GoogleSignIn";
    private static final int RC_SIGN_IN = 9001;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;

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
                signInGoogle();
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

        //Google Sign In
        // [START config_signin]
        // Configure Google Sign In
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        // [START initialize_auth]
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]

    }

    // [START on_start_check_user]
   /* @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }*/
    // [END on_start_check_user]


    // [START onactivityresult]


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseWithAuthWithGoogle(account);
            } catch (ApiException e){
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // [START_EXCLUDE]
                updateUI(null);
                // [END_EXCLUDE]
            }
        }
    }
    // [END onactivityresult]

    // [START auth_with_google]
    private void firebaseWithAuthWithGoogle(GoogleSignInAccount acct){
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        // [START_EXCLUDE silent]
        //showProgressDialog();
        // [END_EXCLUDE]

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Snackbar.make(findViewById(R.id.register_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // [START_EXCLUDE]
                        //hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });

    }

    // Buat Akun Baru Daftar
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

    // Sign in with Google account on user device
    private void signInGoogle() {

        Intent signInGoogle = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInGoogle, RC_SIGN_IN);

        // Dummy Toast Login
        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                registerProgressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Simulasi Login Sukses", Toast.LENGTH_SHORT).show();
            }
        }, 3000);*/
    }

    // Sign out google
    private void signOut() {
        // Firebase sign out
        mAuth.signOut();

        // Google sign out
        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        updateUI(null);
                    }
                });
    }

    // revoke access account google
    private void revokeAccess() {
        // Firebase sign out
        mAuth.signOut();

        // Google revoke access
        mGoogleSignInClient.revokeAccess().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        updateUI(null);
                    }
                });
    }

    private void updateUI(FirebaseUser user){
        //hideProgressDialog();
        if (user != null){
            /*mStatusTextView.setText(getString(R.string.google_status_fmt, user.getEmail()));
            mDetailTextView.setText(getString(R.string.firebase_status_fmt, user.getUid()));

            findViewById(R.id.signInButton).setVisibility(View.GONE);
            findViewById(R.id.signOutAndDisconnect).setVisibility(View.VISIBLE);*/
        } else {
            /*
            mStatusTextView.setText(R.string.signed_out);
            mDetailTextView.setText(null);

            findViewById(R.id.signInButton).setVisibility(View.VISIBLE);
            findViewById(R.id.signOutAndDisconnect).setVisibility(View.GONE);*/
        }
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
    protected void onStart() {
        super.onStart();
        // jika user sudah pernah login dengan email google account di devicenya

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(intent);
    }

    // if user click bar button to pretend video background from stop playing
    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }
}
