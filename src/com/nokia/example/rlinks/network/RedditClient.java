/*
 * Copyright © 2012 Nokia Corporation. All rights reserved.
 * Nokia and Nokia Connecting People are registered trademarks of Nokia Corporation. 
 * Oracle and Java are trademarks or registered trademarks of Oracle and/or its
 * affiliates. Other product and company names mentioned herein may be trademarks
 * or trade names of their respective owners.
 *  
 * See LICENSE.TXT for license information.
 */

package com.nokia.example.rlinks.network;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.nokia.example.rlinks.model.RedditComment;
import com.nokia.example.rlinks.model.RedditLink;

public class RedditClient {

    private static final String TAG = "RedditClient";
    private static final String REDDIT_BASE_URL = "http://www.reddit.com/";
    private static final RLinksHttpClient httpClient;

    static {
        httpClient = new RLinksHttpClient();
    }

    public List<RedditLink> getTopLinks() {
        String contentJson = httpClient.getContent(REDDIT_BASE_URL + ".json");

        JSONObject jsonResponse;
        JSONArray jsonItems;
        try {
            jsonResponse = new JSONObject(contentJson);
            jsonItems = jsonResponse.getJSONObject("data").getJSONArray("children");
        } catch (JSONException e) {
            Log.e(TAG, "Could not populate from JSON data: " + e.getMessage());
            return null;
        }

        int numItems = jsonItems.length();
        if (numItems == 0) {
            return null;
        }
        
        List<RedditLink> items = new ArrayList<RedditLink>();
        RedditLink item;
        JSONObject jsonObj;

        for (int i = 0; i < numItems; i++) {
            try {
                jsonObj = jsonItems.getJSONObject(i).getJSONObject("data");
                item = RedditLink.fromJson(jsonObj);
                items.add(item);
            } catch (JSONException e) {
                Log.e(TAG, "Could not parse JSON object: " + e.getMessage());
            }
        }

        return items;
    }

    // TODO: document the JSON response formats
    public List<RedditComment> getComments(RedditLink item) {
        String commentsUrl = REDDIT_BASE_URL + "comments/" + item.getId() + ".json";
        String commentsJson = httpClient.getContent(commentsUrl);
        if (commentsJson == null) {
            return null;
        }

        final List<RedditComment> comments = new ArrayList<RedditComment>();
        JSONArray commentsJsonArray = null;
        JSONArray childrenJsonArray = null;
        JSONObject listingJsonObject = null;

        try {
            commentsJsonArray = new JSONArray(commentsJson);
            listingJsonObject = commentsJsonArray.getJSONObject(1);
            childrenJsonArray = listingJsonObject.getJSONObject("data").getJSONArray("children");
        } catch (JSONException e) {
            Log.e(TAG, "Could not parse JSON response into array: " + e.getMessage());
            return comments;
        }

        // TODO: merge with getCommentWithChildren()
        JSONObject containerJsonObj, dataJsonObj;
        for (int i = 0, len = childrenJsonArray.length(); i < len; i++) {
            try {
                containerJsonObj = childrenJsonArray.getJSONObject(i);
                dataJsonObj = containerJsonObj.getJSONObject("data");
                addCommentWithChildren(comments, dataJsonObj, 0);
            } catch (JSONException e) {
                Log.e(TAG, "Could not parse comment JSON: " + e.getMessage());
            }
        }

        return comments;
    }

    private void addCommentWithChildren(List<RedditComment> comments, JSONObject dataJsonObj,
            int level) throws JSONException {
        JSONObject listingJsonObject = dataJsonObj.optJSONObject("replies");

        RedditComment comment = RedditComment.fromJson(dataJsonObj);
        comment.setLevel(level);
        comments.add(comment);

        if (listingJsonObject != null) {
            JSONArray childrenJsonArray = listingJsonObject.getJSONObject("data").getJSONArray(
                    "children");

            JSONObject childContainerJsonObj, childDataJsonObj;
            for (int i = 0, len = childrenJsonArray.length(); i < len; i++) {
                childContainerJsonObj = childrenJsonArray.getJSONObject(i);
                childDataJsonObj = childContainerJsonObj.getJSONObject("data");

                String kind = childContainerJsonObj.getString("kind");
                if (kind.equals("t1")) {
                    addCommentWithChildren(comments, childDataJsonObj, level++);
                }
            }
        }
    }
}
