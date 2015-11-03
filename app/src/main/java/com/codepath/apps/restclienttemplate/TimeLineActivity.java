package com.codepath.apps.restclienttemplate;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.text.format.DateUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;


import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.restclienttemplate.fragments.HomeTimelineFragment;
import com.codepath.apps.restclienttemplate.fragments.MentionsTimelineFragment;
import com.codepath.apps.restclienttemplate.fragments.TweetsListFragment;
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



    private TweetsListFragment fragmentTweetsList;
    private String new_tweet;
    private TwitterClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_line);
        ViewPager vpPager = (ViewPager)findViewById(R.id.viewpager);
        vpPager.setAdapter(new TweetsPagerAdapter(getSupportFragmentManager()));
        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip)findViewById(R.id.tabs);
        tabStrip.setViewPager(vpPager);
        client = TwitterApp.getRestClient();
    }




    private void postTweetOnTimeLine(String tweet)
    {
        client.postTweet(tweet, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.d("DEBUG", response.toString());
                //setView();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", errorResponse.toString());
            }
        });

    };


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


    public void onProfileView(MenuItem mi)
    {
        Intent i = new Intent(this, ProfileActivity.class);
        startActivity(i);

    }
    public void onNewTweet(MenuItem mi )
    {
        Toast.makeText(this, "Opening Settings", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(TimeLineActivity.this, NewTweetActivity.class);
        startActivityForResult(i, 200);
    }

    public class TweetsPagerAdapter extends FragmentPagerAdapter
    {
        final int PAGE_COUNT=2;
        private String tabTitles[] = {"Home","Mentions"};
        public TweetsPagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position==0)
                return new HomeTimelineFragment();
            else if(position==1)
                return new MentionsTimelineFragment();
            else
                return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }
    }
}
