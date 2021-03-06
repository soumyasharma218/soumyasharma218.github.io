package com.example.androidtask.network;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

@SuppressLint("NewApi") public class VolleySingleton {
	 private static VolleySingleton instance;
	    private RequestQueue requestQueue;
	    private ImageLoader imageLoader;
	 
	    private VolleySingleton(Context context) {
	        requestQueue = Volley.newRequestQueue(context);
	 
	        imageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {
	            @SuppressLint("NewApi") private final LruCache<String, Bitmap> cache = new LruCache<String, Bitmap>(20);
	 
	 
	            @Override
	            public Bitmap getBitmap(String url) {
	                return cache.get(url);
	            }
	 
	            @SuppressLint("NewApi") @Override
	            public void putBitmap(String url, Bitmap bitmap) {
	                cache.put(url, bitmap);
	            }
	        });
	    }
	 
	 
	    public static VolleySingleton getInstance(Context context) {
	        if (instance == null) {
	            instance = new VolleySingleton(context);
	        }
	        return instance;
	    }
	 
	    public RequestQueue getRequestQueue() {
	        return requestQueue;
	    }
	 
	    public ImageLoader getImageLoader() {
	        return imageLoader;
	    }
	 
	    public <T> void addToRequestQueue(Request<T> req) {
	        req.setTag("App");
	        getRequestQueue().add(req);
	    }
}
