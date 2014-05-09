package com.zibilal.newsimpleloader.app.database;

import android.database.Cursor;
import android.util.Log;

import com.zibilal.consumeapi.lib.network.Response;
import com.zibilal.consumeapi.lib.persistence.CacheObject;
import com.zibilal.consumeapi.lib.persistence.CacheObjectHelper;
import com.zibilal.consumeapi.lib.persistence.CacheSqliteHelper;
import com.zibilal.newsimpleloader.app.model.DetikNewsListJSONResponse;
import com.zibilal.newsimpleloader.app.model.NewsItem2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by bmuhamm on 5/8/14.
 */
public class SimpleCacheObjectHelper extends CacheObjectHelper {

    private static final String TAG="SimpleCacheObjectHelper";
    public static final String COLUMN_KEYWORD="keyword";

    public SimpleCacheObjectHelper(CacheSqliteHelper helper) {
        super(helper);
    }

    @Override
    public List<CacheObject> getCache(String keyword) {
        try {
            HashMap<String, String> map = allColumns(getHelper().getCacheClass());
            String[] allcolumns = new String[map.size()];
            int index=0;
            for(String str : map.keySet()) {
                allcolumns[index] = str;
                index++;
            }
            Cursor cursor = getDatabase().query( getHelper().getTableName(), allcolumns
                    , String.format("%s='%s'", getKeywordColumn(), keyword),
                    null, null, null, null);
            cursor.moveToFirst();
            List<CacheObject> caches = new ArrayList<CacheObject>();
            while(!cursor.isAfterLast()) {
                CacheObject obj = cursorToCacheObject(cursor, getHelper().getCacheClass());
                caches.add(obj);
                cursor.moveToNext();
            }

            cursor.close();

            return caches;
        } catch (Exception e) {
            e.printStackTrace();
            //Log.e(TAG, e);
        }
        return null;
    }

    @Override
    public int addCache(String keyword, Response response) {

        DetikNewsListJSONResponse detikLatestRespons = (DetikNewsListJSONResponse) response;

        try {
            List<NewsItem2> latestList = detikLatestRespons.getItem();
            int lendata = 0;
            for (NewsItem2 item : latestList) {
                saveObject(item, keyword);
                lendata++;
            }
            return lendata;
        } catch(Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return 0;
    }

    @Override
    public String getKeywordColumn() {
        return COLUMN_KEYWORD;
    }
}
