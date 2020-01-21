package com.dvpie.utilities;

import android.os.Bundle;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

public class Amazon extends AppCompatActivity {
    public AmazonScraper aws_service;

    public final int PRODUCT_BOXES = 3;
    public TextView[] pdtTitles = new TextView[PRODUCT_BOXES], pdtPrices = new TextView[PRODUCT_BOXES];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amazon);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pdtTitles[0] = findViewById(R.id.aws_pd1_title);
        pdtTitles[1] = findViewById(R.id.aws_pd2_title);
        pdtTitles[2] = findViewById(R.id.aws_pd3_title);

        pdtPrices[0] = findViewById(R.id.aws_pd1_price);
        pdtPrices[1] = findViewById(R.id.aws_pd2_price);
        pdtPrices[2] = findViewById(R.id.aws_pd3_price);
    }
    public void populateView() {
        Looper.prepare();
        try {
            while(!aws_service.isReady) {
                Thread.sleep(100);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        for(int i = 0; i < 3; i++) {
            final int j = i;
            final String title = aws_service.getProductTitle(i);
            final String price = aws_service.getProductPrice(i);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    pdtTitles[j].setText(title);
                    pdtPrices[j].setText(price);
                }
            });

        }
    }
    public void searchAmazon(View view) {
        EditText searchTxt = findViewById(R.id.aws_search);
        final String query = searchTxt.getText().toString();

        //Clear keyboard from screen
        InputMethodManager inputMM = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        try {
            //May cause Null Pointer Exception if there is no keyboard
            inputMM.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        } catch (NullPointerException e) {
            System.out.println("There was no keyboard to clear. Moving on.");
        }





        Thread refresh = new Thread() {
            public void run() {
                aws_service = new AmazonScraper(query);
                populateView();
            }
        };
        refresh.start();
    }
}
