
package com.nokia.luinjo.reddit;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nokia.luinjo.LuinjoHttpClient;
import com.nokia.luinjo.R;

public class RedditLinkItemView extends RelativeLayout {

    private static final String TAG = "RedditLinkItemView";

    private final Context mContext;
    private ImageView mThumbnailView;

    public RedditLinkItemView(Context context) {
        super(context);

        mContext = context;
        setupView();
    }

    public RedditLinkItemView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        mContext = context;
        setupView();
    }

    public RedditLinkItemView(Context context, RedditLinkItem item) {
        super(context);

        mContext = context;
        setupView();
        populateWith(item);
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

    public void populateWith(RedditLinkItem item) {
        getTextView(R.id.title).setText(item.getTitle());
        getTextView(R.id.score).setText("" + item.getScore());
        getTextView(R.id.domain).setText(item.getDomain());
        getTextView(R.id.num_comments).setText(
                "" + item.getNumComments() + " "
                        + mContext.getResources().getString(R.string.comments));
        getTextView(R.id.subreddit).setText(item.getSubreddit());

        showOrHideThumbnail(item);
    }

    private void showOrHideThumbnail(RedditLinkItem item) {
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
