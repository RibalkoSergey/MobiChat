package com.example.MobiChat.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import com.example.MobiChat.R;
import com.example.MobiChat.fragment.ActionDashboardFragment;

/**
 * Created with IntelliJ IDEA.
 * User: sergey
 * Date: 2/17/14
 * Time: 11:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class ActionDashboardActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE
                && isLarge()) {
            finish();
            return;
        }

        if (savedInstanceState == null) {
            ActionDashboardFragment details = ActionDashboardFragment.newInstance(
                    getIntent().getIntExtra("position", 0), getIntent().getLongExtra("selectedFriendId", 0), getIntent().getStringExtra("selectedFriendName"));
            getSupportFragmentManager().beginTransaction().add(android.R.id.content, details).commit();
        }
    }

    boolean isLarge() {
        return (getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        //getMenuInflater().inflate(R.menu.find_friends_menu, menu);
//
//        ActionBar bar = getSupportActionBar();
//        bar.setDisplayHomeAsUpEnabled(true);
//
//        //bar.setNavigationMode(ActionBar.DISPLAY_SHOW_HOME);
//        //bar.setSubtitle("RSS feed");
//        bar.setTitle("Meesages");
//        bar.setIcon(R.drawable.login_page_icon);
//        return true;
//    }

}
