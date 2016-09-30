package com.example.advait.inclass06;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NewsDetails extends AppCompatActivity {
    ImageView imageView_thumbnail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        setTitle("News Details");

        TextView textView_title = (TextView) findViewById(R.id.story_title);
        TextView textView_date = (TextView) findViewById(R.id.story_date);
        imageView_thumbnail = (ImageView) findViewById(R.id.imageView);
        TextView textView_descriptiontext = (TextView) findViewById(R.id.content_text);
        final NewsItem newsItem;
        final WebView webView = new WebView(this);
        if (getIntent().getExtras() !=null){
            Bundle b = getIntent().getExtras();
            newsItem = (NewsItem) b.get("ITEM_KEY");
            textView_title.setText(newsItem.getTitle());

            String str_date=newsItem.getPuD();

            SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss z");

            SimpleDateFormat sdf2 = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
            Date date1 = null;
            try {
                date1 = sdf.parse(str_date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            String date_req1 = sdf2.format(date1);


            textView_date.setText(date_req1);
            textView_descriptiontext.setText(newsItem.getDescription());
            new GetImage().execute(newsItem.getUrl());



            imageView_thumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = newsItem.getLink();
                    webView.getSettings().setLoadsImagesAutomatically(true);
                    webView.getSettings().setJavaScriptEnabled(true);
                    webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                    webView.loadUrl(url);
                    webView.setWebViewClient(new MyWebViewClient());

                }
            });
        }

    }
    class GetImage extends AsyncTask<String, Void, Bitmap> {


        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                Bitmap image = BitmapFactory.decodeStream(con.getInputStream());
                return image;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null){
                imageView_thumbnail.setImageBitmap(bitmap);
            }
        }
    }
    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
            return true;
        }
    }
}
