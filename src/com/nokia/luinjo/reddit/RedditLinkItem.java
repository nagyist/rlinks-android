package com.nokia.luinjo.reddit;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

public class RedditLinkItem {
	
	public static RedditLinkItem fromJson(JSONObject obj) throws JSONException {
		RedditLinkItem rli = new RedditLinkItem();
		rli.setAuthor(obj.getString("author"));
		rli.setCreated(new Date(Long.valueOf(obj.getLong("created")) * 1000));
		rli.setDomain(obj.getString("domain"));
		rli.setNumComments(obj.getInt("num_comments"));
		rli.setPermalink(obj.getString("permalink"));
		rli.setSubreddit(obj.getString("subreddit"));
		rli.setScore(obj.getInt("score"));
		rli.setTitle(obj.getString("title"));
		rli.setUrl(obj.getString("url"));
		return rli;
	}
	
	private String mAuthor;
	
	private Date mCreated;
	
	private String mDomain;
	
	private int mNumComments;
	
	private String mPermalink;
	
	private int mScore;
	
	private String mSubreddit;
	
	private String mTitle;
	
	private String mUrl;

	public RedditLinkItem() {}

	public String getAuthor() {
		return mAuthor;
	}

	public Date getCreated() {
		return mCreated;
	}

	public String getDomain() {
		return mDomain;
	}

	public int getNumComments() {
		return mNumComments;
	}

	public String getPermalink() {
		return mPermalink;
	}

	public int getScore() {
		return mScore;
	}

	public String getSubreddit() {
		return mSubreddit;
	}

	public String getTitle() {
		return mTitle;
	}

	public String getUrl() {
		return mUrl;
	}

	public void setAuthor(String author) {
		this.mAuthor = author;
	}
	
	public void setCreated(Date created) {
		this.mCreated = created;
	}

	public void setDomain(String domain) {
		this.mDomain = domain;
	}

	public void setNumComments(int mNumComments) {
		this.mNumComments = mNumComments;
	}

	public void setPermalink(String permalink) {
		this.mPermalink = permalink;
	}
	
	public void setScore(int score) {
		this.mScore = score;
	}
	
	public void setSubreddit(String subreddit) {
		this.mSubreddit = subreddit;
	}
	
	public void setTitle(String title) {
		this.mTitle = title;
	}
	
	public void setUrl(String url) {
		this.mUrl = url;
	}
}
