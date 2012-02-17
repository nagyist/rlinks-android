
package com.nokia.luinjo.reddit;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nokia.luinjo.R;

public class RedditCommentAdapter extends BaseAdapter {

    private final Context mContext;
    private final List<RedditComment> mItems;
    
    public RedditCommentAdapter(Context context, List<RedditComment> items) {
        mContext = context;
        mItems = items;
    }

    public int getCount() {
        if (mItems != null) {
            return mItems.size();
        }
        return 0;
    }

    public RedditComment getItem(int position) {
        if (mItems != null) {
            return mItems.get(position);
        }
        return null;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listitem_comment, parent, false);
        }
        
        // TODO: set item left margin instead if possible
        RedditComment item = getItem(position);
        int level = item.getLevel();
        
        // Increase left padding for each level, but gradually less so that the comment
        // body will still have enough horizontal space
        int depthOffset = level == 0 ? 0 : Math.max(0, (50 * level) - (30 * (level - 1)));
        
        TextView author = (TextView) view.findViewById(R.id.author);
        author.setPadding(depthOffset, 0, 0, 0);
        author.setText(item.getAuthor());
        
        TextView body = (TextView) view.findViewById(R.id.body);
        body.setPadding(depthOffset, 0, 0, 0);
        body.setText(item.getBody());
        
        return view;
    }

}
