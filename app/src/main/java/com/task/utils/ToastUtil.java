package com.task.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.task.R;

public class ToastUtil {

    public static void show(Context context, int resId) {
        show(context, context.getResources().getText(resId), Toast.LENGTH_SHORT);
    }

    public static void show(Context context, int resId, int duration) {
        show(context, context.getResources().getText(resId), duration);
    }

    public static void show(Context context, CharSequence text) {
        show(context, text, Toast.LENGTH_SHORT);
    }

    public static void show(Context context, CharSequence text, int duration) {
        Toast.makeText(context, text, duration).show();
    }

    public static void makeCustomToast(Context context, int resId) {
        makeCustomToast(context, context.getResources().getText(resId));
    }

    public static void makeCustomToast(Context context, CharSequence text) {
        View view = View.inflate(context, R.layout.custom_toast, null);
        TextView textView = (TextView) view.findViewById(R.id.text_view);
        textView.setText(text);

        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/regular.otf");
        textView.setTypeface(typeface);

        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.BOTTOM| Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.setView(view);
        toast.show();
    }

    private ToastUtil() {
        throw new UnsupportedOperationException(
                "Should not create instance of Util class. Please use as static..");
    }
}