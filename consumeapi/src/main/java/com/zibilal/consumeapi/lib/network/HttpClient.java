package com.zibilal.consumeapi.lib.network;

import android.util.Base64;

import com.google.gson.Gson;
import com.zibilal.consumeapi.lib.persistence.CacheObject;
import com.zibilal.consumeapi.lib.persistence.CacheObjectHelper;
import com.zibilal.consumeapi.lib.rawbyte.RawParser;
import com.zibilal.consumeapi.lib.xml.XmlParser;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by bmuhamm on 4/2/14.
 */
public class HttpClient {

    private static final String TAG="HttpClient";

    public static final String JSON_TYPE="json";
    public static final String XML_TYPE="xml";
    public static final String BYTE_TYPE="byte";

    private static final String DEFAULT_USER="detik";
    private static final String DEFAULT_PASSWORD="d3t1kc0m";

    private String mUser;
    private String mPassword;

    private boolean mNeedAuthentication;
    private boolean mCached;

    private CacheObjectHelper mCacheObjectHelper;
    private String mKeyword;

    public HttpClient(){
        mNeedAuthentication=false;
    }

    public HttpClient(boolean needAuthentication) {
        mNeedAuthentication=needAuthentication;
    }

    public void setCacheProtocol(boolean isCached,
                                 CacheObjectHelper cacheObjectHelper, String keyword) {
        mCached=isCached;
        mCacheObjectHelper=cacheObjectHelper;
        mKeyword=keyword;
    }

    public void initRequestProperty(HttpURLConnection conn) {
        if(mNeedAuthentication) {

            if(mUser == null || mPassword == null) {
                mUser = DEFAULT_USER;
                mPassword = DEFAULT_PASSWORD;
            }

            conn.setRequestProperty(HttpDefault.HEADER_AUTHENTICATION, getBasicAuthentication(mUser, mPassword));
        }
    }

    private Response getFromCache() {
        final List<CacheObject> list = mCacheObjectHelper.getCache(mKeyword);

        if(list!=null && list.size() > 0) {

            Response response = new Response() {
                @Override
                public Object responseData() {
                    return list;
                }
            };

            return response;

        } else {
            return null;
        }
    }

    public Object getJson(String url, Class < ? extends Response> clss) throws Exception{
        if(mCached) {
            Response response = getFromCache();
            if(response== null) {
                HttpURLConnection conn = (HttpURLConnection) (new URL(url).openConnection());
                initRequestProperty(conn);
                int responseCode = conn.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = new BufferedInputStream(conn.getInputStream());
                    Reader reader = new InputStreamReader(inputStream);
                    Gson gson = new Gson();
                    response = gson.fromJson(reader, clss);
                    mCacheObjectHelper.addCache(mKeyword,  response);
                } else {
                    throw new Exception(conn.getResponseMessage());
                }
            }

            return response;
        } else {
            HttpURLConnection conn = (HttpURLConnection) (new URL(url).openConnection());
            initRequestProperty(conn);
            int responseCode = conn.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = new BufferedInputStream(conn.getInputStream());
                Reader reader = new InputStreamReader(inputStream);
                Gson gson = new Gson();
                Response response = gson.fromJson(reader, clss);
                return response;
            } else {
                throw new Exception(conn.getResponseMessage());
            }
        }
    }

    public String getBasicAuthentication(String user, String pass) {
        String creds = String.format("%s:%s", user, pass);
        return "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
    }

    public Object getXml(String url, Class < ? extends Response> clss) throws Exception {

        HttpURLConnection conn = (HttpURLConnection) (new URL(url).openConnection());
        initRequestProperty(conn);
        int responseCode = conn.getResponseCode();
        if(responseCode == HttpURLConnection.HTTP_OK) {
            InputStream inputStream = new BufferedInputStream(conn.getInputStream());
            Object response = XmlParser.parse(clss, inputStream);
            return response;
        } else {
            throw new Exception(conn.getResponseMessage());
        }
    }

    public Object getBytes(String url, Class < ? extends Response> clss) throws Exception {
        InputStream inputStream = ((new URL(url)).openConnection()).getInputStream();
        Object response = RawParser.parse(clss, inputStream);

        return response;
    }


}
