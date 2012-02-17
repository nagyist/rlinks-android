package com.nokia.luinjo.reddit;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nokia.luinjo.LuinjoHttpClient;
import com.nokia.luinjo.R;

public class RedditLinkItemView extends RelativeLayout {

	private final Context mContext;

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
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.listitem_link, this);
	}

	private TextView getTextView(int id) {
		return ((TextView) this.findViewById(id));
	}

	public void populateWith(RedditLinkItem item) {
	    showOrHideThumbnail(item);
        
		getTextView(R.id.title).setText(item.getTitle());
		getTextView(R.id.score).setText("" + item.getScore());
		getTextView(R.id.domain).setText(item.getDomain());
		getTextView(R.id.num_comments).setText(
				"" + item.getNumComments() + " " + mContext.getResources().getString(R.string.comments));
		getTextView(R.id.subreddit).setText(item.getSubreddit());		
	}
	
	private void showOrHideThumbnail(RedditLinkItem item) {
        ImageView thumbnailView = (ImageView) this.findViewById(R.id.thumbnail);
        String thumbnailUrl = item.getThumbnail();        
        if (thumbnailUrl == null || thumbnailUrl.equals("")) {
            thumbnailView.setVisibility(View.GONE);
        } else {
            showThumbnailImage(thumbnailView, thumbnailUrl);
        }	    
	}
	
	private void showThumbnailImage(final ImageView thumbnailView, final String thumbnailUrl) {
        new Thread(new Runnable() {
            public void run() {
                final LuinjoHttpClient httpClient = new LuinjoHttpClient();
                final Bitmap imageBitmap = httpClient.getImageBitmapFromUrl(thumbnailUrl);
                if (imageBitmap != null) {
                    thumbnailView.post(new Runnable() {
                        public void run() {
                            thumbnailView.setImageBitmap(imageBitmap);
                        }
                    });
                }
            }
        }).start();	    
	}
}
