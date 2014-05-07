package com.zibilal.consumeapi.lib.persistence;

import android.content.Context;
import android.content.res.Resources;
import android.os.Environment;

import com.zibilal.consumeapi.lib.R;

import java.io.File;

/**
 * Created by bmuhamm on 4/3/14.
 */
public class FileCache {

    private File cacheDir;
    private Context mContext;

    public FileCache(Context context) {

        mContext = context;
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Resources resources = mContext.getResources();
            /*
            String defaultDir = resources.getString(R.string.default_images_cache_dir, "images");
            cacheDir = new File(Environment.getExternalStorageDirectory(), defaultDir); */
        } else {
            cacheDir = mContext.getCacheDir();
        }

        if(!cacheDir.exists())
            cacheDir.mkdirs();

    }

    public File getFile(String url) {
        String filename = url.substring(url.lastIndexOf("/") + 1, url.length());
        return new File(cacheDir, filename);
    }

    public void clear(){
        File[] files = cacheDir.listFiles();
        if(files == null) return;

        for(File f: files)
            f.delete();
    }
}
