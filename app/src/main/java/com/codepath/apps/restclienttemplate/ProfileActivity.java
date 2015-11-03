package com.codepath.apps.restclienttemplate;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.restclienttemplate.fragments.UserTimeLineFragment;
import com.codepath.apps.restclienttemplate.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

public class ProfileActivity extends AppCompatActivity {

    TwitterClient client;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        client= TwitterApp.getRestClient();
        client.getUserInfo(new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                user = User.fromJson(response);
                getSupportActionBar().setTitle(user.getScreeName());
                populateProfileHeader(user);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.d("DEBUG", errorResponse.toString());
            }
        });
//        client.getUserInfo(new JsonHttpResponseHandler(){
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//
//                user = User.fromJson(response);
//                getSupportActionBar().setTitle("@"+user.getScreeName());
//                populateProfileHeader(user);
//            }
//        });
        String screenName = getIntent().getStringExtra("screen_name");
        if(savedInstanceState==null) {
            UserTimeLineFragment fragmentUserTimeLine = UserTimeLineFragment.newInstance(screenName);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flContainer, fragmentUserTimeLine);
            ft.commit();
        }

    }
    public void populateProfileHeader(User user)
    {
        ImageView ivProfile = (ImageView) findViewById(R.id.ivProfileImage);
        TextView tvUser = (TextView)findViewById(R.id.tvUserName);
        TextView tvTagLine = (TextView)findViewById(R.id.tvTagLine);
        TextView tvFollowers = (TextView)findViewById(R.id.tvFollowers);
        TextView tvFollowing = (TextView)findViewById(R.id.tvFollowing);

        //Name.setText(user.getName());
        //tvRelativDate.setText(tweet.getCreateAt());

        tvTagLine.setText(user.getTagLine());
        tvFollowers.setText(user.getFollowersCount()+" Followers");
        tvUser.setText(user.getScreeName());
        tvFollowing.setText(user.getFollowIngCount()+" Following");
        ivProfile.setImageResource(android.R.color.transparent);
        Picasso.with(this).load(user.getProfileImageUrl()).into(ivProfile);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
