package com.nokia.luinjo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class LuinjoMainActivity extends ListActivity {

	private static final String TAG  = "MainActivity";	
	private static final RedditLinkItem[] ITEMS;
	
	private static RedditLinkItem createItem(int idx) {
		RedditLinkItem item = new RedditLinkItem();
		item.setTitle("ITEM " + idx);
		return item;
	}
	
	static {
		ITEMS = new RedditLinkItem[] {
			createItem(1), createItem(2), createItem(3)
		};
	}
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
                
        ListView lv = getListView();
        lv.setTextFilterEnabled(true);
        
        lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				RedditLinkItem item = (RedditLinkItem) getListAdapter().getItem(position);				
				Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();				
			}        	
		});
        
        populateList();        
    }
    
    private void populateList() {
    	RedditHttpClient client = new RedditHttpClient();
    	
    	JSONObject jsonResponse;    	
    	JSONArray jsonItems;
		try {
			jsonResponse = new JSONObject(client.getTopStories());
			jsonItems = jsonResponse.getJSONObject("data").getJSONArray("children");
		} catch (JSONException e) {
			Log.e(TAG, "Could not populate from JSON data: " + e.getMessage());
			return;
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
    	
        RedditLinkAdapter adapter = new RedditLinkAdapter(this, items);
        setListAdapter(adapter);    	
    }
}