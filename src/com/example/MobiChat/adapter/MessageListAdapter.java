package com.example.MobiChat.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.MobiChat.R;
import com.example.MobiChat.entity.Friend;
import com.example.MobiChat.entity.Message;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: sergey
 * Date: 3/4/14
 * Time: 11:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class MessageListAdapter extends BaseAdapter {

    Context ctx;
    LayoutInflater lInflater;
    public List<Message> objects;

    public MessageListAdapter(Context context, List<Message> messageList) {
        ctx = context;
        objects = messageList;
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
            listItem = lInflater.inflate(R.layout.message_item, null);

        TextView tvOwnreMessage = (TextView) listItem.findViewById(R.id.txtViewOwnerMessage);
        TextView tvMessage = (TextView) listItem.findViewById(R.id.txtMessage);

        Message message = getMessageItem(position);

        tvOwnreMessage.setText(message.getFromUserName().toString());
        tvMessage.setText(message.getText());

        return listItem;
    }

    public Message getMessageItem(int position) {
        return ((Message) getItem(position));
    }

    public void addItem(Message message) {
        objects.add(new Message(message.getId(), message.getFromUserId(), message.getFromUserName(), message.getToUserId(), message.getToUserName(), message.getText(), message.getDateMessage()));
    }

    private Long getCurrentUserId() {
        SharedPreferences sPref = ctx.getApplicationContext().getSharedPreferences("currentUser", Context.MODE_PRIVATE);
        return sPref.getLong("id", 0);
    }
}
