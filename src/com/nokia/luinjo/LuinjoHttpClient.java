package com.nokia.luinjo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

public class LuinjoHttpClient {
	
	private static final String TAG  = "LuinjoHttpClient";

    public String getContent(String url) {
    	HttpGet request = new HttpGet();    	
		try {
			URI uri = new URI(url);
			request.setURI(uri);
		} catch (URISyntaxException e) {
			Log.e(TAG, "Invalid URL: " + e.getMessage());
			return null;
		}
    	return getContent(request);
    }
    
    // TODO: do data fetching in separate thread
    // TODO: configure charset etc. for client
    private String getContent(HttpUriRequest request) {
    	HttpResponse response = null;    	
    	try {        	
        	HttpClient client = new DefaultHttpClient();
        	Log.d(TAG, "Making request to " + request.getURI());
        	response = client.execute(request);        	    		
    	} catch (IOException e) {
    		Log.e(TAG, "IOException executing HTTP request: " + e.getMessage());
    		return null;
    	}
    	
    	StatusLine statusLine = response.getStatusLine();
    	if (statusLine.getStatusCode() != 200) {
    		Log.e(TAG, "Request wasn't completed successfully (status code " + statusLine.getStatusCode() + ")");
    		return null;
    	}
    	
    	HttpEntity entity = response.getEntity();
    	String content = null;
    	if (entity == null) {
    		return null;
    	}
    	
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
