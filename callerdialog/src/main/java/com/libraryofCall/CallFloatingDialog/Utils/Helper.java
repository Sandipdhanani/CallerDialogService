package com.libraryofCall.CallFloatingDialog.Utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import java.util.Random;

public class Helper {
//    public static String Admob_Visibility = "1";
//    public static String add_status = "1";
//
//    public static String Admob_Banner = "ca-app-pub-3436981624340085/1566789920";
//    public static String Admob_Native = "ca-app-pub-3436981624340085/8643603664";


    public static Bitmap generateAvatar(String name) {
        int size = 100;
        int backgroundColor = getLightColor();

        Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setColor(backgroundColor);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(size / 2, size / 2, size / 2, paint);

        int darkenedColor = getDarkenedColor(backgroundColor);
        paint.setColor(darkenedColor);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(45);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText(String.valueOf(name.charAt(0)).toUpperCase(), size / 2, size / 2 + 15, paint);

        return bitmap;
    }

    public static int getDarkenedColor(int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] *= 0.6f;
        return Color.HSVToColor(hsv);
    }

    public static int getLightColor() {
        Random random = new Random();
        int red = (random.nextInt(156) + 100);
        int green = (random.nextInt(156) + 100);
        int blue = (random.nextInt(156) + 100);
        return Color.rgb(red, green, blue);
    }
}
