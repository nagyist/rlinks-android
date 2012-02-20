
package com.nokia.luinjo;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.nokia.luinjo.display.RedditLink;
import com.nokia.luinjo.display.RedditLinkAdapter;
import com.nokia.luinjo.http.RedditClient;

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
                RedditLink item = (RedditLink) getListAdapter().getItem(position);

                // Start a new Activity, passing it the current Item as a
                // Serializable
                Intent intent = new Intent(self, LuinjoDetailsActivity.class);
                intent.putExtra(RedditLink.class.getName(), item);
                self.startActivity(intent);
            }
        });

        populateList();
    }

    private void populateList() {
        ProgressDialog dialog = ProgressDialog.show(LuinjoMainActivity.this, "", getResources()
                .getText(R.string.loading), true);

        RedditClient client = new RedditClient();
        RedditLink[] items = client.getTopStories();
        Log.d(TAG, "Loaded " + items.length + " items");
        RedditLinkAdapter adapter = new RedditLinkAdapter(this, items);
        setListAdapter(adapter);

        dialog.hide();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh_feed:
                Log.d(TAG, "Refreshing feed...");
                this.populateList();                
                ((RedditLinkAdapter) this.getListAdapter()).notifyDataSetChanged();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Override onConfigurationChanged to prevent reloading the activity on
     * orientation change. This also requires
     * android:configChanges="orientation" added in the manifest.
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
