package com.dvpie.utilities;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

import java.io.InputStream;
import java.net.URL;

public class ImageTools {
    public Drawable loadImageFromWebOperations(String url,@NonNull String srcName) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is,  srcName);
            return d;
        } catch(Exception e) {
            return null;
        }
    }

}
