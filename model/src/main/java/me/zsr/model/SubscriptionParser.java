package me.zsr.model;

import android.util.Xml;

import org.jsoup.Jsoup;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import me.zsr.bean.Article;
import me.zsr.bean.Subscription;
import me.zsr.common.DateUtil;
import me.zsr.common.StringUtil;
import me.zsr.common.XMLUtil;

// TODO: 10/29/16 optimize: fetch other useful tag
public class SubscriptionParser {
    private static final String RSS = "rss";
    private static final String ITEM = "item";
    private static final String CHANNEL = "channel";
    private static final String TITLE = "title";
    private static final String LINK = "link";
    private static final String PUB_DATE = "pubDate";
    private static final String DESC = "description";
    private static final String CONTENT = "content:encoded";
    private static final String LAST_BUILD_DATE = "lastBuildDate";

    public static Subscription parse(String xmlStr) {
        if (StringUtil.isNullOrEmpty(xmlStr)) {
            return null;
        }

        XmlPullParser parser = Xml.newPullParser();
        try {
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(new StringReader(xmlStr));
            parser.nextTag();

            // TODO: 2/22/17 verify source
            return readRssForSubscription(parser);
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Subscription readRssForSubscription(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, null, RSS);
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals(CHANNEL)) {
                return readChannelForSubscription(parser);
            } else {
                XMLUtil.skip(parser);
            }
        }
        return null;
    }

    private static Subscription readChannelForSubscription(XmlPullParser parser) throws XmlPullParserException, IOException {
        Subscription subscription = new Subscription();

        parser.require(XmlPullParser.START_TAG, null, CHANNEL);
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals(TITLE)) {
                subscription.setTitle(readTitle(parser));
            } else if (name.equals(LINK)) {
                subscription.setSiteUrl(readLink(parser));
            } else if (name.equals(LAST_BUILD_DATE)) {
                subscription.setTime(readLastBuildDate(parser));
            } else if (name.equals(DESC)) {
                subscription.setDesc(readDesc(parser));
            } else {
                XMLUtil.skip(parser);
            }
        }
        subscription.setKey("feed/" + subscription.getUrl());
        subscription.setCategory("");
        subscription.setSortid("");
        subscription.setAccountId(0L);
        subscription.setTotalCount(0L);
        subscription.setUnreadCount(0L);
        return subscription;
    }

    public static List<Article> parseArticle(String xmlStr) {
        if (StringUtil.isNullOrEmpty(xmlStr)) {
            return null;
        }

        XmlPullParser parser = Xml.newPullParser();
        try {
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(new StringReader(xmlStr));
            parser.nextTag();
            return readRssForArticle(parser);
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static List<Article> readRssForArticle(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, null, RSS);
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals(CHANNEL)) {
                return readChannelForArticle(parser);
            } else {
                XMLUtil.skip(parser);
            }
        }
        return null;
    }

    private static List<Article> readChannelForArticle(XmlPullParser parser) throws XmlPullParserException, IOException {
        List entries = new ArrayList();

        parser.require(XmlPullParser.START_TAG, null, CHANNEL);
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals(ITEM)) {
                entries.add(readItemForArticle(parser));
            } else {
                XMLUtil.skip(parser);
            }
        }
        return entries;
    }

    private static Article readItemForArticle(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, ITEM);
        String title = null;
        String link = null;
        String pubDate = null;
        String description = null;
        String content = null;
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals(TITLE)) {
                title = readTitle(parser);
            } else if (name.equals(LINK)) {
                link = readLink(parser);
            } else if (name.equals(PUB_DATE)) {
                pubDate = readPubDate(parser);
            } else if (name.equals(DESC)) {
                description = readDesc(parser);
            } else if (name.equals(CONTENT)) {
                content = readContent(parser);
            } else {
                XMLUtil.skip(parser);
            }
        }
        return new Article(null, title, link, description, false, false, content, null,
                DateUtil.parseRfc822(pubDate).getTime(), false, "", "", "");
    }

    private static String readTitle(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, TITLE);
        String title = Jsoup.parse(readText(parser)).text();
        parser.require(XmlPullParser.END_TAG, null, TITLE);

        return title;
    }

    private static String readLink(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, LINK);
        String link = readText(parser);
        parser.require(XmlPullParser.END_TAG, null, LINK);
        return link;
    }

    private static String readPubDate(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, PUB_DATE);
        String pubData = readText(parser);
        parser.require(XmlPullParser.END_TAG, null, PUB_DATE);
        return pubData;
    }

    private static String readDesc(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, DESC);
        String desc = readText(parser);
        parser.require(XmlPullParser.END_TAG, null, DESC);
        return desc;
    }

    private static String readContent(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, CONTENT);
        String content = readText(parser);
        parser.require(XmlPullParser.END_TAG, null, CONTENT);
        return content;
    }

    private static Long readLastBuildDate(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, LAST_BUILD_DATE);
        String dateStr = readText(parser);
        parser.require(XmlPullParser.END_TAG, null, LAST_BUILD_DATE);

        return DateUtil.parseRfc822(dateStr).getTime();
    }

    private static String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }
}
