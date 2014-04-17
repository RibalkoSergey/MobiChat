package com.example.MobiChat.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ListView;
import com.example.MobiChat.R;
import com.example.MobiChat.adapter.FriendListAdapter;
import com.example.MobiChat.entity.Friend;
import com.example.MobiChat.utils.HttpClientHelper;
import com.example.MobiChat.utils.ImageMeneger;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created with IntelliJ IDEA.
 * User: sergey
 * Date: 2/17/14
 * Time: 10:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class FriendsListFragment extends Fragment {
    private HttpClientHelper httpClientHelper = new HttpClientHelper();
    private List<Friend> friendList;
    private ListView listViewFriendsList;
    private FriendListAdapter friendListAdapter;
    private View v;
    private Timer timerUpdateListfriends;
    private FriendListLoader friendListLoader;

    public interface onItemClickListener {
        public void itemClick(int position, Long friendId, String friendName);
    }

    onItemClickListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.friend_list, null);
        //timerStart();

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        //timerStart();
    }

    @Override
    public void onStart() {
        super.onStart();
        timerUpdateListfriends = new Timer();
        timerStart();
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listViewFriendsList = (ListView)v.findViewById(R.id.listViewFriendList);
        try {
            friendList = httpClientHelper.getFriendsListByUserId(getCurrentUserId());
        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


        friendListAdapter = new FriendListAdapter(v.getContext(), friendList);
        listViewFriendsList.setAdapter(friendListAdapter);
        listViewFriendsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Long friendId = friendListAdapter.getFriendItem(position).getId();
                String friendName = friendListAdapter.getFriendItem(position).getFirstName();
                listener.itemClick(position, friendId, friendName);
            }
        });
        /*listViewFriendsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Long idFriend = friendListAdapter.getFriendItem(position).getId();
                listener.itemClick(position, idFriend);
            }

        });*/

        //timerStart();
    }

    private void addAvatarToFriends(List<Friend> friendList) {
        for(Friend friend : friendList) {
            //friend.setFoto(new ImageMeneger(friend.getFoto(), friend.getId()).execute(););
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, new String[] {"111", "222", "333", "444"});

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (onItemClickListener) activity;
    }

    private void timerStart() {
        timerUpdateListfriends.schedule(new TimerTask() {
            private Handler handler = new Handler(Looper.getMainLooper());

            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        friendListLoader = new FriendListLoader();
                        friendListLoader.execute();
                    }
                });
            }
        }, 30000L, 1000 * 30);
    }

    class FriendListLoader extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... params) {
            List<Friend> friendList = new ArrayList<Friend>();

            try {
                friendList = httpClientHelper.getFriendsListByUserId(getCurrentUserId());
            } catch (JSONException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

            for (Friend friend : friendList) {
                List<Friend> friendListAd = friendListAdapter.objects;
                if (!friendListAd.contains(friend)) {
                    friendListAdapter.addItem(friend);
                }

                //friendListAd.get(0).setCountNotReadMessages(10L);
                //friendListAd.get(1).setCountNotReadMessages(5L);


                friendListAd.get(friendListAd.indexOf(friend)).setOnLine(friend.isOnLine());
                friendListAd.get(friendListAd.indexOf(friend)).setCountNotReadMessages(friend.getCountNotReadMessages());

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            friendListAdapter.notifyDataSetChanged();
            //listViewFriendsList.refreshDrawableState();
            //listViewFriendsList.setSelection(listViewFriendsList.getAdapter().getCount() - 1);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        timerUpdateListfriends.cancel();
    }

    private Long getCurrentUserId() {
        SharedPreferences sPref = getActivity().getSharedPreferences("currentUser", Context.MODE_PRIVATE);
        return sPref.getLong("id", 0);
    }



}
