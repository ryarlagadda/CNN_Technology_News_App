package com.example.raviteja.NewsApp;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Advait on 26-09-2016.
 */
public class NewsItemUtil {
    static public class NewsItemPullParser {


        static ArrayList<NewsItem> parseNewsItem(InputStream in) throws XmlPullParserException, IOException {
            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setInput(in, "UTF-8");

            ArrayList<NewsItem> newsItemList = new ArrayList<NewsItem>();
            NewsItem newsItem = null;

            int event = parser.getEventType();

            while (event != XmlPullParser.END_DOCUMENT) {
                switch (event) {
                    case XmlPullParser.START_TAG:

                        if (parser.getName().equals("item")) {
                            newsItem = new NewsItem();

                        }
                       if(parser.getName().equals("media:content")){
                                newsItem.setUrl(parser.getAttributeValue(null,"url"));
                                Log.d("image",newsItem.getUrl());
                            }

                        if (newsItem != null) {
                            if(parser.getName().equals("title")) {
                                newsItem.setTitle(parser.nextText().trim());
                            }
                            else if (parser.getName().equals("description")) {
                                newsItem.setDescription(parser.nextText().trim());
                            } else if (parser.getName().equals("pubDate")) {


                                newsItem.setPuD(parser.nextText().trim());
//                                convertToDate(parser.nextText());
                            } else if (parser.getName().equals("link")) {
                                newsItem.setLink(parser.nextText().trim());
                            }

                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("item")) {
                            newsItemList.add(newsItem);
                            for(int i=1;i<newsItemList.size();i++){
                            String str_date1=newsItemList.get(i-1).getPuD();
                                String str_date2=newsItemList.get(i).getPuD();
                            //Log.d("date",str_date);
                            SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss z");

                            SimpleDateFormat sdf2 = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
                            try {
                                Date date1 = sdf.parse(str_date1);
                                Date date2 = sdf.parse(str_date2);
                                Log.d("list",newsItemList.get(i).getTitle().toString());
                                Log.d("date1",date1.toString());
                                Log.d("date2",date2.toString());

                                String date_req1 = sdf2.format(date1);
                                String date_req2 = sdf2.format(date2);

                                if(date2.compareTo(date1)>0)
                                {
                                    NewsItem temp_newsItem = newsItemList.get(i-1);
                                   newsItemList.set(i-1,newsItemList.get(i));
                                    newsItemList.set(i,temp_newsItem);
                                    Log.d("date1 inside loop",date1.toString());
                                    Log.d("date2 inside loop",date2.toString());
                                    i=0;
                                }

                                //Log.d("date changed",date.toString());

                                //Date final_date = (Date)sdf2.parse(date_req);

                            } catch (ParseException e) {
                                Log.d("error","date error");

                                e.printStackTrace();
                            }
                            }

                        }
                        break;

                }


                event = parser.next();

            }

            return newsItemList;
        }
    }
}
