package com.codepath.apps.restclienttemplate.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.codepath.apps.restclienttemplate.TwitterApp;
import com.codepath.apps.restclienttemplate.TwitterClient;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by dgunda on 11/2/15.
 */
public class MentionsTimelineFragment extends TweetsListFragment{
    private TwitterClient client;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client= TwitterApp.getRestClient();
        populateTimeLine(12);
    }

    private void populateTimeLine(int offset)
    {
        client.getMentionsTimeLine(offset, new JsonHttpResponseHandler() {
            //Success
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.d("DEBUG", response.toString());

                // ArrayList<Tweet> tweets = ;
                //aTweet.addAll("Test");
                //clear();
                addAll(Tweet.fromJsonArray(response));
                //Log.d("DEBUG", aTweet.toString());
                //fragmentTweetsList.getSwipeContainer().setRefreshing(false);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", errorResponse.toString());
                //fragmentTweetsList.getSwipeContainer().setRefreshing(false);
            }
        });
    }
}
