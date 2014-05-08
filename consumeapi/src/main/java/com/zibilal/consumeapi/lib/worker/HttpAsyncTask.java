package com.zibilal.consumeapi.lib.worker;

import android.os.AsyncTask;
import android.util.Log;

import com.zibilal.consumeapi.lib.network.HttpClient;
import com.zibilal.consumeapi.lib.network.Response;
import com.zibilal.consumeapi.lib.persistence.CacheObject;
import com.zibilal.consumeapi.lib.persistence.CacheObjectHelper;

import java.util.List;


/**
 * Created by bmuhamm on 3/30/14.
 */
public class HttpAsyncTask extends AsyncTask<String, Integer, Response> {

    public static final String TAG="HttpAsyncTask";
    private OnPostExecute callback;
    private Class<? extends Response> mResponseClass;

    private boolean mNeedAuthentication;

    private CacheObjectHelper mCacheObjectHelper;
    private String mKeyword;

    public HttpAsyncTask(OnPostExecute postExecute, Class<? extends Response> responseClass, boolean needAuthentication, CacheObjectHelper cacheObjectHelper, String keyword){
        callback = postExecute;
        mResponseClass = responseClass;
        mNeedAuthentication = needAuthentication;
        mCacheObjectHelper=cacheObjectHelper;
        mKeyword=keyword;
    }

    @Override
    protected Response doInBackground(String... strings) {
        String url;
        String type;
        if(strings.length > 1) {
            url = strings[0];
            type = strings[1];
        } else {
            url = strings[0];
            type = HttpClient.JSON_TYPE;
        }

        boolean loadFromHttp;

        if(mCacheObjectHelper != null && mKeyword != null) {

            final List<CacheObject> list = mCacheObjectHelper.getCache(mKeyword);

            if(list!=null && list.size() > 0) {
                loadFromHttp=false;

                Response response = new Response() {
                    @Override
                    public Object responseData() {
                        return list;
                    }
                };

                return response;

            } else {
                loadFromHttp=true;
            }

        } else {
            loadFromHttp=true;
        }

        if(loadFromHttp) {
            try {
                HttpClient httpClient = new HttpClient(mNeedAuthentication);
                Response response;
                if (type.equals(HttpClient.XML_TYPE)) {
                    response = (Response) httpClient.getXml(url, mResponseClass);
                } else if (type.equals(HttpClient.BYTE_TYPE)) {
                    response = (Response) httpClient.getBytes(url, mResponseClass);
                } else {
                    response = (Response) httpClient.getJson(url, mResponseClass);
                }
                if(mCacheObjectHelper != null && mKeyword != null) {
                    mCacheObjectHelper.addCache(mKeyword, response);
                }
                return response;

            } catch (Exception e) {
                Log.e(TAG, "Exception occured: " + e.getMessage());
            }
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        callback.onProgress(values[0]);
    }

    @Override
    protected void onPostExecute(Response response) {
        callback.onUpdate(response);
    }

    public static interface OnPostExecute {
        public void onProgress(Integer i);
        public void onUpdate(Response response);
    }
}
