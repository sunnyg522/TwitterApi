package com.codepath.apps.restclienttemplate.models;

import android.text.format.DateUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by dgunda on 10/24/15.
 */
public class Tweet {


    private String body;
    private long uid;
     private User user;
    private String createAt;

    public String getBody() {
        return body;
    }

    public long getUid() {
        return uid;
    }

    public String getCreateAt() {
        return createAt;
    }

    public User getUser() {
        return user;
    }

    public static Tweet fromJson(JSONObject jsonObject)
    {
        Tweet tweet = new Tweet();
        try{

            tweet.body = jsonObject.getString("text");
            tweet.uid = jsonObject.getLong("id");
            //tweet.createAt = jsonObject.getString("created_at");
            tweet.createAt = getRelativeTimeAgo(jsonObject.getString("created_at"));
            tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
        }catch (JSONException e)
        {
            e.printStackTrace();
        }


        return tweet;
    }

    public static ArrayList<Tweet> fromJsonArray(JSONArray json) {

        ArrayList<Tweet> tweets  = new ArrayList<Tweet>();

        for(int i =0; i<json.length();i++ )
        {
            try {
                JSONObject tweetJson = json.getJSONObject(i);
                Tweet tweet = Tweet.fromJson(tweetJson);
                if(tweet != null)
                {
                    tweets.add(tweet);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }
            //tweets.add(json.getJSONArray(""));
        }

        return tweets;
    }

    public static String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }
}
