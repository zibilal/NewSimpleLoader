package com.zibilal.newsimpleloader.app.list.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.zibilal.newsimpleloader.app.R;

import org.apache.http.MethodNotSupportedException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bmuhamm on 3/29/14.
 */
public class DefaultBaseAdapter extends BaseAdapter {

    private BaseAdapterHelper mAdapterHelper;

    private List<ThumbnailedAdapterHelper.IDataAdapter> mData;

    public DefaultBaseAdapter(BaseAdapterHelper adapterHelper) {
        this(adapterHelper, null);
    }

    public DefaultBaseAdapter(BaseAdapterHelper adapterHelper,  List<ThumbnailedAdapterHelper.IDataAdapter> data) {
        mAdapterHelper = adapterHelper;
        if(data == null) {
            mData = new ArrayList<ThumbnailedAdapterHelper.IDataAdapter>();
        } else
            mData = data;
    }

    public void addData(ThumbnailedAdapterHelper.IDataAdapter data) {
        mData.add(data);
        notifyDataSetChanged();
    }

    public void addData(List<ThumbnailedAdapterHelper.IDataAdapter> data) {
        mData.addAll(data);
        notifyDataSetChanged();
    }

    public void clear(){
        mData.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = mAdapterHelper.displayView(position, convertView, parent, (ThumbnailedAdapterHelper.IDataAdapter) getItem(position));

        return convertView;
    }
}
