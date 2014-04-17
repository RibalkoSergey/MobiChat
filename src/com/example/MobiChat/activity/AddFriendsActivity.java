package com.example.MobiChat.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import com.example.MobiChat.R;
import com.example.MobiChat.adapter.InvitesAdapter;
import com.example.MobiChat.adapter.MessageListAdapter;
import com.example.MobiChat.entity.Friend;
import com.example.MobiChat.utils.HttpClientHelper;
import org.json.JSONException;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: sergey
 * Date: 4/13/14
 * Time: 1:10 AM
 * To change this template use File | Settings | File Templates.
 */
public class AddFriendsActivity extends ActionBarActivity {
    private HttpClientHelper httpClientHelper = new HttpClientHelper();
    private ListView listViewInvite;
    private InvitesAdapter invitesAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_friends);

        listViewInvite = (ListView)findViewById(R.id.listViewInvites);
        List<Friend> invites = null;
        try {
            invites = httpClientHelper.getInvitesByUserId(getCurrentUserId());
        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        invitesAdapter = new InvitesAdapter(getApplicationContext(), invites, getCurrentUserId());
        listViewInvite.setAdapter(invitesAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.find_friends_menu, menu);

        ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);

        //bar.setNavigationMode(ActionBar.DISPLAY_SHOW_HOME);
        //bar.setSubtitle("RSS feed");
        bar.setTitle("Invites");
        bar.setIcon(R.drawable.login_page_icon);
        return true;
    }

    private Long getCurrentUserId() {
        SharedPreferences sPref = getSharedPreferences("currentUser", Context.MODE_PRIVATE);
        return sPref.getLong("id", 0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent homeIntent = new Intent(this, MainActivity.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
