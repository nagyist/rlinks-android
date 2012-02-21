/*
 * Copyright � 2012 Nokia Corporation. All rights reserved.
 * Nokia and Nokia Connecting People are registered trademarks of Nokia Corporation. 
 * Oracle and Java are trademarks or registered trademarks of Oracle and/or its
 * affiliates. Other product and company names mentioned herein may be trademarks
 * or trade names of their respective owners.
 *  
 * See LICENSE.TXT for license information.
 */

package com.nokia.example.luinjo.display;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class RedditLinkAdapter extends BaseAdapter {

    private final Context mContext;
    private final RedditLink[] mItems;

    public RedditLinkAdapter(Context context, RedditLink[] items) {
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
        return new RedditLinkView(mContext, mItems[position]);
    }
}
