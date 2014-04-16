package com.zibilal.newsimpleloader.app.list.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zibilal.newsimpleloader.app.R;
import com.zibilal.newsimpleloader.app.model.DataHttpModel;
import com.zibilal.newsimpleloader.app.model.IPOI;
import com.zibilal.newsimpleloader.app.model.POIItem;

import java.lang.reflect.Method;

/**
 * Created by bmuhamm on 3/29/14.
 */
public class ThumbnailedAdapterHelper implements BaseAdapterHelper {

    private LayoutInflater mLayoutInlInflater;
    private Context mContext;
    private Class<? extends IDataAdapter> mClass;
    private int layoutStub;

    public static interface IDataAdapter {
        public void setDescription(String description);
        public String getDescrption();
        public void setTitle(String title);
        public String getTitle();
        public void setSubtitle(String subtitle);
        public String getSubtitle();
        public void setThumbnailUrl(String url);
        public String getThumbnailUrl();
    }

    public ThumbnailedAdapterHelper(Context context, Class<? extends IDataAdapter> clss, int layoutStub) {
        mContext = context;
        mLayoutInlInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mClass = clss;
    }

    @Override
    public void displayView(int position, View convertView, ViewGroup parent, IDataAdapter iDataAdapter) {
        ViewHolder holder;

        if(convertView==null){
            convertView = mLayoutInlInflater.inflate(layoutStub, parent, false);
            holder = new ViewHolder();
            holder.titleView=(TextView) convertView.findViewById(R.id.title);
            holder.subtitleView=(TextView) convertView.findViewById(R.id.subtitle);
            holder.descriptionView=(TextView) convertView.findViewById(R.id.description);
            holder.thumbnailView=(ImageView) convertView.findViewById(R.id.imageView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        setValue(holder, iDataAdapter);
    }

    static class ViewHolder{
        TextView titleView;
        TextView subtitleView;
        TextView descriptionView;
        ImageView thumbnailView;
    }

    private void setValue(ViewHolder viewHolder, IDataAdapter iDataAdapter) {

        Object temp = IPOI.getValue("getTitle", iDataAdapter);
        if(temp != null) {
            viewHolder.titleView.setText((String) temp);
        } else {
            viewHolder.titleView.setVisibility(View.GONE);
        }

        temp = IPOI.getValue("getSubtitle", iDataAdapter);
        if(temp != null) {
            viewHolder.subtitleView.setText((String) temp);
        } else {
            viewHolder.subtitleView.setVisibility(View.GONE);
        }

        temp = IPOI.getValue("getDescription", iDataAdapter);
        if(temp != null) {
            viewHolder.descriptionView.setText((String) temp);
        } else {
            viewHolder.descriptionView.setVisibility(View.GONE);
        }

        temp = IPOI.getValue("getThumbnailUrl", iDataAdapter);
        if(temp != null){

        } else {
            viewHolder.thumbnailView.setVisibility(View.GONE);
        }
    }
}
