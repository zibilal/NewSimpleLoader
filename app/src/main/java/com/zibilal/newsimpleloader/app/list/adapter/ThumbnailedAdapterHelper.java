package com.zibilal.newsimpleloader.app.list.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zibilal.consumeapi.lib.network.HttpClient;
import com.zibilal.consumeapi.lib.network.Response;
import com.zibilal.consumeapi.lib.rawbyte.ResponseRaw;
import com.zibilal.consumeapi.lib.worker.HttpAsyncTask;
import com.zibilal.newsimpleloader.app.R;
import com.zibilal.newsimpleloader.app.model.IPOI;

/**
 * Created by bmuhamm on 3/29/14.
 */
public class ThumbnailedAdapterHelper implements BaseAdapterHelper {

    private static final String TAG="ThumbnailedAdapterHelper";

    private LayoutInflater mLayoutInlInflater;
    private Context mContext;
    private Class<? extends IDataAdapter> mClass;
    private int mLayoutStub;
    private int mLayoutHeaderStub;
    private int[] mLayoutStubIds={R.id.title, R.id.subtitle, R.id.description, R.id.thumbnail};

    private boolean mHeaderDisplayed;

    private static final int VIEW_HOLDER_LENGTH=4;

    public static interface IDataAdapter {
        public void setDescription(String description);
        public String getDescription();
        public void setTitle(String title);
        public String getTitle();
        public void setSubtitle(String subtitle);
        public String getSubtitle();
        public void setThumbnailUrl(String url);
        public String getThumbnailUrl();
    }

    public ThumbnailedAdapterHelper(Context context, Class<? extends IDataAdapter> clss) {
        mContext = context;
        mLayoutInlInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mClass = clss;
        mLayoutStub = R.layout.item_http_base_adapter;
        mLayoutHeaderStub=R.layout.item_header;
        mHeaderDisplayed=false;
    }

    @Override
    public View displayView(int position, View convertView, ViewGroup parent, IDataAdapter iDataAdapter) {

        if(mLayoutStubIds.length != VIEW_HOLDER_LENGTH)
            throw new IllegalArgumentException("Layout stubs must be an array of integer with length of " + VIEW_HOLDER_LENGTH);

        ViewHolder holder;

        Log.d(TAG, String.format("Position %d", position));
        Log.d(TAG, String.format("Position %b", mHeaderDisplayed));

        if(convertView == null) {
            convertView = mLayoutInlInflater.inflate(mLayoutStub, parent, false);

            holder = new ViewHolder();
                holder.titleView = (TextView) convertView.findViewById(mLayoutStubIds[0]);
                holder.subtitleView = (TextView) convertView.findViewById(mLayoutStubIds[1]);
                holder.descriptionView = (TextView) convertView.findViewById(mLayoutStubIds[2]);
                holder.thumbnailView = (ImageView) convertView.findViewById(mLayoutStubIds[3]);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        setValue(holder, iDataAdapter);

        return convertView;
    }

    static class ViewHolder{
        TextView titleView;
        TextView subtitleView;
        TextView descriptionView;
        ImageView thumbnailView;
    }

    private void setValue(ViewHolder viewHolder, IDataAdapter iDataAdapter) {

        if(!mHeaderDisplayed)
            mHeaderDisplayed=true;

        Object temp = IPOI.getValue("getTitle", iDataAdapter);
        if(viewHolder.titleView != null) {
            if (temp != null ) {
                viewHolder.titleView.setText((String) temp);
            } else {
                viewHolder.titleView.setVisibility(View.GONE);
            }
        }

        temp = IPOI.getValue("getSubtitle", iDataAdapter);
        if(viewHolder.subtitleView != null) {
            if (temp != null) {
                viewHolder.subtitleView.setText((String) temp);
            } else {
                viewHolder.subtitleView.setVisibility(View.GONE);
            }
        }

        temp = IPOI.getValue("getDescription", iDataAdapter);
        if(viewHolder.descriptionView != null) {
            if (temp != null) {
                viewHolder.descriptionView.setText((String) temp);
            } else {
                viewHolder.descriptionView.setVisibility(View.GONE);
            }
        }

        temp = IPOI.getValue("getThumbnailUrl", iDataAdapter);
        if(viewHolder.thumbnailView != null) {
            if (temp != null) {
                final ImageView imageView = viewHolder.thumbnailView;
                HttpAsyncTask httpAsyncTask = new HttpAsyncTask(
                        new HttpAsyncTask.OnPostExecute() {
                            @Override
                            public void onProgress(Integer i) {

                            }

                            @Override
                            public void onUpdate(Response response) {
                                try {
                                    ResponseRaw raw = (ResponseRaw) response;
                                    byte[] rawData = (byte[]) raw.responseData();
                                    Bitmap bitmap = BitmapFactory.decodeByteArray(rawData, 0, rawData.length);
                                    imageView.setImageBitmap(bitmap);
                                } catch (Exception ex) {
                                    Log.e(TAG, "Exception occured while trying to load a thumbnail");
                                }
                            }
                        }, ResponseRaw.class,
                        false,
                        null,
                        null
                );
                String stemp = (String) temp;
                httpAsyncTask.execute(stemp, HttpClient.BYTE_TYPE);
            }
        }
    }
}
