package com.nokia.luinjo;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class LuinjoMainActivity extends ListActivity {

	private static final String TAG  = "MainActivity";	
	private static final String[] ITEMS = { "NEWS ITEM 1", "NEWS ITEM 2", "NEWS ITEM 3" };
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setListAdapter(new RedditLinkAdapter(this, ITEMS));
        
        ListView lv = getListView();
        lv.setTextFilterEnabled(true);
        
        lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String item = (String) getListAdapter().getItem(position);
				Toast.makeText(getApplicationContext(), item, Toast.LENGTH_SHORT).show();				
			}        	
		});
        
        /*
        setContentView(R.layout.main);
                
        TextView tv;
        tv = (TextView) findViewById(R.id.textview);
        tv.setText("Loading...");
        
        String contentStr = getTopStories();
        if (contentStr == null) {
        	contentStr = "(no data)";
        }                
        tv.setText(contentStr);
        */        
    }
    

}