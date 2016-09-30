package com.example.raviteja.NewsApp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Advait on 26-09-2016.
 */
public class GetDataAsyncTask extends AsyncTask<String,Void,ArrayList<NewsItem>> {

    ProgressDialog progressDialog;
    MainActivity activity;
    ScrollView scrollView;

    public GetDataAsyncTask(MainActivity activity) { this.activity = activity;}
    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(activity);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMax(10000);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        super.onPreExecute();

    }

    @Override

    protected ArrayList<NewsItem> doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int statusCode = con.getResponseCode();
            if(statusCode == HttpURLConnection.HTTP_OK){
                InputStream in = con.getInputStream();
                return NewsItemUtil.NewsItemPullParser.parseNewsItem(in);

            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    protected void onPostExecute(ArrayList<NewsItem> newsItems) {
        super.onPostExecute(newsItems);
        scrollView = (ScrollView) activity.findViewById(R.id.scrollView_list);
        if(newsItems!=null)
            Log.d("demo", "onPostExecute: "+ newsItems.toString());
        progressDialog.dismiss();
        LinearLayout linearLayout = new LinearLayout(activity);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        for(final NewsItem newsItem : newsItems){
            LinearLayout linearLayout2 = new LinearLayout(activity);
            linearLayout2.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));


            TextView textView_item = new TextView(activity);
            textView_item.setText(newsItem.getTitle());
            textView_item.setTextSize(15);
            textView_item.setPadding(0, 5, 0, 5);
            ImageView imageView_item = new ImageView(activity);
            Picasso.with(activity).load(newsItem.getUrl()).into(imageView_item);
            textView_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, NewsDetails.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("ITEM_KEY",newsItem);
                    intent.putExtras(bundle);
                    activity.startActivity(intent);
                }
            });
            linearLayout2.addView(imageView_item);
            linearLayout2.addView(textView_item);
            linearLayout.addView(linearLayout2);
        }
        scrollView.addView(linearLayout);

    }


}