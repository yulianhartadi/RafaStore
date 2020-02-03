package net.kampungweb.rafastore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import net.kampungweb.rafastore.utils.Tools;

import java.util.Objects;

public class CheckOutActivity extends AppCompatActivity {

    private enum State{
        SHIPPING,
        PEMBAYARAN,
        KONFIRMASI
    }

    State[] array_state = new State[]{State.SHIPPING, State.PEMBAYARAN, State.KONFIRMASI};

    private ImageView dotShipping, dotPembayaran, dotKonfirmasi;
    private TextView tvShipping, tvPembayaran, tvKonfirmasi;
    private View lineFirst, lineSecond;

    private int index_state = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        initToolbar();
        initContent();
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar_checkout);
        toolbar.setNavigationIcon(R.drawable.ic_close);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.grey_60), PorterDuff.Mode.SRC_ATOP);
        setSupportActionBar(toolbar);
        //iki dapat warning
        //getSupportActionBar().setTitle(null);

        Objects.requireNonNull(getSupportActionBar()).setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this, android.R.color.white);
        Tools.setSystemBarLight(this);
    }

    private void initContent(){
        lineFirst = findViewById(R.id.line_first);
        lineSecond = findViewById(R.id.line_second);
        dotShipping = findViewById(R.id.iv_dot_shipping);
        dotPembayaran = findViewById(R.id.iv_dot_payment);
        dotKonfirmasi = findViewById(R.id.iv_dot_confirm);
        tvShipping = findViewById(R.id.tv_shipping);
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
            }
        });

        (findViewById(R.id.lyt_previous)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index_state < 1) return;
                index_state--;
                displayFragment(array_state[index_state]);
            }
        });

    }

    private void displayFragment(State state) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment fragment = null;
        refreshStepTitle();



    }

    private void refreshStepTitle() {
    }
}
