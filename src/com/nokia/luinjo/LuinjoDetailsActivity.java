package com.nokia.luinjo;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import com.nokia.luinjo.reddit.RedditLinkItem;
import com.nokia.luinjo.reddit.RedditLinkItemView;

public class LuinjoDetailsActivity extends Activity {
	
	private static final String TAG = "DetailsActivity";

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		RedditLinkItem item = (RedditLinkItem) getIntent().getSerializableExtra(RedditLinkItem.class.getName());
		Log.d(TAG, "Received an item: " + item.toString());
		
		setContentView(R.layout.details);
		RedditLinkItemView itemView = (RedditLinkItemView) findViewById(R.id.link_item);
		itemView.populateWith(item);
	}
	
	@Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
	}	
}
