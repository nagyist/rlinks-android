/*
 * Copyright © 2012 Nokia Corporation. All rights reserved.
 * Nokia and Nokia Connecting People are registered trademarks of Nokia Corporation. 
 * Oracle and Java are trademarks or registered trademarks of Oracle and/or its
 * affiliates. Other product and company names mentioned herein may be trademarks
 * or trade names of their respective owners.
 *  
 * See LICENSE.TXT for license information.
 */

package com.nokia.example.luinjo.display;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.Html;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nokia.example.luinjo.R;
import com.nokia.example.luinjo.network.LuinjoHttpClient;

public class RedditLinkView extends RelativeLayout {
    
    private final Context mContext;
    private ImageView mThumbnailView;

    public RedditLinkView(Context context) {
        super(context);

        mContext = context;
        setupView();
    }

    public RedditLinkView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        mContext = context;
        setupView();
    }

    public RedditLinkView(Context context, RedditLink item) {
        super(context);

        mContext = context;        
        setupView();
        updateView(item);
    }

    private void setupView() {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.listitem_link, this);

        mThumbnailView = (ImageView) findViewById(R.id.thumbnail);
    }

    private TextView getTextView(int id) {
        return ((TextView) this.findViewById(id));
    }

    public void updateView(final RedditLink item) {
        getTextView(R.id.title).setText(Html.fromHtml(item.getTitle()).toString());
        getTextView(R.id.score).setText("" + item.getScore());
        getTextView(R.id.domain).setText(item.getDomain());
        getTextView(R.id.num_comments).setText(
                "" + item.getNumComments() + " "
                        + mContext.getResources().getString(R.string.comments));
        getTextView(R.id.subreddit).setText(item.getSubreddit());

        mThumbnailView.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                final Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(item.getUrl()));
                mContext.startActivity(intent);
            }
        });        
        showOrHideThumbnail(item);
    }

    private void showOrHideThumbnail(RedditLink item) {
        ImageView thumbnailView = (ImageView) this.findViewById(R.id.thumbnail);
        String thumbnailUrl = item.getThumbnail();

        if (thumbnailUrl == null || thumbnailUrl.equals("")) {
            // For links with no thumbnails, set the view visibility to GONE
            // to make the layout reflow
            thumbnailView.setVisibility(View.GONE);
        } else {
            // Load thumbnail asynchronously
            new LoadThumbnailTask().execute(thumbnailUrl);
        }
    }

    private class LoadThumbnailTask extends AsyncTask<String, Integer, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... params) {
            LuinjoHttpClient httpClient = new LuinjoHttpClient();
            Bitmap imageBitmap = httpClient.getImageBitmapFromUrl(params[0]);
            return imageBitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            mThumbnailView.setImageBitmap(result);
        }
    }
}
