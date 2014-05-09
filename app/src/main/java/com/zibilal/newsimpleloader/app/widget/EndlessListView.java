package com.zibilal.newsimpleloader.app.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.HeaderViewListAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.zibilal.consumeapi.lib.network.HttpClient;
import com.zibilal.consumeapi.lib.network.Response;
import com.zibilal.consumeapi.lib.rawbyte.ResponseRaw;
import com.zibilal.consumeapi.lib.worker.HttpAsyncTask;
import com.zibilal.newsimpleloader.app.R;
import com.zibilal.newsimpleloader.app.list.adapter.DefaultBaseAdapter;
import com.zibilal.newsimpleloader.app.list.adapter.ThumbnailedAdapterHelper;

import java.util.List;

/**
 * Created by bmuhamm on 4/22/14.
 */
public class EndlessListView extends ListView implements AbsListView.OnScrollListener{

    private View mFooter;
    private View mHeader;
    private boolean isLoading;
    private EndlessListener mListener;

    public EndlessListView(Context context, AttributeSet attributeSet, int defStyle) {

        super(context, attributeSet, defStyle);
        this.setOnScrollListener(this);
        isLoading=false;
    }

    public EndlessListView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.setOnScrollListener(this);
        isLoading=  false;
    }

    public EndlessListView(Context context) {
        super(context);
        this.setOnScrollListener(this);
        isLoading=false;
    }

    public void setListener(EndlessListener listener) {
        mListener=listener;
    }

    public void setLoadingView(int resId) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mFooter = inflater.inflate(resId, this, false);
        addFooterView(mFooter);
    }

    public void setHeaderView(int resId, ThumbnailedAdapterHelper.IDataAdapter model) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mHeader = inflater.inflate(resId, this, false);
        TextView headerTitle = (TextView) mHeader.findViewById(R.id.title_header);
        TextView headerSubtitle = (TextView) mHeader.findViewById(R.id.subtitle_header);
        TextView headerDescription = (TextView) mHeader.findViewById(R.id.description_header);
        final ImageView headerThumbnails = (ImageView) mHeader.findViewById(R.id.thumbnail_header);

        headerTitle.setText(model.getTitle());
        headerSubtitle.setText(model.getSubtitle());
        headerDescription.setText(model.getDescription());
        HttpAsyncTask worker = new HttpAsyncTask(new HttpAsyncTask.OnPostExecute() {
            @Override
            public void onProgress(Integer i) {

            }

            @Override
            public void onUpdate(Response response) {
                try {
                    ResponseRaw raw = (ResponseRaw) response;
                    byte[] rawData = (byte[]) raw.responseData();
                    Bitmap bitmap = BitmapFactory.decodeByteArray(rawData, 0, rawData.length);
                    headerThumbnails.setImageBitmap(bitmap);
                } catch (Exception ex) {
                    Log.e("EndlessListView", "Exception occured while trying to load a thumbnail");
                }
            }
        }, ResponseRaw.class, false, null, null);
        worker.execute(model.getThumbnailUrl(), HttpClient.BYTE_TYPE);
        addHeaderView(mHeader);
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        if(getAdapter() == null) {
            return;
        } else if(getAdapter().getCount() == 0) {
            return;
        } else {
            int l = visibleItemCount + firstVisibleItem;
            if (l >= totalItemCount && !isLoading && l > 1) {
                addFooterView(mFooter);
                isLoading=true;
                mListener.loadData();
            }
        }

    }

    public void addNewData(List<ThumbnailedAdapterHelper.IDataAdapter> data) {

        DefaultBaseAdapter adapter;
        if(getFooterViewsCount() > 0) {
            HeaderViewListAdapter headerViewListAdapter = (HeaderViewListAdapter) getAdapter();
            adapter = (DefaultBaseAdapter) headerViewListAdapter.getWrappedAdapter();

        } else {
            adapter = (DefaultBaseAdapter) getAdapter();
        }
        adapter.addData(data);
        isLoading=false;
        removeFooterView(mFooter);
    }

    public static interface EndlessListener {
        public void loadData();
    }
}
