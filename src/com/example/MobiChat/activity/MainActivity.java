package com.example.MobiChat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.example.MobiChat.R;
import com.example.MobiChat.fragment.ActionDashboardFragment;
import com.example.MobiChat.fragment.FriendsListFragment;

/**
 * Created with IntelliJ IDEA.
 * User: sergey
 * Date: 2/14/14
 * Time: 10:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class MainActivity extends ActionBarActivity implements FriendsListFragment.onItemClickListener {
    boolean withDetails = true;
    int position = 0;
    Long selectedFriendId;
    String selectedFriendName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
        if (savedInstanceState != null) {
            position = savedInstanceState.getInt("position");
        }

        withDetails = (findViewById(R.id.cont) != null);

        if (withDetails) {
            showDetails(position, selectedFriendId, selectedFriendName);
        }
    }

    void showDetails(int pos, Long friendId, String friendName) {
        if (withDetails) {
            ActionDashboardFragment actionDashboardFragment = (ActionDashboardFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.cont);
            if (actionDashboardFragment == null || actionDashboardFragment.getPosition() != pos) {
                actionDashboardFragment = ActionDashboardFragment.newInstance(pos, friendId, friendName);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.cont, actionDashboardFragment).commit();
            }
        } else {
            startActivity(new Intent(this, ActionDashboardActivity.class).putExtra("position", position).putExtra("selectedFriendId", selectedFriendId).putExtra("selectedFriendName", selectedFriendName));
        }
    }

    @Override
    public void itemClick(int position, Long selectedFriendId, String selectedFriendName) {
        this.position = position;
        this.selectedFriendId = selectedFriendId;
        this.selectedFriendName = selectedFriendName;

        showDetails(position, selectedFriendId, selectedFriendName);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("position", position);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        ActionBar bar = getSupportActionBar();
        //bar.setDisplayHomeAsUpEnabled(true);

        //bar.setNavigationMode(ActionBar.DISPLAY_SHOW_HOME);
        bar.setSubtitle("Friends");
        bar.setTitle("Chat");
        bar.setIcon(R.drawable.login_page_icon);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_search:
                Intent intentFind = new Intent(getApplication().getApplicationContext() , FindFrendsActivity.class);
                startActivity(intentFind);

                break;
            case R.id.menu_list_requests:
                Intent intentAdd = new Intent(getApplication().getApplicationContext() , AddFriendsActivity.class);
                startActivity(intentAdd);
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
