package com.dvpie.utilities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class News extends AppCompatActivity {


    public NewsData news;

    public TextView[] ArticleTitles = new TextView[5], ArticleAuthors = new TextView[5], ArticleDescriptions = new TextView[5],ArticleLinks = new TextView[5];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //Initialize all TextViews:

        //Titles:
        ArticleTitles[0] = findViewById(R.id.article1_title);
        ArticleTitles[1] = findViewById(R.id.article2_title);
        ArticleTitles[2] = findViewById(R.id.article3_title);
        ArticleTitles[3] = findViewById(R.id.article4_title);
        ArticleTitles[4] = findViewById(R.id.article5_title);
        //Authors:
        ArticleAuthors[0] = findViewById(R.id.article1_author);
        ArticleAuthors[1] = findViewById(R.id.article2_author);
        ArticleAuthors[2] = findViewById(R.id.article3_author);
        ArticleAuthors[3] = findViewById(R.id.article4_author);
        ArticleAuthors[4] = findViewById(R.id.article5_author);

        //Descriptions:
        ArticleDescriptions[0] = findViewById(R.id.article1_description);
        ArticleDescriptions[1] = findViewById(R.id.article2_description);
        ArticleDescriptions[2] = findViewById(R.id.article3_description);
        ArticleDescriptions[3] = findViewById(R.id.article4_description);
        ArticleDescriptions[4] = findViewById(R.id.article5_description);

        //Links:
        ArticleLinks[0] = findViewById(R.id.article1_link);
        ArticleLinks[1] = findViewById(R.id.article2_link);
        ArticleLinks[2] = findViewById(R.id.article3_link);
        ArticleLinks[3] = findViewById(R.id.article4_link);
        ArticleLinks[4] = findViewById(R.id.article5_link);

        news = new NewsData();

        Thread INIT = new Thread() {
            public void run() {
                populateView();
            }
        };

        INIT.start();
    }

    public void populateView() {
        Looper.prepare();
        try {
            while(!news.isReady()) {
                Thread.sleep(100);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        for(int i = 0; i < 5; i++){
            final String title = news.getArticleTitle(i);
            String author = news.getArticleAuthor(i);
            final String byLine = "By: " + author;
            final String description = news.getArticleDescription(i);
            final String strLink = news.getArticleLink(i);
            final int j = i;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ArticleLinks[j].setText(strLink);
                    ArticleTitles[j].setText(title);
                    ArticleAuthors[j].setText(byLine);
                    ArticleDescriptions[j].setText(description);
                }
            });


        }
    }
    public void searchNews(View view) {
        EditText searchTxt = findViewById(R.id.news_search);
        String query = searchTxt.getText().toString();

        //Clear keyboard from screen
        InputMethodManager inputMM = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        try {
            //May cause Null Pointer Exception if there is no keyboard
            inputMM.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        } catch (NullPointerException e) {
            System.out.println("There was no keyboard to clear. Moving on.");
        }


        query = news.formatSearch(query);
        news.searchNews(query);


        Thread refresh = new Thread() {
            public void run() {
                populateView();
            }
        };
        refresh.start();
    }

    public void goToArticle(View view) {
        //Turn the view passed in to our linear layout
        LinearLayout layout = (LinearLayout)view;
        //The child at the index 3 is our Link Child that we need
        View linkView = layout.getChildAt(3);
        TextView linkTXT = (TextView) linkView;
        //Get the Link to the article
        String link = linkTXT.getText().toString();
        if(link == null) {
            return;
        }
        //Start up browser
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        startActivity(browserIntent);
    }

}
