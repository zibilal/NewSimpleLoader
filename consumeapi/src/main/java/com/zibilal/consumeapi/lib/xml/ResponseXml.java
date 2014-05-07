package com.zibilal.consumeapi.lib.xml;

import com.zibilal.consumeapi.lib.network.Response;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by bmuhamm on 4/15/14.
 */
public interface ResponseXml extends Response {
    Object parse(InputStream in) throws XmlPullParserException, IOException;
}
