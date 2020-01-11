package net.kampungweb.rafastore.data;

import android.content.Context;
import android.content.res.TypedArray;

import androidx.appcompat.content.res.AppCompatResources;

import net.kampungweb.rafastore.R;
import net.kampungweb.rafastore.model.ShopCategory;

import java.util.ArrayList;
import java.util.List;

/**
 * Generate dummy data shopping category
 *
 * @return list of object
 */

public class DataGenerator {

    public static List<ShopCategory> getShoppingCategory(Context ctx) {
        List<ShopCategory> items = new ArrayList<>();
        TypedArray drw_arr = ctx.getResources().obtainTypedArray(R.array.shop_category_icon);
        TypedArray drw_arr_bg = ctx.getResources().obtainTypedArray(R.array.shop_category_bg);
        String title_arr[] = ctx.getResources().getStringArray(R.array.shop_category_title);
        String brief_arr[] = ctx.getResources().getStringArray(R.array.shop_category_brief);
        for (int i = 0; i < drw_arr.length(); i++){
            ShopCategory obj = new ShopCategory();
            obj.image = drw_arr.getResourceId(i, -1);
            obj.image_bg = drw_arr_bg.getResourceId(i, -1);
            obj.title = title_arr[i];
            obj.brief = brief_arr[i];
            obj.imageDrw = AppCompatResources.getDrawable(ctx, obj.image);
            items.add(obj);
        }
        return items;
    }

}
