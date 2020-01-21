package com.dvpie.utilities;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class NewsData {
    private String _newsKey = "cbf0a69de20e446296cb711a9014bb33";
    private String _newsLink = "https://newsapi.org/v2/everything";
    private boolean isReady = false;


    private String JSONresponse;
    private JSONObject newsData;
    private JSONObject[] topArticles;

    public NewsData() {
        String apiKey = "apiKey=" + _newsKey;
        String flags = "?sortBy=popularity&q=united%20states&" + apiKey;
        String url = _newsLink + flags;
        final String urL = url;
        Thread retr = new Thread(){
            public void run() {
                retrieveNews(urL);
            }
        };
        retr.start();
    }

    public void searchNews(String query) {
        this.isReady = false;
        String apiKey = "apiKey=" + _newsKey;
        String flags = "?sortBy=popularity&q=";
        flags += query + "&" + apiKey;
        final String url = _newsLink + flags;
        Thread retr = new Thread() {
            public void run() {
                retrieveNews(url);
            }
        };
        retr.start();
    }

    public String formatSearch(String search) {
        if(search.contains(" ")) {
            search = search.replaceAll(" ", "%20");
        }
        if(search.contains("&")) {
            search = search.replaceAll("&", "And");
        }
        return search;
    }

    private void retrieveNews(String UrlConn) {
        try {
            this.isReady = false;
            URL urlCon = new URL(UrlConn);
            URLConnection request = urlCon.openConnection();
            request.connect();

            InputStreamReader is = new InputStreamReader(request.getInputStream());
            BufferedReader br = new BufferedReader(is);
            String JSON = "";
            String inputLine;
            if((inputLine = br.readLine()) != null) {
                JSON += inputLine +"\n";
            } else {
                System.out.println("It was null");
            }
            while((inputLine = br.readLine()) != null) {
                JSON += inputLine + "\n";
            }
            System.out.println(JSON);
            newsData = new JSONObject(JSON);
            topArticles = new JSONObject[5];
            JSONArray articles = newsData.getJSONArray("articles");
            for(int i = 0; i < 5; i++) {
                topArticles[i] = articles.getJSONObject(i);
            }

            this.isReady = true;
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    public boolean isReady() {
        return this.isReady;
    }

    public String getArticleTitle(int index) {
        try {
            return newsData.getJSONArray("articles").getJSONObject(index).getString("title");
        } catch( JSONException e) {
            Log.d("JSON ERROR:", e.getMessage());
            return "Error Fetching Resource (Title)";
        }
    }
    public String getArticleAuthor(int index) {
        try {
            return newsData.getJSONArray("articles").getJSONObject(index).getString("author");
        } catch(JSONException e) {
            Log.d("JSON ERROR:", e.getMessage());
            return "Error Fetching Resource (Author)";
        }
    }

    public String getArticleDescription(int index) {
        try {
            return newsData.getJSONArray("articles").getJSONObject(index).getString("description");
        } catch(JSONException e) {
            Log.d("JSON ERROR:", e.getMessage());
            return "Error Fetching Resource (Description)";
        }
    }
    public String getImageLink(int index) {
        try {
            return newsData.getJSONArray("articles").getJSONObject(index).getString("urlToImage");
        } catch(JSONException e) {
            Log.d("JSON ERROR:", e.getMessage());
            return "Error Fetching Resource (Image Link)";
        }
    }
    public String getArticleLink(int index) {
        try {
            return newsData.getJSONArray("articles").getJSONObject(index).getString("url");
        } catch(JSONException e) {
            Log.d("JSON ERROR:", e.getMessage());
            return "Error Fetching Resource (URL)";
        }
    }

    @Override
    public String toString() {
        if (newsData != null)
            return newsData.toString();
        else
            return null;
    }
}
