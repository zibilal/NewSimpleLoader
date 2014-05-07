package com.zibilal.consumeapi.lib.xml;

import android.util.Log;

import com.zibilal.consumeapi.lib.network.Response;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by bmuhamm on 4/15/14.
 */
public class XmlParser {

    private static final String TAG="XmlParser";

    public static Object parse(Class<? extends Response> clss, InputStream input) throws XmlPullParserException, IOException{
        try {
            ResponseXml rxml = (ResponseXml) clss.newInstance();

            return rxml.parse(input);
        } catch (InstantiationException e) {
            Log.e(TAG, "Exception : " + e.getMessage());
        } catch (IllegalAccessException e) {
            Log.e(TAG, "Exception : " + e.getMessage());
        } finally {
            input.close();
        }

        return null;
    }

}
