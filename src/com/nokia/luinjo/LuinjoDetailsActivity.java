
package com.nokia.luinjo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewSwitcher;

import com.nokia.luinjo.reddit.RedditClient;
import com.nokia.luinjo.reddit.RedditComment;
import com.nokia.luinjo.reddit.RedditCommentAdapter;
import com.nokia.luinjo.reddit.RedditLinkItem;
import com.nokia.luinjo.reddit.RedditLinkItemView;

public class LuinjoDetailsActivity extends Activity {

    private static final String TAG = "DetailsActivity";

    private RedditLinkItem mLinkItem;
    private RedditLinkItemView mLinkView;
    private ImageView mImageView;
    private ListView mCommentsView;
    
    private ViewSwitcher mViewSwitcher;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.details);
        mLinkView = (RedditLinkItemView) findViewById(R.id.link_item);
        // mImageView = (ImageView) findViewById(R.id.link_image);
        mCommentsView = (ListView) findViewById(R.id.comments_list);
        mViewSwitcher = (ViewSwitcher) findViewById(R.id.view_switcher);

        // Get link item passed with the intent
        mLinkItem = (RedditLinkItem) getIntent().getSerializableExtra(
                RedditLinkItem.class.getName());

        // Open link in browser on tap
        mLinkView.populateWith(mLinkItem);
        mLinkView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(mLinkItem.getUrl()));
                startActivity(intent);
            }
        });        
        
        // showOrHideImage();        
        loadComments();
    }

    private boolean isImage(String url) {
        return (url.endsWith(".jpg") || url.endsWith(".png") || url.endsWith(".gif"));
    }

    private void showOrHideImage() {
        final String itemUrl = mLinkItem.getUrl();
        // If the URL looks like an image, show the ImageView; otherwise hide it
        boolean isImage = isImage(itemUrl);
        mImageView.setVisibility(isImage ? View.VISIBLE : View.INVISIBLE);
        if (!isImage) {
            return;
        }

        // Load the image bitmap in a non-blocking thread
        new Thread(new Runnable() {
            public void run() {
                final LuinjoHttpClient httpClient = new LuinjoHttpClient();
                final Bitmap imageBitmap = httpClient.getImageBitmapFromUrl(itemUrl);
                if (imageBitmap != null) {
                    mImageView.post(new Runnable() {
                        public void run() {
                            mImageView.setImageBitmap(imageBitmap);
                        }
                    });
                }
            }
        }).start();
    }

    private void loadComments() {
        final List<RedditComment> comments = new ArrayList<RedditComment>();
        final RedditCommentAdapter adapter = new RedditCommentAdapter(this, comments);
        mCommentsView.setAdapter(adapter);

        // Load comments in a separate thread
        new Thread(new Runnable() {
            public void run() {
                Log.d(TAG, "Loading comments...");
                RedditClient client = new RedditClient();
                comments.addAll(client.getComments(mLinkItem));

                mCommentsView.post(new Runnable() {
                    public void run() {
                        adapter.notifyDataSetChanged();
                        mViewSwitcher.showNext();
                    }
                });
            }
        }).start();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
