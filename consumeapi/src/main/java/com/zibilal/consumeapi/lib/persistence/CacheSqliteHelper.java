package com.zibilal.consumeapi.lib.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;

/**
 * Created by bmuhamm on 5/7/14.
 */
public abstract class CacheSqliteHelper extends SQLiteOpenHelper {

    public abstract String getCreateQuery();
    public abstract void setColumns(HashMap<String, String> columns);
    public abstract String getDropQuery();
    public abstract String getTableName();
    public abstract Class<? extends CacheObject> getCacheClass();

    public CacheSqliteHelper(Context context, String databaseName, int databaseVersion) {
        super(context, databaseName,null, databaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createQuery = getCreateQuery();
        sqLiteDatabase.execSQL(getCreateQuery());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        sqLiteDatabase.execSQL(getDropQuery());
        onCreate(sqLiteDatabase);
    }
}
