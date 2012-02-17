package com.nokia.luinjo.reddit;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

public class RedditComment {

	public static RedditComment fromJson(JSONObject obj) throws JSONException {
		RedditComment comment = new RedditComment();
		comment.setAuthor(obj.getString("author"));
		comment.setBody(obj.getString("body"));
		comment.setCreated(new Date(Long.valueOf(obj.getLong("created")) * 1000));
		return comment;
	}

	private String mAuthor;
	
	private String mBody;

	private Date mCreated;

	private int mLevel;

	public String getAuthor() {
	    return this.mAuthor;
	}
	
	public void setAuthor(String author) {
	    this.mAuthor = author;
	}
	
	public String getBody() {
		return mBody;
	}

	public void setBody(String body) {
		this.mBody = body;
	}

	public Date getCreated() {
		return mCreated;
	}

	public void setCreated(Date created) {
		this.mCreated = created;
	}

	public int getLevel() {
		return mLevel;
	}
	
	public void setLevel(int level) {
		this.mLevel = level;
	}
}
