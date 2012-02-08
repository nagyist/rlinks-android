package com.nokia.luinjo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class LuinjoMainActivity extends Activity {
		
	public static final String REDDIT_BASE_URL = "http:/www.reddit.com/";
	private static final String TAG  = "MainActivity";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        System.out.println(getTopStories());
    }
    
    // TODO: create new thread
    private String getTopStories() {
    	HttpResponse response = null;
    	
    	try {        	
        	HttpGet request = new HttpGet();
        	// request.setURI(new URI(REDDIT_BASE_URL + ".json"));
        	request.setURI(new URI("http://www.futurice.com"));
        	HttpClient client = new DefaultHttpClient();        	    
        	response = client.execute(request);        	    		
    	} catch (IOException e) {
    		Log.e(TAG, "IOException executing HTTP request: " + e.getMessage());
    	} catch (URISyntaxException e) {
    		Log.e(TAG, "Invalid URI syntax: " + e.getMessage());
			e.printStackTrace();
		}
    	
    	HttpEntity entity = response.getEntity();
    	String content = null;
    	if (entity != null) {
    		InputStream in = null;
			try {
				in = entity.getContent();
				content = getAsString(in);
			} catch (IllegalStateException e) {
				Log.e(TAG, "Illegal state encountered reading content: " + e.getMessage());
			} catch (IOException e) {
				Log.e(TAG, "I/O error reading content: " + e.getMessage());
			} finally {
				try {
					if (in != null) {
						in.close();
					}
				}
				catch (IOException ioe) {
					Log.e(TAG, "Could not close input stream: " + ioe.getMessage());
				}
			}
    	}    	
    	return content;
    }
    
    private String getAsString(InputStream in) {		
		StringBuilder sb = new StringBuilder();		
		String line = null;
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		try {						
			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			Log.e(TAG, "Could not read response: " + e.getMessage());
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException ioe) {
				Log.e(TAG, "Couldn't close reader: " + ioe.getMessage());
			}
		}
		
    	return sb.toString();
    }
}