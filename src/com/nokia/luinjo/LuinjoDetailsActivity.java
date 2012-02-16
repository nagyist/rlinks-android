
package com.nokia.luinjo;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.nokia.luinjo.reddit.RedditClient;
import com.nokia.luinjo.reddit.RedditLinkItem;
import com.nokia.luinjo.reddit.RedditLinkItemView;

public class LuinjoDetailsActivity extends Activity {

    private static final String TAG = "DetailsActivity";

    private ImageView mImageView;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RedditLinkItem item = (RedditLinkItem) getIntent().getSerializableExtra(
                RedditLinkItem.class.getName());
        Log.d(TAG, "Received an item: " + item.toString());

        setContentView(R.layout.details);
        mImageView = (ImageView) findViewById(R.id.link_image);

        RedditLinkItemView itemView = (RedditLinkItemView) findViewById(R.id.link_item);
        itemView.populateWith(item);

        showOrHideImage(item);
        // loadComments(item);
    }

    private boolean isImage(String url) {
        return (url.endsWith(".jpg") || url.endsWith(".png") || url.endsWith(".gif"));
    }

    private void showOrHideImage(final RedditLinkItem item) {
        // If the URL looks like an image, show the ImageView; otherwise hide it
        boolean isImage = isImage(item.getUrl());
        mImageView.setVisibility(isImage ? View.VISIBLE : View.INVISIBLE);
        if (!isImage) {
            return;
        }

        // Load the image bitmap in a non-blocking thread
        new Thread(new Runnable() {
            public void run() {
                final Bitmap imageBitmap = getImageBitmapFromUrl(item.getUrl());
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

    private void loadComments(RedditLinkItem item) {
        RedditClient client = new RedditClient();
        client.getComments(item);
    }

    private Bitmap getImageBitmapFromUrl(String imageUrlStr) {
        URL imageUrl = null;
        HttpURLConnection connection = null;
        InputStream is = null;
        BufferedInputStream buffer = null;
        Bitmap bitmap = null;
        
        try {
            imageUrl = new URL(imageUrlStr);
            connection = (HttpURLConnection) imageUrl.openConnection();
            is = connection.getInputStream();
            buffer = new BufferedInputStream(is);
            bitmap = BitmapFactory.decodeStream(buffer);
        } catch (MalformedURLException e) {
            Log.e(TAG, "Invalid URL: " + e.getMessage());
        } catch (IOException ioe) {
            Log.e(TAG, "I/O exception on trying to load image: " + ioe.getMessage());
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (buffer != null) {
                    buffer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        return bitmap;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
