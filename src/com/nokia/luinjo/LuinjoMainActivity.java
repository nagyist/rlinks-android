package com.nokia.luinjo;

import android.app.ListActivity;
import android.os.Bundle;
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
        
        setListAdapter(new RedditLinkAdapter(this, ITEMS));
        
        ListView lv = getListView();
        lv.setTextFilterEnabled(true);
        
        lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				RedditLinkItem item = (RedditLinkItem) getListAdapter().getItem(position);				
				Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();				
			}        	
		});
    }    
}