package com.zibilal.newsimpleloader.app.model;

import com.zibilal.consumeapi.lib.network.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bmuhamm on 4/22/14.
 */
public class DetikNewsListJSONResponse implements Response {

    List<NewsItem2> item;

    public DetikNewsListJSONResponse() {
        item = new ArrayList<NewsItem2>();
    }

    public List<NewsItem2> getItem() {
        return item;
    }

    public void setItem(List<NewsItem2> item) {
        this.item = item;
    }

    @Override
    public Object responseData() {
        return item;
    }
}
