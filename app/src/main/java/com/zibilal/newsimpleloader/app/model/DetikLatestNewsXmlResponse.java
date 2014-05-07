package com.zibilal.newsimpleloader.app.model;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by bmuhamm on 4/21/14.
 */
public class DetikLatestNewsXmlResponse extends DefaultResponseXml {

    private static final String ns = null;

    private List<NewsItem> mItems;

    public DetikLatestNewsXmlResponse() {
        mItems = new ArrayList<NewsItem>();
    }

    @Override
    public Object parse(InputStream in) throws XmlPullParserException, IOException {
        XmlPullParser parser = Xml.newPullParser();
        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
        parser.setInput(in, null);
        parser.nextTag();
        mItems = readItems(parser);
        return this;
    }

    @Override
    public Object responseData() {
        return mItems;
    }

    private List<NewsItem> readItems(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "items");
        while(parser.next() != XmlPullParser.END_TAG) {
            if(parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            String name = parser.getName();
            if(name.equals("item")) {
                mItems.add(readItem(parser));
            } else {
                skip(parser);
            }
        }

        return mItems;
    }

    private NewsItem readItem(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "item");

        String title=null;
        String subtitle=null;
        String link=null;
        String guid=null;
        Date pubDate=null;
        String reporter=null;
        String kanal=null;
        String penulis=null;
        String editor=null;
        String description=null;
        NewsItem.Enclosure enclosure=null;

        while(parser.next() != XmlPullParser.END_TAG) {
            if(parser.getEventType() != XmlPullParser.START_TAG){
                continue;
            }

            String name=parser.getName();
            if(name.equals("title")){
                title=readTitle(parser);
            } else if(name.equals("subtitle")){
                subtitle = readSubtitle(parser);
            } else if(name.equals("link")) {
                link = readLink(parser);
            } else if(name.equals("guid")) {
                guid = readGuid(parser);
            } else if(name.equals("pubDate")){
                pubDate = readPubDate(parser);
            } else if(name.equals("reporter")){
                reporter = readReporter(parser);
            } else if(name.equals("kanal")){
                kanal = readKanal(parser);
            } else if(name.equals("penulis")){
                penulis = readPenulis(parser);
            } else if(name.equals("editor")){
                editor = readEditor(parser);
            } else if(name.equals("description")) {
                description = readDescription(parser);
            } else if(name.equals("enclosure")) {
                enclosure = readEnclosure(parser);
            } else {
                skip(parser);
            }
        }

        return new NewsItem(title, subtitle, link, guid, pubDate, reporter, kanal,
                penulis, editor, description, enclosure);
    }

    private String readTitle(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "title");
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "title");
        return title;
    }

    private String readSubtitle(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "subtitle");
        String subtitle = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "subtitle");
        return subtitle;
    }

    private String readLink(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "link");
        String link = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "link");
        return link;
    }

    private String readGuid(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "guid");
        String guid = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "guid");
        return guid;
    }

    private Date readPubDate(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "pubDate");
        Date date=null;
        try{
            String sdate = readText(parser);
            date = (new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")).parse(sdate);
        } catch (Exception e) { }
        parser.require(XmlPullParser.END_TAG, ns, "pubDate");
        return date;
    }

    private String readReporter(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "reporter");
        String reporter = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "reporter");
        return reporter;
    }

    private String readKanal(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "kanal");
        String kanal = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "kanal");
        return kanal;
    }

    private String readPenulis(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "penulis");
        String penulis=readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "penulis");
        return penulis;
    }

    private String readEditor(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "editor");
        String editor=readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "editor");
        return editor;
    }

    private String readDescription(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "description");
        String description = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "description");
        return description;
    }

    private NewsItem.Enclosure readEnclosure(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "enclosure");
        String tag = parser.getName();
        String captionAtt = parser.getAttributeValue(null, "caption");
        String urlAtt = parser.getAttributeValue(null, "url");
        NewsItem.Enclosure enclosure=null;
        if(tag.equals("enclosure")){
            enclosure = new NewsItem.Enclosure(captionAtt, urlAtt);
            parser.nextTag();
        }
        return enclosure;
    }
}
