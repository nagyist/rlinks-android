package com.nokia.luinjo.reddit;

import com.nokia.luinjo.R;
import com.nokia.luinjo.R.id;
import com.nokia.luinjo.R.layout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

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
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View rowView = inflater.inflate(R.layout.listitem_link, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.label);
		textView.setText(mItems[position].getTitle());
		
		return rowView;
	}	
}
