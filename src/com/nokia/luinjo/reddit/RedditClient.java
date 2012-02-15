package com.nokia.luinjo.reddit;

import java.util.ArrayList;
import java.util.List;

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
    
    public List<RedditComment> getComments(RedditLinkItem item) {
    	String commentsJson = httpClient.getContent(REDDIT_BASE_URL + item.getPermalink() + ".json");
    	
    	List<RedditComment> comments = new ArrayList<RedditComment>();
    	JSONArray jsonResponse = null;
    	JSONArray childrenJsonArray = null;    	
    	
    	try {
    		jsonResponse = new JSONArray(commentsJson);
    		childrenJsonArray = jsonResponse.getJSONObject(1).getJSONObject("data").getJSONArray("children");
    	} catch (JSONException e) {
    		Log.e(TAG, "Could not parse JSON response into array: " + e.getMessage());
    	}
    	
    	JSONObject containerJsonObj, dataJsonObj;	
		for (int i = 0, len = childrenJsonArray.length(); i < len; i++) {
			try {
				containerJsonObj = childrenJsonArray.getJSONObject(i);
				dataJsonObj = containerJsonObj.getJSONObject("data");
				
				RedditComment comment = getCommentWithChildren(dataJsonObj, 0);
				comments.add(comment);
			} catch (JSONException e) {
				Log.e(TAG, "Could not parse comment JSON: " + e.getMessage());
			}
		}
    	
    	return comments;
    }
    
    private RedditComment getCommentWithChildren(JSONObject dataJsonObj, int level) throws JSONException {
    	Log.d(TAG, "getCommentWithChildren called for level " + level);    	
    	
    	List<RedditComment> replies = null;
    	JSONObject repliesJsonObj = dataJsonObj.optJSONObject("replies");    	
		if (repliesJsonObj != null) {
			JSONArray childrenJsonArray = repliesJsonObj.getJSONObject("data").getJSONArray("children");
			
			replies = new ArrayList<RedditComment>();
			JSONObject childContainerJsonObj, childDataJsonObj;
			for (int i = 0, len = childrenJsonArray.length(); i < len; i++) {
				childContainerJsonObj = childrenJsonArray.getJSONObject(i);
				childDataJsonObj = childContainerJsonObj.getJSONObject("data");
				
				String kind = childContainerJsonObj.getString("kind"); 
				if (kind.equals("t1")) {					
					replies.add(getCommentWithChildren(childDataJsonObj, level++));
				} else {
					System.out.println("End of replies reached");
				}
			}
		}

		RedditComment comment = RedditComment.fromJson(dataJsonObj);
		comment.setChildren(replies);		
		return comment;
    }    
}





