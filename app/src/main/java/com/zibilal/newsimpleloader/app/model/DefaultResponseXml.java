package com.zibilal.newsimpleloader.app.model;

import com.zibilal.consumeapi.lib.xml.ResponseXml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by bmuhamm on 4/21/14.
 */
public abstract class DefaultResponseXml implements ResponseXml {

    public void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if(parser.getEventType() != XmlPullParser.START_TAG ) {
            throw new IllegalStateException();
        }

        int depth=1;

        while(depth!=0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

    public String readText(XmlPullParser parser) throws XmlPullParserException, IOException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }

        return result;
    }
}
