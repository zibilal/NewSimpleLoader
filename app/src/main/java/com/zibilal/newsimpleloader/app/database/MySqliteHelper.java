package com.zibilal.newsimpleloader.app.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by bmuhamm on 5/6/14.
 */
public class MySqliteHelper extends SQLiteOpenHelper {

    public static final String TABLE_COMMENTS="comments";
    public static final String COLUMN_ID="_id";
    public static final String COLUMN_COMMENT="comment";

    private static final String DATABASE_NAME="comments.db";
    private static final int DATABASE_VERSION=1;

    // Database creation
    private static final String DATABASE_CREATE=
            String.format("create table %s (%s integer primary key autoincrement, " +
                    "%s text not null)", TABLE_COMMENTS, COLUMN_ID, COLUMN_COMMENT);

    // Database delete
    private static final String DATABASE_DROP=
            String.format("drop table if exists %s", TABLE_COMMENTS);

    public MySqliteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        sqLiteDatabase.execSQL(DATABASE_DROP);
        onCreate(sqLiteDatabase);
    }
}
