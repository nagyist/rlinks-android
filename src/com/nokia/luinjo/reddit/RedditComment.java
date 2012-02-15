package com.nokia.luinjo.reddit;

import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class RedditComment {

	public static RedditComment fromJson(JSONObject obj) throws JSONException {
		RedditComment comment = new RedditComment();
		comment.setBody(obj.getString("body"));
		return comment;
	}
	
	private String mBody;

	private Date mCreated;

	private List<RedditComment> mChildren;

	private RedditComment mParent;
	
	private int mLevel;

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

	public List<RedditComment> getChildren() {
		return mChildren;
	}

	public void setChildren(List<RedditComment> children) {
		this.mChildren = children;
	}

	public RedditComment getParent() {
		return mParent;
	}

	public void setParent(RedditComment parent) {
		this.mParent = parent;
	}
	
	public int getLevel() {
		return mLevel;
	}
	
	public void setLevel(int level) {
		this.mLevel = level;
	}
}
