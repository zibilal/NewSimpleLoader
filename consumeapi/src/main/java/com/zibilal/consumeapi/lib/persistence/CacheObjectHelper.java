package com.zibilal.consumeapi.lib.persistence;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import com.zibilal.consumeapi.lib.network.Response;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

/**
 * Created by bmuhamm on 5/7/14.
 */
public abstract class CacheObjectHelper {

    private CacheSqliteHelper mSqlHelper;
    private SQLiteDatabase mDatabase;

    public CacheObjectHelper(CacheSqliteHelper helper){
        mSqlHelper=helper;
    }

    public void open(){
        mDatabase=mSqlHelper.getWritableDatabase();
    }

    public void close(){
        mSqlHelper.close();
    }

    public abstract List<CacheObject> getCache(String keyword);

    public abstract int addCache(String keyword, Response response, Class<? extends Response> type);

    public  String[] allColumns(CacheObject object) throws Exception {

        Class<? extends CacheObject> clss = object.getClass();
        Field[] fields = clss.getDeclaredFields();

        List<String> columns = new ArrayList<String>();

        for(Field field : fields) {
            ColumnCache columnCache = field.getAnnotation(ColumnCache.class);
            if(columnCache != null) {
                String sname = columnCache.columName();
                columns.add(sname);
            } else {
                columns.add(field.getName());
            }
        }

        if(columns.size() > 0) {

            String[] allColumns = columns.toArray(new String[columns.size()]);
            return allColumns;
        } else
            return null;
    }

    public  Field getFieldByName(String name, CacheObject object) {
        Class<? extends  CacheObject> clss = object.getClass();
        Field[] fields = clss.getDeclaredFields();
        for(Field f : fields) {
            String str = f.getName().toLowerCase();
            if(name.toLowerCase().equals(str))
                return f;
        }

        return null;
    }

    public  long saveObject(CacheObject object) throws Exception{
        ContentValues values = new ContentValues();
        Class<? extends  CacheObject> clss = object.getClass();
        Method[] methods = clss.getDeclaredMethods();

        for(Method m : methods) {
            if(m.getName().startsWith("get")) {
                Object o = m.invoke(object, null);
                String fieldName = m.getName().substring("get".length(), m.getName().length());
                Field f = getFieldByName(fieldName, object);
                ColumnCache columnCache = f.getAnnotation(ColumnCache.class);
                String key;
                if(columnCache != null) {
                    key = columnCache.columName();
                } else {
                    key = f.getName();
                }

                if(o instanceof Integer) {
                    values.put(key, (Integer) o);
                } else if(o instanceof String) {
                    values.put(key, (String) o);
                } else if(o instanceof Long) {
                    values.put(key, (Long) o);
                }
            }
        }
        SQLiteDatabase db = mSqlHelper.getWritableDatabase();
        long insertid = db.insert(mSqlHelper.getTableName(), null, values);
        return insertid;
    }

    private  void setValue(String columnName, CacheObject cObj, Object value) throws Exception {
        Class<? extends CacheObject> clss = cObj.getClass();
        Field[] fields=clss.getDeclaredFields();

        Field selectedField = null;

        for(Field field: fields) {
            ColumnCache columnCache = field.getAnnotation(ColumnCache.class);
            String name;
            if(columnCache!=null) {
                name = columnCache.columName();
            } else {
                name = field.getName();
            }
            if(name != null && name.equals(columnName)) {
                selectedField = field;
                break;
            }
        }

        if(selectedField != null) {
            selectedField.set(cObj, value);
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public  CacheObject cursorToCacheObject(Cursor cursor, Class<? extends CacheObject> clss) throws Exception {
        int collen = cursor.getColumnCount();

        CacheObject cObj = clss.newInstance();

        for(int i=0; i < collen; i++) {
            String colName = cursor.getColumnName(i);
            int type = cursor.getType(i);
            Object obj;
            switch (type){
                case Cursor.FIELD_TYPE_INTEGER:
                    obj = cursor.getInt(i);
                    break;
                case Cursor.FIELD_TYPE_FLOAT:
                    obj = cursor.getFloat(i);
                    break;
                case Cursor.FIELD_TYPE_STRING:
                    obj = cursor.getString(i);
                    break;
                default:
                    throw new Exception("Wrong cursor type...");
            }

            setValue(colName, cObj, obj);

        }

        return cObj;
    }
}
