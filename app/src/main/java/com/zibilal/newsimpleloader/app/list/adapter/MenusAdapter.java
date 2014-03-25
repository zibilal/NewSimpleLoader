package com.zibilal.newsimpleloader.app.list.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zibilal.newsimpleloader.app.R;

import java.util.List;

/**
 * Created by bmuhamm on 3/21/14.
 */
public class MenusAdapter extends BaseAdapter {

    private List<String> mData;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public MenusAdapter(Context context, List<String> data){
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        mData = data;
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
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if(convertView==null) {
            convertView = mLayoutInflater.inflate(R.layout.item_menus, viewGroup, false);
            holder = new ViewHolder();
            holder.text = (TextView) convertView.findViewById(R.id.item_text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String item = (String) getItem(position);

        holder.text.setText(item);

        return convertView;
    }

    static class ViewHolder{
        TextView text;
    }
}
