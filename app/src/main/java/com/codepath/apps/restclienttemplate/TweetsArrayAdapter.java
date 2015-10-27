package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by dgunda on 10/24/15.
 */
public class TweetsArrayAdapter extends ArrayAdapter {

    public TweetsArrayAdapter(Context context, List<Tweet> tweets) {
        super(context,0, tweets);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Tweet tweet = (Tweet) getItem(position);

        if(convertView == null )
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent,false);
        }
        ImageView ivProfile = (ImageView) convertView.findViewById(R.id.ivProfileImage);
        TextView tvUser = (TextView)convertView.findViewById(R.id.tvUserName);
        TextView tvBody = (TextView)convertView.findViewById(R.id.tvBody);
        TextView tvRelativDate = (TextView)convertView.findViewById(R.id.tvTimestamp);
        TextView Name = (TextView)convertView.findViewById(R.id.textView);


        Log.d("DEBUG", tweet.getUser().getScreeName() );

        Name.setText(tweet.getUser().getName());
        tvRelativDate.setText(tweet.getCreateAt());
        tvUser.setText(tweet.getUser().getScreeName());
        tvBody.setText(tweet.getBody());

        ivProfile.setImageResource(android.R.color.transparent);
        Picasso.with(getContext()).load(tweet.getUser().getProfileImageUrl()).into(ivProfile);
        return  convertView;
    }
}
