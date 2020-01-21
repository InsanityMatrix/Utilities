package com.dvpie.utilities;

import android.util.JsonWriter;
import android.util.Log;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
//TODO: Make Working Amazon Scraper API
public class AmazonScraper {
    private String generatedJSON;
    public boolean isReady = false;
    private JSONObject gson;


    public AmazonScraper(String query) {
        if(query.contains(" "))
            query = query.replaceAll(" ", "+").toLowerCase();

        String url = "https://www.amazon.com/s?k=" + query;
        scrapeForProducts(url);
    }
    private void scrapeForProducts(String url) {
        try {
            //Connect to website: may throw IOException
            Document amazonPage = Jsoup.connect(url).get();
            Element resultList = amazonPage.select("div.s-result-list").first();


            JSONObject genJSON = new JSONObject();
            Elements firstProducts = resultList.children();
            //The first three children are products.
            for(int product = 0; product < 3; product++) {

                JSONObject prod = new JSONObject();
                Element prodInfo = firstProducts.get(product).select("div.a-section").get(0).child(1);

                Element titleElmnt = prodInfo.select("span.a-text-normal").first();
                Element priceElmnt = prodInfo.select("span.a-price").first().child(0);
                Element linkElment = titleElmnt.parent();

                String prodLink = linkElment.attr("abs:href");

                String prodTitle = titleElmnt.text();
                String prodPrice = priceElmnt.text();



                prod.put("title",prodTitle);
                prod.put("price", prodPrice);
                prod.put("link",prodLink);

                genJSON.put("product" + product, prod);
            }

            this.gson = new JSONObject(genJSON.toString());
            this.generatedJSON = genJSON.toString();
            this.isReady = true;
        } catch (Exception e) {
            Log.d("JSoup Error", e.getMessage());
        }
    }

    public String getProductTitle(int index) {
        try {
            String title = this.gson.getJSONObject("product" + index).getString("title");
            return title;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getProductPrice(int index) {
        try {
            String title = this.gson.getJSONObject("product" + index).getString("price");
            return title;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public String getProductLink(int index) {
        try {
            String title = this.gson.getJSONObject("product" + index).getString("link");
            return title;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
