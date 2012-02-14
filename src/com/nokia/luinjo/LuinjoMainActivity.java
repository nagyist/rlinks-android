package com.nokia.luinjo;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.nokia.luinjo.reddit.RedditClient;
import com.nokia.luinjo.reddit.RedditLinkAdapter;
import com.nokia.luinjo.reddit.RedditLinkItem;

public class LuinjoMainActivity extends ListActivity {

	private static final String TAG = "MainActivity";

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ListView lv = getListView();
		lv.setTextFilterEnabled(true);

		final Context self = this;
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				RedditLinkItem item = (RedditLinkItem) getListAdapter().getItem(position);
				
				// Start a new Activity, passing it the current Item as a Serializable
				Intent intent = new Intent(self, LuinjoDetailsActivity.class);
				intent.putExtra(RedditLinkItem.class.getName(), item);
				self.startActivity(intent);
			}
		});

		populateList();
	}

	private void populateList() {
		ProgressDialog dialog = ProgressDialog.show(
				LuinjoMainActivity.this, "", getResources().getText(R.string.loading), true);

		RedditClient client = new RedditClient();
		RedditLinkItem[] items = client.getTopStories();
		RedditLinkAdapter adapter = new RedditLinkAdapter(this, items);
		setListAdapter(adapter);

		dialog.hide();
	}
	
	/**
	 * Override onConfigurationChanged to prevent reloading the activity on orientation change.
	 * This also requires android:configChanges="orientation" added in the manifest.
	 */
	@Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
	}
}