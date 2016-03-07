package com.plusend.photo.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;

import com.plusend.photo.R;

import java.io.ByteArrayOutputStream;

/**
 * Created by plusend on 16/2/3.
 */
public class BitmapUtils {

    public static byte[] Bitmap2Bytes(Bitmap b) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG, 0, baos);
        return baos.toByteArray();
    }

    public static Bitmap Bytes2Bimap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }
    }

    public static Bitmap getBitmapFromUri(Context context, Uri uri) {
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * refer link: https://magiclen.org/android-drawingcache/
     */
    public static Bitmap getMagicDrawingCache(View view) {
        Bitmap bitmap = (Bitmap) view.getTag(R.id.cacheBitmapKey);
        Boolean dirty = (Boolean) view.getTag(R.id.cacheBitmapDirtyKey);
        if (view.getWidth() + view.getHeight() == 0) {
            view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        }
        int viewWidth = view.getWidth();
        int viewHeight = view.getHeight();
        if (bitmap == null || bitmap.getWidth() != viewWidth || bitmap.getHeight() != viewHeight) {
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
            bitmap = Bitmap.createBitmap(viewWidth, viewHeight, Bitmap.Config.RGB_565);
            view.setTag(R.id.cacheBitmapKey, bitmap);
            dirty = true;
        }
        if (dirty) {
            bitmap.eraseColor(0x000000);
            Canvas canvas = new Canvas(bitmap);
            view.draw(canvas);
            view.setTag(R.id.cacheBitmapDirtyKey, false);
        }
        return bitmap;
    }
}
