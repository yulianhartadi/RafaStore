package net.kampungweb.rafastore;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import net.kampungweb.rafastore.utils.Tools;

import java.util.Objects;

public class CheckOutActivity extends AppCompatActivity {

    private enum State {
        SHIPPING,
        PEMBAYARAN,
        KONFIRMASI
    }

    State[] array_state = new State[]{State.SHIPPING, State.PEMBAYARAN, State.KONFIRMASI};

    private ImageView dotPengiriman, dotPembayaran, dotKonfirmasi;
    private TextView tvPengiriman, tvPembayaran, tvKonfirmasi;
    private View lineFirst, lineSecond;

    private int index_state = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        initToolbar();
        initContent();

        //display fragment first user checkout
        displayFragment(State.SHIPPING);
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar_checkout);
        toolbar.setNavigationIcon(R.drawable.ic_close);
        Objects.requireNonNull(toolbar.getNavigationIcon()).setColorFilter(getResources().getColor(R.color.grey_60), PorterDuff.Mode.SRC_ATOP);
        setSupportActionBar(toolbar);
        //iki dapat warning
        //getSupportActionBar().setTitle(null);

        Objects.requireNonNull(getSupportActionBar()).setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this, android.R.color.white);
        Tools.setSystemBarLight(this);
    }

    private void initContent() {
        lineFirst = findViewById(R.id.line_first);
        lineSecond = findViewById(R.id.line_second);
        dotPengiriman = findViewById(R.id.iv_dot_shipping);
        dotPembayaran = findViewById(R.id.iv_dot_payment);
        dotKonfirmasi = findViewById(R.id.iv_dot_confirm);
        tvPengiriman = findViewById(R.id.tv_shipping);
        tvPembayaran = findViewById(R.id.tv_payment);
        tvKonfirmasi = findViewById(R.id.tv_konfirmasi);

        dotPembayaran.setColorFilter(getResources().getColor(R.color.grey_10), PorterDuff.Mode.SRC_ATOP);
        dotKonfirmasi.setColorFilter(getResources().getColor(R.color.grey_10), PorterDuff.Mode.SRC_ATOP);

        (findViewById(R.id.lyt_next)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index_state == array_state.length - 1) return;
                index_state++;
                displayFragment(array_state[index_state]);

                if (index_state > 1) {
                    //display pop up process
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //registerProgressBar.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(), "Simulasi Transaksi Sukses", Toast.LENGTH_SHORT).show();
                        }
                    }, 3000);
                }
            }
        });

        (findViewById(R.id.lyt_previous)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index_state < 1) return;
                index_state--;
                displayFragment(array_state[index_state]);

                if (index_state > 1){
                    // pop up kembali ke cart fragment
                }
            }
        });

    }

    //display fragment step checkout
    private void displayFragment(State state) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = null;
        refreshStepTitle();

        if (state.name().equalsIgnoreCase(State.SHIPPING.name())) {
            //fragment shipping
            fragment = new PengirimanFragment();
            tvPengiriman.setTextColor(getResources().getColor(R.color.grey_90));
            dotPengiriman.clearColorFilter();

        } else if (state.name().equalsIgnoreCase(State.PEMBAYARAN.name())) {
            //fragment pembayaran
            fragment = new PembayaranFragment();
            lineFirst.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            dotPengiriman.setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
            dotPembayaran.clearColorFilter();
            tvPembayaran.setTextColor(getResources().getColor(R.color.grey_90));

        } else if (state.name().equalsIgnoreCase(State.KONFIRMASI.name())) {
            //fragment konfirmasi
            fragment = new KonfirmasiFragment();
            lineSecond.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            dotPengiriman.setColorFilter(getResources().getColor(R.color.colorPrimary));
            dotPembayaran.setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
            dotKonfirmasi.clearColorFilter();
            tvKonfirmasi.setTextColor(getResources().getColor(R.color.grey_90));
        }

        if (fragment == null) return;
        fragmentTransaction.replace(R.id.frame_content, fragment);
        fragmentTransaction.commit();
    }

    private void refreshStepTitle() {
        tvPengiriman.setTextColor(getResources().getColor(R.color.grey_20));
        tvPembayaran.setTextColor(getResources().getColor(R.color.grey_20));
        tvKonfirmasi.setTextColor(getResources().getColor(R.color.grey_20));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
