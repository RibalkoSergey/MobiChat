package com.example.MobiChat.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.MobiChat.R;
import com.example.MobiChat.entity.Friend;
import com.example.MobiChat.entity.Message;
import com.example.MobiChat.utils.HttpClientHelper;
import com.example.MobiChat.utils.ImageMeneger;
import com.example.MobiChat.utils.LoadImage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: sergey
 * Date: 3/4/14
 * Time: 11:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class FriendListAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    public List<Friend> objects;


    public FriendListAdapter(Context context, List<Friend> friendList) {
        ctx = context;
        objects = friendList;
        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        int pos = position;
        if (listItem == null)
            listItem = lInflater.inflate(R.layout.friend_item, null);

        ImageView iv = (ImageView) listItem.findViewById(R.id.imgFoto);
        ImageView ivMessafe = (ImageView) listItem.findViewById(R.id.friendMessage);
        TextView tvTitle = (TextView) listItem.findViewById(R.id.tv_name);
        TextView tvCount = (TextView) listItem.findViewById(R.id.count);

        Friend friend = getFriendItem(position);



        // byte[] photo = friend.getFoto();
        //byte[] photo = httpClientHelper.getFotoByUserId(friend.getId());
        //Bitmap bitmap = BitmapFactory.decodeByteArray(photo, 0, photo.length);
        //iv.setImageBitmap(bitmap);
        //iv.refreshDrawableState();
        //ImageMeneger.fetchImage(friend.getId(), iv);



        new LoadImage(iv, friend.getId()).execute();

        tvTitle.setText(friend.getFirstName() + " " + friend.getLastName());
        //tvDate.setText(friend.getLastName());
        tvCount.setText(String.valueOf(friend.getCountNotReadMessages()));
        if (friend.getCountNotReadMessages() > 0) {
            ivMessafe.setImageResource(R.drawable.message_true);
        } else {
            ivMessafe.setImageResource(R.drawable.message_false);
        }

        return listItem;
    }

    public Friend getFriendItem(int position) {
        return ((Friend) getItem(position));
    }

    public void addItem(Friend friend) {
        objects.add(new Friend(friend.getId(), friend.getFirstName(), friend.getLastName(), friend.getEmail(), friend.isOnLine(), null, friend.getCountNotReadMessages()));
    }

}
