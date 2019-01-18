package com.linearbd.mixdesign.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ImageSaver {

    public static String captureScreenShotAndSave(Context mContext, View view,String fileName) {
        view.setBackgroundColor(Color.WHITE);
        String mPath = mContext.getApplicationContext().getExternalCacheDir() + "/" + "IMG_" + System.currentTimeMillis() + "_" + fileName;

        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bitmap);
        view.draw(c);
        c.drawBitmap(bitmap, 0, 0, null);

        OutputStream fout = null;
        File imageFile = new File(mPath);
        try {
            fout = new FileOutputStream(imageFile);

            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fout);//set image quality and formate as you required.
            fout.flush();
            fout.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageFile.getAbsolutePath();
    }
}
