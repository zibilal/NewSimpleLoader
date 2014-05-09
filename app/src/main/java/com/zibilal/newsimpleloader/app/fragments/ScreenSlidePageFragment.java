package com.zibilal.newsimpleloader.app.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zibilal.consumeapi.lib.network.HttpClient;
import com.zibilal.consumeapi.lib.network.Response;
import com.zibilal.consumeapi.lib.rawbyte.ResponseRaw;
import com.zibilal.consumeapi.lib.worker.HttpAsyncTask;
import com.zibilal.newsimpleloader.app.R;
import com.zibilal.newsimpleloader.app.model.DNPhotoDetail;
import com.zibilal.newsimpleloader.app.widget.TouchImageView;

/**
 * Created by bmuhamm on 4/28/14.
 */
public class ScreenSlidePageFragment extends Fragment {

    public static final String ARG_DATA="data";
    private DNPhotoDetail mDetail;
    private TouchImageView mPhotoView;
    private byte[] rawByte;

    public static ScreenSlidePageFragment create( DNPhotoDetail detail) {
        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_DATA, detail);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDetail =getArguments().getParcelable(ARG_DATA);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.cell_photo_view, container, false);

        TextView resumeView = (TextView) rootView.findViewById(R.id.text_description);
        resumeView.setText(mDetail.getDescription());

        mPhotoView = (TouchImageView) rootView.findViewById(R.id.photo_view);
        final ProgressBar photoProgressBar = (ProgressBar) rootView.findViewById(R.id.photo_progressbar);

        HttpAsyncTask httpAsyncTask = new HttpAsyncTask(new HttpAsyncTask.OnPostExecute() {
            @Override
            public void onProgress(Integer i) {

            }

            @Override
            public void onUpdate(Response response) {
                ResponseRaw raw = (ResponseRaw) response;
                rawByte = (byte[]) raw.responseData();
                Bitmap bitmap = BitmapFactory.decodeByteArray(rawByte, 0, rawByte.length);
                mPhotoView.setImageBitmap(bitmap);
                mPhotoView.setVisibility(View.VISIBLE);
                photoProgressBar.setVisibility(View.GONE);
            }
        }, ResponseRaw.class, false, null, null);
        httpAsyncTask.execute(mDetail.getStory(), HttpClient.BYTE_TYPE);

        return rootView;
    }

    public ScreenSlidePageFragment(){}
}
