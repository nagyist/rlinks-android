/*
 * Copyright © 2012 Nokia Corporation. All rights reserved.
 * Nokia and Nokia Connecting People are registered trademarks of Nokia Corporation. 
 * Oracle and Java are trademarks or registered trademarks of Oracle and/or its
 * affiliates. Other product and company names mentioned herein may be trademarks
 * or trade names of their respective owners.
 *  
 * See LICENSE.TXT for license information.
 */

package com.nokia.example.luinjo.display;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

public class RedditComment {

    public static RedditComment fromJson(JSONObject obj) throws JSONException {
        RedditComment comment = new RedditComment();
        comment.setAuthor(obj.optString("author"));
        comment.setBody(obj.optString("body"));
        comment.setCreated(new Date(Long.valueOf(obj.optLong("created")) * 1000));
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
