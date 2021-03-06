package com.fitness.centrale.misc;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * Created by Psyycker on 17/11/2017.
 */

public class ImageUtility {

public static String bitmapToBase64(Bitmap bitmap){
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
    byte[] byteArray = byteArrayOutputStream .toByteArray();
    String encoded = "data:png;charset=utf-8;base64," +  Base64.encodeToString(byteArray, Base64.DEFAULT);
    return encoded;
}


public static Bitmap base64ToImage(String base64){
    String[] splited = base64.split(",");
    byte[] byteArray = Base64.decode(splited[splited.length - 1], Base64.DEFAULT);
    return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
}


}
