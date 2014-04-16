package com.zibilal.newsimpleloader.app.model;

import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by bmuhamm on 4/2/14.
 */
public class IPOI {
    private static String TAG="IPOI";

    public static Object getValue(String methodName, Object obj) {
        Class clss = obj.getClass();
        try {
            Method method = clss.getMethod(methodName);
            return method.invoke(obj, null);
        } catch (NoSuchMethodException e) {
            Log.e(TAG, "[GetValue] Exception occured : " + e.getMessage());
        } catch (IllegalAccessException e) {
            Log.e(TAG, "[GetValue] Exception occured : " + e.getMessage());
        } catch (InvocationTargetException e) {
            Log.e(TAG, "[GetValue] Exception occured : " + e.getMessage());
        }
        return null;
    }

    public static void setValue(String methodName, Object obj, Object value, Class<?> valueClass) {
        Class clss = obj.getClass();
        try {
            Method method = clss.getMethod(methodName, new Class[] {valueClass});
            method.invoke(obj, new Object[] {value});
        } catch (NoSuchMethodException e) {
            Log.e(TAG, "[SetValue] Exception occured : " + e.getMessage());
        } catch (IllegalAccessException e) {
            Log.e(TAG, "[SetValue] Exception occured : " + e.getMessage());
        } catch (InvocationTargetException e) {
            Log.e(TAG, "[SetValue] Exception occured : " + e.getMessage());
        }
    }
}
