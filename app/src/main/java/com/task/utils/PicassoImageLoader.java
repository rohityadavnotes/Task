package com.task.utils;

import android.content.Context;
import android.widget.ImageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.task.R;

public class PicassoImageLoader {

    /**
     * Load Image
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadImage(Context context, String url, ImageView imageView) {
        Picasso.get()
                .load(url)
                .resize(200, 200)
                /* https://placeholder.pics/ */
                //.placeholder(R.drawable.ic_placeholder)
                //.error(R.drawable.ic_error)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        LogcatUtil.informationMessage("PI", "onSuccess");
                    }

                    @Override
                    public void onError(Exception e) {
                        LogcatUtil.informationMessage("PI", e.getMessage());
                    }
                });
    }

    private PicassoImageLoader() {
        throw new UnsupportedOperationException("Should not create instance of Util class. Please use as static..");
    }
}
