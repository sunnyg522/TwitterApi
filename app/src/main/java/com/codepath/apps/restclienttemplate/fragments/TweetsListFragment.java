package com.codepath.apps.restclienttemplate.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.codepath.apps.restclienttemplate.EndlessScrollListener;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.TweetsArrayAdapter;
import com.codepath.apps.restclienttemplate.models.Tweet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dgunda on 11/1/15.
 */
public class TweetsListFragment extends Fragment{
    //inflation login

    public ListView lvTweets;
    public TweetsArrayAdapter aTweet;
    public ArrayList<Tweet> tweets;
    public View v;
    public SwipeRefreshLayout swipeContainer;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_tweets_list, parent, false);
        lvTweets = (ListView)v.findViewById(R.id.lvTimeLine);
        lvTweets.setAdapter(aTweet);
        swipeContainer = (SwipeRefreshLayout) v.findViewById(R.id.swipeContainer);
        return v;
    }

    //creation lifecycle.

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tweets = new ArrayList<>();

        aTweet = new TweetsArrayAdapter(getActivity(), tweets);
    }

    public void clear()
    {
        aTweet.clear();
    }
//    public SwipeRefreshLayout getSwipeContainer()
//    {
//        return swipeContainer;
//    }
    public void addAll(List<Tweet> tweets)
    {

        aTweet.addAll(tweets);

    }
}
