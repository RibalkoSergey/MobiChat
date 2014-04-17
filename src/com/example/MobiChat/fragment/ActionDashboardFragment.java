package com.example.MobiChat.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.MobiChat.R;
import com.example.MobiChat.adapter.MessageListAdapter;
import com.example.MobiChat.entity.Message;
import com.example.MobiChat.utils.HttpClientHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created with IntelliJ IDEA.
 * User: sergey
 * Date: 2/17/14
 * Time: 11:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class ActionDashboardFragment extends Fragment {
    private HttpClientHelper httpClientHelper = new HttpClientHelper();
    private Button btnSendMessage;
    private ListView listViewMessageList;
    private MessageListAdapter friendListAdapter;
    private Timer timer = new Timer();
    private Context context = getActivity();
    private MyTask mt;

    public static ActionDashboardFragment newInstance(int pos, Long selectedFriendId, String selectedFriendName) {
        ActionDashboardFragment details = new ActionDashboardFragment();
        Bundle args = new Bundle();
        args.putInt("position", pos);
        args.putLong("selectedFriendId", selectedFriendId);
        args.putString("selectedFriendName", selectedFriendName);
        details.setArguments(args);
        return details;
    }

    public int getPosition() {
        return getArguments().getInt("position", 0);
    }

    public Long getSelectedFriendId() {
        return getArguments().getLong("selectedFriendId", 0);
    }

    public String getSelectedFriendName() {
        return getArguments().getString("selectedFriendName", "friend");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.action_dashboard, container, false);
        btnSendMessage = (Button) v.findViewById(R.id.btnSendMessage);
        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li = LayoutInflater.from(getActivity().getApplicationContext());
                View promptsView = li.inflate(R.layout.dialog_send_message, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText userInput = (EditText) promptsView
                        .findViewById(R.id.txtEditMessage);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        sendMessage(userInput.getText().toString());
                                        friendListAdapter.addItem(new Message(null, getCurrentUserId(), getCurrentUserName(), getSelectedFriendId(), null, userInput.getText().toString(), null));
                                        listViewMessageList.refreshDrawableState();
                                        listViewMessageList.setSelection(listViewMessageList.getAdapter().getCount()-1);
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            }
        });

        listViewMessageList = (ListView)v.findViewById(R.id.listViewMessages);
        List<Message> messages = getNotReadMessage();
        friendListAdapter = new MessageListAdapter(v.getContext(), messages);
        listViewMessageList.setAdapter(friendListAdapter);

        List<Long> listIds = new ArrayList<Long>();
        for (Message message : messages) {
            listIds.add(message.getId());
        }
        httpClientHelper.markMessagesAsRead(listIds.toArray(new Long[listIds.size()]));

        timerStart();

        return v;
    }

    private void sendMessage(String messageTxt) {

        SharedPreferences sPref = getActivity().getSharedPreferences("currentUser", Context.MODE_PRIVATE);
        Long toUserId = getSelectedFriendId();
        Long fromUserId = sPref.getLong("id", 0);

        Message message = new Message(null, fromUserId, toUserId, messageTxt, null);
        httpClientHelper.sendMessage(message);
//        Toast.makeText(getActivity(), savedText, Toast.LENGTH_SHORT).show();
//        Toast.makeText(getActivity(), String.valueOf(getSelectedFriendId()), Toast.LENGTH_SHORT).show();
    }

    private List<Message> getNotReadMessage() {
        SharedPreferences sPref = getActivity().getSharedPreferences("currentUser", Context.MODE_PRIVATE);
        Long fromUserId = getSelectedFriendId();
        Long toUserId = sPref.getLong("id", 0);

        return httpClientHelper.getNotReadMessages(fromUserId, toUserId);
    }

    private void timerStart() {
        timer.schedule(new TimerTask() {
            private Handler handler = new Handler(Looper.getMainLooper());

            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        mt = new MyTask();
                        mt.execute();
                    }
                });
            }
        }, 10000L, 1000 * 15);
    }

    class MyTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... params) {
            List<Long> listIds = new ArrayList<Long>();
            List<Message> messages = getNotReadMessage();
            for (Message message : messages) {
                friendListAdapter.addItem(message);
                listIds.add(message.getId());
            }
            if (messages.size() > 0) {
                httpClientHelper.markMessagesAsRead(listIds.toArray(new Long[listIds.size()]));
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            listViewMessageList.refreshDrawableState();
            listViewMessageList.setSelection(listViewMessageList.getAdapter().getCount() - 1);

        }
    }

    @Override
    public void onStop() {
        super.onStop();
        timer.cancel();
    }

    private Long getCurrentUserId() {
        SharedPreferences sPref = getActivity().getSharedPreferences("currentUser", Context.MODE_PRIVATE);
        return sPref.getLong("id", 0);
    }

    private String getCurrentUserName() {
        SharedPreferences sPref = getActivity().getSharedPreferences("currentUser", Context.MODE_PRIVATE);
        return sPref.getString("login", "My");
    }

}
