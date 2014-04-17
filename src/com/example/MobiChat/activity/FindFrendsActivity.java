package com.example.MobiChat.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.example.MobiChat.R;
import com.example.MobiChat.adapter.FoundFriendsAdapter;
import com.example.MobiChat.adapter.FriendListAdapter;
import com.example.MobiChat.entity.Friend;
import com.example.MobiChat.utils.HttpClientHelper;
import org.json.JSONException;

import java.lang.reflect.Array;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: sergey
 * Date: 4/11/14
 * Time: 10:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class FindFrendsActivity extends ActionBarActivity implements FoundFriendsAdapter.ShowerDialog{
    private HttpClientHelper httpClientHelper = new HttpClientHelper();
    private ListView listViewFriendsList;
    private List<Friend> friendList;
    private FoundFriendsAdapter friendListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_friends);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.find_friends_menu, menu);

        ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                Toast.makeText(getApplicationContext(), "onClick", Toast.LENGTH_SHORT).show();
                return false;  //To change body of implemented methods use File | Settings | File Templates.
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String name) {
                findFriends(name);
                return false;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;  //To change body of implemented methods use File | Settings | File Templates.
            }
        });



        //bar.setNavigationMode(ActionBar.DISPLAY_SHOW_HOME);
        //bar.setSubtitle("RSS feed");
        //bar.setTitle("Search friends");
        bar.setIcon(R.drawable.login_page_icon);
        return true;
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

    private void findFriends(String name) {
        listViewFriendsList = (ListView) findViewById(R.id.listViewFoundFriendList);
        try {
            friendList = httpClientHelper.getFriendsListByName(name);
        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        SharedPreferences sPref = getSharedPreferences("currentUser", Context.MODE_PRIVATE);
        Long fromUserId = sPref.getLong("id", 0);



        //addAvatarToFriends(friendList);
        friendListAdapter = new FoundFriendsAdapter(getApplicationContext(), friendList, fromUserId, this);
        listViewFriendsList.setAdapter(friendListAdapter);

        /*listViewFriendsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Long idFriend = friendListAdapter.getFriendItem(position).getId();
                listener.itemClick(position, idFriend);
            }
        }); */
    }


    @Override
    public void showDialog(String text) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_alert_to_find_friends);
        dialog.setTitle("Information");

        // set the custom dialog components - text, image and button
        TextView textV = (TextView) dialog.findViewById(R.id.text);
        textV.setText(text);
        ImageView image = (ImageView) dialog.findViewById(R.id.image);
        image.setImageResource(R.drawable.info);

        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
