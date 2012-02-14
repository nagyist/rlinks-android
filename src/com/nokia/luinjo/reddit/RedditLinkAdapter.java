package com.nokia.luinjo.reddit;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class RedditLinkAdapter extends BaseAdapter {
	
	private final Context mContext;
	private final RedditLinkItem[] mItems;
	
	public RedditLinkAdapter(Context context, RedditLinkItem[] items) {
		mContext = context;
		mItems = items;
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
		return new RedditLinkItemView(mContext, mItems[position]);
	}
}
