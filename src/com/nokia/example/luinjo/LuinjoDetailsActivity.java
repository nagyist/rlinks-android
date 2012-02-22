/*
 * Copyright © 2012 Nokia Corporation. All rights reserved.
 * Nokia and Nokia Connecting People are registered trademarks of Nokia Corporation. 
 * Oracle and Java are trademarks or registered trademarks of Oracle and/or its
 * affiliates. Other product and company names mentioned herein may be trademarks
 * or trade names of their respective owners.
 *  
 * See LICENSE.TXT for license information.
 */

package com.nokia.example.luinjo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ViewSwitcher;

import com.nokia.example.luinjo.display.RedditCommentAdapter;
import com.nokia.example.luinjo.display.RedditLinkView;
import com.nokia.example.luinjo.model.RedditComment;
import com.nokia.example.luinjo.model.RedditLink;
import com.nokia.example.luinjo.network.RedditClient;

public class LuinjoDetailsActivity extends Activity {

    private RedditLink mLinkItem;
    private RedditLinkView mLinkView;
    private ListView mCommentsView;
    private List<RedditComment> mComments;

    private ViewSwitcher mViewSwitcher;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get link item passed with the intent
        mLinkItem = (RedditLink) getIntent().getSerializableExtra(
                RedditLink.class.getName());

        setContentView(R.layout.details);
        mLinkView = (RedditLinkView) findViewById(R.id.link_item);
        mCommentsView = (ListView) findViewById(R.id.comments_list);
        mViewSwitcher = (ViewSwitcher) findViewById(R.id.view_switcher);

        // Open link in browser on tap
        mLinkView.updateView(mLinkItem);
        mLinkView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(mLinkItem
                        .getUrl()));
                startActivity(intent);
            }
        });

        loadComments();
    }

    private void loadComments() {
        mComments = new ArrayList<RedditComment>();
        mCommentsView.setAdapter(new RedditCommentAdapter(this, mComments));

        new LoadCommentsTask().execute(mLinkItem);
    }

    private class LoadCommentsTask extends AsyncTask<RedditLink, Integer, Void> {
        @Override
        protected Void doInBackground(RedditLink... params) {
            RedditClient client = new RedditClient();
            mComments.addAll(client.getComments(params[0]));
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            ((RedditCommentAdapter) mCommentsView.getAdapter()).notifyDataSetChanged();
            mViewSwitcher.showNext();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
