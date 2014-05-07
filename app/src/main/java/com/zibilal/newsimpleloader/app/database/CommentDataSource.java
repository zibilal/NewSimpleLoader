package com.zibilal.newsimpleloader.app.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bmuhamm on 5/6/14.
 */
public class CommentDataSource {

    private SQLiteDatabase mDatabase;
    private MySqliteHelper mDbHelper;
    private String[] allColumns = {MySqliteHelper.COLUMN_ID , MySqliteHelper.COLUMN_COMMENT};

    public CommentDataSource(Context context) {
        mDbHelper = new MySqliteHelper(context);
    }

    public void open() throws Exception {
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public void close() {
        mDbHelper.close();
    }

    public Comment createComment(String comment) {
        ContentValues values = new ContentValues();
        values.put(MySqliteHelper.COLUMN_COMMENT, comment);
        long insertid = mDatabase.insert(MySqliteHelper.TABLE_COMMENTS, null, values);

        Cursor cursor = mDatabase.query(MySqliteHelper.TABLE_COMMENTS, allColumns, MySqliteHelper.COLUMN_ID+"="+insertid,
                null, null, null, null);
        cursor.moveToFirst();

        Comment newComment = cursorToComment(cursor);
        cursor.close();
        return newComment;
    }

    public void deleteComment(Comment comment) {
        long id = comment.getId();
        mDatabase.delete(MySqliteHelper.TABLE_COMMENTS, MySqliteHelper.COLUMN_ID+"="+id, null);
    }

    public List<Comment> getAllComments() {
        List<Comment> comments = new ArrayList<Comment>();
        Cursor cursor = mDatabase.query(MySqliteHelper.TABLE_COMMENTS, allColumns,
                null, null, null, null, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Comment comment = cursorToComment(cursor);
            comments.add(comment);
            cursor.moveToNext();
        }
        cursor.close();
        return comments;
    }

    private Comment cursorToComment(Cursor cursor) {
        Comment comment = new Comment();
        comment.setId(cursor.getLong(0));
        comment.setComment(cursor.getString(1));
        return comment;
    }
}
