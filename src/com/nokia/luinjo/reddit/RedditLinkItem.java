package com.nokia.luinjo.reddit;

import org.json.JSONException;
import org.json.JSONObject;

public class RedditLinkItem {
	
	private String title;
	
	private String subreddit;
	
	private Integer score;
	
	private String domain;
	
	private String permalink;
	
	private String author;	
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubreddit() {
		return subreddit;
	}

	public void setSubreddit(String subreddit) {
		this.subreddit = subreddit;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getPermalink() {
		return permalink;
	}

	public void setPermalink(String permalink) {
		this.permalink = permalink;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
	
	public RedditLinkItem() {}
	
	public static RedditLinkItem fromJson(JSONObject obj) throws JSONException {
		RedditLinkItem rli = new RedditLinkItem();
		rli.setTitle(obj.getString("title"));
		return rli;
	}
}
