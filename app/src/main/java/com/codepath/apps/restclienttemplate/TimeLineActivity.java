package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.text.format.DateUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;


public class TimeLineActivity extends AppCompatActivity {

    private TwitterClient client;
    private ListView lvTweets;
    private TweetsArrayAdapter aTweet;
    private ArrayList<Tweet> tweets;
    private SwipeRefreshLayout swipeContainer;
    private String new_tweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_line);
        client=TwitterApp.getRestClient();
        populateTimeLine(12);
        setView();

    }

    private void setView()
    {

        lvTweets = (ListView)findViewById(R.id.lvTimeLine);

        tweets = new ArrayList<>();

        aTweet = new TweetsArrayAdapter(this, tweets);


        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                populateTimeLine(13);
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        lvTweets.setAdapter(aTweet);
        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                Log.i("Debug", "page number:" + page + "totalItemsCount" + totalItemsCount);
                int offset = page * 8;
                populateTimeLine(offset);
                return true;
            }
        });
    }

    private void populateTimeLine(int offset)
    {
        client.getHomeTimeLine(offset, new JsonHttpResponseHandler() {
            //Success
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.d("DEBUG", response.toString());

                // ArrayList<Tweet> tweets = ;
                //aTweet.addAll("Test");
                aTweet.clear();
                aTweet.addAll(Tweet.fromJsonArray(response));
                Log.d("DEBUG", aTweet.toString());
                swipeContainer.setRefreshing(false);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", errorResponse.toString());
                swipeContainer.setRefreshing(false);
            }
        });
    }
    private void postTweetOnTimeLine(String tweet)
    {
        client.postTweet(tweet, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.d("DEBUG", response.toString());
                setView();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", errorResponse.toString());
            }
        });

    };

    public String getRelativeTimeAgo(String rawJsonDate) {
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        new_tweet = data.getStringExtra("tweet");
        Toast.makeText(this, new_tweet, Toast.LENGTH_SHORT).show();
        postTweetOnTimeLine(new_tweet);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_time_line, menu);
        return true;
    }
    public void showSettingsScreen() {
        Toast.makeText(this, "Opening Settings", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(TimeLineActivity.this, NewTweetActivity.class);
        startActivityForResult(i, 200);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_settings:
                showSettingsScreen();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
