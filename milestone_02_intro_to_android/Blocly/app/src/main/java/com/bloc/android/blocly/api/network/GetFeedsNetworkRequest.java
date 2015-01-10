package com.bloc.android.blocly.api.network;

import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by Wayne on 1/9/2015.
 */
public class GetFeedsNetworkRequest extends NetworkRequest<List<GetFeedsNetworkRequest.FeedResponse>> {
    // store each feed's address
    String [] feedUrls;
    public static final int ERROR_PARSING = 3;
    private static final String XML_TAG_TITLE = "title";
    private static final String XML_TAG_DESCRIPTION = "description";
    private static final String XML_TAG_LINK = "link";
    private static final String XML_TAG_ITEM = "item";
    private static final String XML_TAG_PUB_DATE = "pubDate";
    private static final String XML_TAG_GUID = "guid";
    private static final String XML_TAG_ENCLOSURE = "enclosure";
    private static final String XML_ATTRIBUTE_URL = "url";
    private static final String XML_ATTRIBUTE_TYPE = "type";
    private static final Pattern TITLE_TAG =
            Pattern.compile("<title>(.*)</title>", Pattern.CASE_INSENSITIVE| Pattern.DOTALL);


    public GetFeedsNetworkRequest(String... feedUrls) {
        this.feedUrls = feedUrls;
    }

    // override the mandatory abstract method declared in NetworkRequest
    @Override
    public List<FeedResponse> performRequest() {
        Log.i ("# of items: ", "" +feedUrls.length);
        List<FeedResponse> responseFeeds = new ArrayList<FeedResponse>(feedUrls.length);
        for (String feedUrlString : feedUrls) {
            Log.i ("URL of feed: ", feedUrlString );
            /*try {
                //Log.i("Title of feed: ", title);
            } catch (IOException e) {
                e.printStackTrace();
            }*/
            InputStream inputStream = openStream(feedUrlString);
            if (inputStream == null) {
                return null;
            }
            try {
                try {
                    DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                    // parsing an InputStream directly into a new Document, therefore we bypass reading the InputStream ourselves
                    Document xmlDocument = documentBuilder.parse(inputStream);
                    String channelTitle = optFirstTagFromDocument(xmlDocument, XML_TAG_TITLE);
                    String channelDescription = optFirstTagFromDocument(xmlDocument, XML_TAG_DESCRIPTION);
                    String channelURL = optFirstTagFromDocument(xmlDocument, XML_TAG_LINK);
                    // recover a NodeList of each item tag found in the feed
                    NodeList allItemNodes = xmlDocument.getElementsByTagName(XML_TAG_ITEM);
                    List<ItemResponse> responseItems = new ArrayList<ItemResponse>(allItemNodes.getLength());
                    for (int itemIndex = 0; itemIndex < allItemNodes.getLength(); itemIndex++) {
                    // prepare a temporary variable for each field we hope to recover
                        String itemURL = null;
                        String itemTitle = null;
                        String itemDescription = null;
                        String itemGUID = null;
                        String itemPubDate = null;
                        String itemEnclosureURL = null;
                        String itemEnclosureMIMEType = null;
                        // recover the Node representing the individual RSS item
                        Node itemNode = allItemNodes.item(itemIndex);
                        NodeList tagNodes = itemNode.getChildNodes();
                        for (int tagIndex = 0; tagIndex < tagNodes.getLength(); tagIndex++) {
                            Node tagNode = tagNodes.item(tagIndex);
                            String tag = tagNode.getNodeName();
                            // iterate across all tags to find those we wish to parse
                            if (XML_TAG_LINK.equalsIgnoreCase(tag)) {
                                itemURL = tagNode.getTextContent();
                            } else if (XML_TAG_TITLE.equalsIgnoreCase(tag)) {
                                itemTitle = tagNode.getTextContent();
                            } else if (XML_TAG_DESCRIPTION.equalsIgnoreCase(tag)) {
                                itemDescription = tagNode.getTextContent();
                            } else if (XML_TAG_ENCLOSURE.equalsIgnoreCase(tag)) {
                                // retrieve a map of all attributes and recover both the url and type entries
                                NamedNodeMap enclosureAttributes = tagNode.getAttributes();
                                itemEnclosureURL = enclosureAttributes.getNamedItem(XML_ATTRIBUTE_URL).getTextContent();
                                itemEnclosureMIMEType = enclosureAttributes.getNamedItem(XML_ATTRIBUTE_TYPE).getTextContent();
                            } else if (XML_TAG_PUB_DATE.equalsIgnoreCase(tag)) {
                                itemPubDate = tagNode.getTextContent();
                            } else if (XML_TAG_GUID.equalsIgnoreCase(tag)) {
                                itemGUID = tagNode.getTextContent();
                            }
                        }
                        responseItems.add(new ItemResponse(itemURL, itemTitle, itemDescription,
                                itemGUID, itemPubDate, itemEnclosureURL, itemEnclosureMIMEType));
                    }
                /*BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                // After we open a connection to the feed, we begin reading it
                String line = bufferedReader.readLine();
                while (line != null) {
                    Log.v(getClass().getSimpleName(), "Line: " + line);

                    line = bufferedReader.readLine();
                    // extract the title
                    try {
                        Matcher matcher = TITLE_TAG.matcher(line);
                        if (matcher.find()) {
                    *//* replace any occurrences of whitespace (which may
                     * include line feeds and other uglies) as well
                     * as HTML brackets with a space *//*
                            //return matcher.group(1).replaceAll("[\\s\\<>]+", " ").trim();
                            String title = matcher.group(1).replaceAll("[\\s\\<>]+", " ").trim();
                            Log.i("Title of RSS feed: ", title);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                // Close the reader and its stream
                bufferedReader.close();*/

            } catch (IOException e) {
                e.printStackTrace();
                setErrorCode(ERROR_IO);
                return null;
            }
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            }
        }
        return responseFeeds;
    }

    private String optFirstTagFromDocument(Document document, String tagName) {
        NodeList elementsByTagName = document.getElementsByTagName(tagName);
        if (elementsByTagName.getLength() > 0) {
            return elementsByTagName.item(0).getTextContent();
        }
        return null;
    }

    // comprised of the three piece of channel tag information we defined
    public static class FeedResponse {
        public final String channelFeedURL;
        public final String channelTitle;
        public final String channelURL;
        public final String channelDescription;
        public final List<ItemResponse> channelItems;

        FeedResponse(String channelFeedURL, String channelTitle, String channelURL,
                     String channelDescription, List<ItemResponse> channelItems) {
            this.channelFeedURL = channelFeedURL;
            this.channelTitle = channelTitle;
            this.channelURL = channelURL;
            this.channelDescription = channelDescription;
            this.channelItems = channelItems;
        }
    }

    // #4
    public static class ItemResponse {
        public final String itemURL;
        public final String itemTitle;
        public final String itemDescription;
        public final String itemGUID;
        public final String itemPubDate;
        public final String itemEnclosureURL;
        public final String itemEnclosureMIMEType;

        ItemResponse(String itemURL, String itemTitle, String itemDescription,
                     String itemGUID, String itemPubDate, String itemEnclosureURL,
                     String itemEnclosureMIMEType) {
            this.itemURL = itemURL;
            this.itemTitle = itemTitle;
            this.itemDescription = itemDescription;
            this.itemGUID = itemGUID;
            this.itemPubDate = itemPubDate;
            this.itemEnclosureURL = itemEnclosureURL;
            this.itemEnclosureMIMEType = itemEnclosureMIMEType;
        }
    }
}
