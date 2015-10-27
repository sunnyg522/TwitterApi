package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class NewTweetActivity extends AppCompatActivity {

    private EditText etNewTweet;
    private Button btPostTweet;
    private int RESULT_OK = 200;
    private TextView tvCharCount;
    private int i = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_tweet);
        etNewTweet = (EditText)findViewById(R.id.etNewTweet);

        tvCharCount = (TextView)findViewById(R.id.tvCharCount);
        etNewTweet.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {
                i++;
                tvCharCount.setText(String.valueOf(etNewTweet.length()));
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_tweet, menu);
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





public void onPostTweet(View view) {

        String tweet = etNewTweet.getText().toString();
        Intent i = new Intent();
        i.putExtra("tweet", tweet);
        setResult(RESULT_OK, i);
        finish();
    }
}
