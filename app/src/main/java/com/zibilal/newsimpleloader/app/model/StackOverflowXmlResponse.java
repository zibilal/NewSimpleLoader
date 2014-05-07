package com.zibilal.newsimpleloader.app.model;

import android.util.Xml;

import com.zibilal.consumeapi.lib.xml.ResponseXml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bmuhamm on 4/14/14.
 */
public class StackOverflowXmlResponse extends DefaultResponseXml {
    private static final String ns = null;

    private List<Entry> mEntries = new ArrayList<Entry>();

    @Override
    public Object parse(InputStream in) throws XmlPullParserException, IOException {
        XmlPullParser parser = Xml.newPullParser();
        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
        parser.setInput(in, null);
        parser.nextTag();
        mEntries = readFeed(parser);
        return this;
    }

    private List <Entry> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException{


        parser.require(XmlPullParser.START_TAG, ns, "feed");
        while(parser.next() != XmlPullParser.END_TAG) {
            if(parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            String name = parser.getName();
            if(name.equals("entry")) {
                mEntries.add(readEntry(parser));
            } else {
                skip(parser);
            }
        }

        return mEntries;
    }

    private Entry readEntry (XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "entry");

        String title = null;
        String summary = null;
        String link = null;

        while(parser.next() != XmlPullParser.END_TAG) {
            if(parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            String name = parser.getName();
            if(name.equals("title")) {
                title = readTitle(parser);
            } else if (name.equals("summary")) {
                summary = readSummary(parser);
            } else if (name.equals("link")) {
                link = readLink(parser);
            } else {
                skip(parser);
            }
        }

        return new Entry(title, summary, link);
    }

    private String readTitle(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "title");
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "title");
        return title;
    }

    private String readSummary(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "summary");
        String summary = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "summary");
        return summary;
    }

    private String readLink(XmlPullParser parser) throws XmlPullParserException, IOException {
        String link="";

        parser.require(XmlPullParser.START_TAG, ns, "link");
        String tag = parser.getName();
        String relType = parser.getAttributeValue(null, "rel");

        if(tag.equals("link")) {
            if(relType.equals("alternate")) {
                link = parser.getAttributeValue(null, "href");
                parser.nextTag();
            }
        }

        parser.require(XmlPullParser.END_TAG, ns, "link");
        return link;
    }

    @Override
    public Object responseData() {
        return mEntries;
    }
}
