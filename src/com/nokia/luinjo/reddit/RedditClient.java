package com.nokia.luinjo.reddit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.nokia.luinjo.LuinjoHttpClient;

public class RedditClient {
	
	private static final String TAG = "RedditClient";
	private static final String REDDIT_BASE_URL = "http://www.reddit.com/";	
	private static final LuinjoHttpClient httpClient;	
	private static final RedditLinkItem[] NO_ITEMS = new RedditLinkItem[] {};
	
	static {
		httpClient = new LuinjoHttpClient();
	}
	
    public RedditLinkItem[] getTopStories() {
    	String contentJson = httpClient.getContent(REDDIT_BASE_URL + ".json");

    	JSONObject jsonResponse;    	
    	JSONArray jsonItems;
		try {
			jsonResponse = new JSONObject(contentJson);
			jsonItems = jsonResponse.getJSONObject("data").getJSONArray("children");
		} catch (JSONException e) {
			Log.e(TAG, "Could not populate from JSON data: " + e.getMessage());
			return NO_ITEMS;
		}
		
    	RedditLinkItem[] items;
    	int numItems = jsonItems.length(); 
    	if (numItems == 0) {
    		items = new RedditLinkItem[] {};
    	} else {
    		items = new RedditLinkItem[jsonItems.length()];
    		
    		RedditLinkItem item;
    		JSONObject jsonObj;
        	for (int i = 0; i < numItems; i++) {
        		try {
					jsonObj = jsonItems.getJSONObject(i).getJSONObject("data");
					item = RedditLinkItem.fromJson(jsonObj); 
					items[i] = item;
				} catch (JSONException e) {
					items[i] = new RedditLinkItem();
					Log.e(TAG, "Could not parse JSON object: " + e.getMessage());
				}        		
        	}    		
    	}
    	
    	return items;
    }
}
