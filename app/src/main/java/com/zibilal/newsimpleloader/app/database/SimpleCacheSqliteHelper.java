package com.zibilal.newsimpleloader.app.database;

import android.content.Context;
import android.util.Log;

import com.zibilal.consumeapi.lib.persistence.CacheObject;
import com.zibilal.consumeapi.lib.persistence.CacheObjectHelper;
import com.zibilal.consumeapi.lib.persistence.CacheSqliteHelper;
import com.zibilal.newsimpleloader.app.model.NewsItem2;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by bmuhamm on 5/7/14.
 */
public class SimpleCacheSqliteHelper extends CacheSqliteHelper {

    private static String TAG="SimpleCacheSqliteHelper";

    private String mDatabaseName;
    private int mDatabaseVersion;
    private String mTableName;
    private Class<? extends CacheObject> mClass;

    private HashMap<String, String> mColumns;

    public SimpleCacheSqliteHelper(Context context, Class<? extends CacheObject> clss) {
        this(context, "latestnews.db", 14, clss);
    }

    public SimpleCacheSqliteHelper(Context context, String databaseName, int databaseVersion, Class<? extends CacheObject> clss) {
        super(context, databaseName, databaseVersion);
        mClass=clss;
    }

    public String getDatabaseName(){
        return mDatabaseName;
    }

    public void setDatabaseName(String databaseName){
        mDatabaseName=databaseName;
    }

    public int getDatabaseVersion(){
        return mDatabaseVersion;
    }

    public void setDatabaseVersion(int databaseVersion){
        mDatabaseVersion=databaseVersion;
    }

    @Override
    public String getCreateQuery() {
        try {
            String str = String.format("create table %s (_id integer primary key autoincrement, ", getTableName());

            for(Map.Entry<String, String> entry: mColumns.entrySet()) {
                String s = String.format("%s %s, ", entry.getKey(), entry.getValue());
                str += s;
            }
            // remove the last comma
            str = str.substring(0, str.length()-2);

            str += " )";

            return str;
        } catch(Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return null;
    }

    @Override
    public void setColumns(HashMap<String, String> columns) {
        mColumns=columns;
    }

    @Override
    public String getDropQuery() {
        return String.format("drop table if exists %s", getTableName());
    }

    @Override
    public String getTableName() {
        return "latestnews";
    }

    @Override
    public Class<? extends CacheObject> getCacheClass() {
        return mClass;
    }

    public void setTableName(String tableName){
        mTableName=tableName;
    }
}
