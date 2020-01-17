package net.kampungweb.rafastore;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.balysv.materialripple.MaterialRippleLayout;

import net.kampungweb.rafastore.model.ImageSlider;
import net.kampungweb.rafastore.utils.Tools;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private AdapterImageSlider adapterImageSlider;
    private Runnable runnable = null;
    private Handler handler = new Handler();
    private ViewPager viewPager;
    private TextView tvTitleSlider, tvBriefSlider;
    private LinearLayout layoutDots;


    private static int[] arrayImagePlace = {
            R.drawable.image_20,
            R.drawable.image_21,
            R.drawable.image_22,
            R.drawable.image_23,
            R.drawable.image_8,
    };

    private static String[] arrayTitlePlace = {
            "Dui fringilla ornare finibus, orci odio",
            "Mauris sagittis non elit quis fermentum",
            "Mauris ultricies augue sit amet est sollicitudin",
            "Suspendisse ornare est ac auctor pulvinar",
            "Vivamus laoreet aliquam ipsum eget pretium",
    };

    private static String[] arrayBriefPlace = {
            "Foggy Hill",
            "The Backpacker",
            "River Forest",
            "Mist Mountain",
            "Side Park",
    };


    public HomeFragment() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_home, container, false);

        layoutDots = view.findViewById(R.id.layout_dots);
        viewPager = view.findViewById(R.id.vp_main_pager);
        adapterImageSlider = new AdapterImageSlider(getActivity(), new ArrayList<ImageSlider>());

        final List<ImageSlider> items = new ArrayList<>();
        for (int i = 0; i < arrayImagePlace.length; i++) {
            ImageSlider obj = new ImageSlider();
            obj.image = arrayImagePlace[i];
            obj.imageDrw = getResources().getDrawable(obj.image);
            obj.name = arrayTitlePlace[i];
            obj.brief = arrayBriefPlace[i];
            items.add(obj);
        }

        this.adapterImageSlider.setItems(items);
        viewPager.setAdapter(adapterImageSlider);

        //dislay image pertama dulu
        viewPager.setCurrentItem(0);

        //addBottomDots
        addBottomDots(layoutDots, adapterImageSlider.getCount(), 0);

        // Text belum jalan
        ((TextView) view.findViewById(R.id.title_slider)).setText(items.get(0).name);
        ((TextView) view.findViewById(R.id.brief_slider)).setText(items.get(0).brief);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ((TextView) view.findViewById(R.id.title_slider)).setText(items.get(0).name);
                ((TextView) view.findViewById(R.id.brief_slider)).setText(items.get(0).brief);
                addBottomDots(layoutDots, adapterImageSlider.getCount(), position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        startAutoSlider(adapterImageSlider.getCount());
        return view;

    }

    private static class AdapterImageSlider extends PagerAdapter {

        private Activity act;
        private List<ImageSlider> items;

        private AdapterImageSlider.OnItemClickListener onItemClickListener;

        private interface OnItemClickListener {
            void onItemClick(View view, ImageSlider obj);
        }

        public void setOnItemClickListener(AdapterImageSlider.OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }


        // constructor
        private AdapterImageSlider(Activity activity, List<ImageSlider> items) {
            this.act = activity;
            this.items = items;
        }

        @Override
        public int getCount() {
            return this.items.size();
        }

        public ImageSlider getItem(int pos) {
            return items.get(pos);
        }

        public void setItems(List<ImageSlider> items) {
            this.items = items;
            notifyDataSetChanged();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == (object);
        }

        @NonNull
        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            final ImageSlider o = items.get(position);

            LayoutInflater inflater = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert inflater != null;
            final View v = inflater.inflate(R.layout.item_slider_image, container, false);

            ImageView image = v.findViewById(R.id.image_slider_main);
            MaterialRippleLayout lyt_parent = v.findViewById(R.id.mrl_parent);
            Tools.displayImageOriginal(act, image, o.image);

            lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(v, o);
                    }
                }
            });

            (container).addView(v);

            return v;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            (container).removeView((RelativeLayout) object);
        }
    }

    //Bottom Dot Putih
    private void addBottomDots(LinearLayout layoutDots, int size, int current) {
        ImageView[] dots = new ImageView[size];

        layoutDots.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new ImageView(getContext());
            int widthHeight = 15;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(widthHeight, widthHeight));
            params.setMargins(10, 10, 10, 10);
            dots[i].setLayoutParams(params);
            dots[i].setImageResource(R.drawable.shape_circle_outline);
            layoutDots.addView(dots[i]);
        }

        if (dots.length > 0) {
            dots[current].setImageResource(R.drawable.shape_circle);
        }
    }

    // start auto slider
    private void startAutoSlider(final int count) {
        runnable = new Runnable() {
            @Override
            public void run() {
                int pos = viewPager.getCurrentItem();
                pos = pos + 1;
                if (pos >= count) pos = 0;
                viewPager.setCurrentItem(pos);
                handler.postDelayed(runnable, 3000);
            }
        };
        handler.postDelayed(runnable, 3000);
    }

    @Override
    public void onDestroy() {
        if (runnable != null) handler.removeCallbacks(runnable);
        super.onDestroy();
    }
}
