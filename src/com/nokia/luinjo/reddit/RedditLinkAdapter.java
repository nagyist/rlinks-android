package com.nokia.luinjo.reddit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nokia.luinjo.R;

public class RedditLinkAdapter extends BaseAdapter {
	
	private final Context mContext;
	private final RedditLinkItem[] mItems;
	
	private final String COMMENTS_STR; 
	
	public RedditLinkAdapter(Context context, RedditLinkItem[] items) {
		mContext = context;
		mItems = items;
		
		COMMENTS_STR = mContext.getResources().getString(R.string.comments);
	}

	public int getCount() {
		if (mItems != null) {
			return mItems.length;
		}
		return 0;
	}

	public Object getItem(int position) {
		if (mItems != null) {
			return mItems[position];
		}
		return null;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);		
		View rowView = inflater.inflate(R.layout.listitem_link, parent, false);
		
		
		RedditLinkItem item = mItems[position];
		((TextView) rowView.findViewById(R.id.title)).setText(item.getTitle());
		((TextView) rowView.findViewById(R.id.score)).setText("" + item.getScore());
		((TextView) rowView.findViewById(R.id.domain)).setText(item.getDomain());
		((TextView) rowView.findViewById(R.id.num_comments)).setText("" + item.getNumComments() + " " + COMMENTS_STR);
		((TextView) rowView.findViewById(R.id.subreddit)).setText(item.getSubreddit());
		
		return rowView;
	}	
}
