package com.example.MobiChat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.MobiChat.R;
import com.example.MobiChat.entity.Friend;
import com.example.MobiChat.utils.HttpClientHelper;
import com.example.MobiChat.utils.LoadImage;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: sergey
 * Date: 4/13/14
 * Time: 1:13 AM
 * To change this template use File | Settings | File Templates.
 */
public class InvitesAdapter extends BaseAdapter {
    Long currentUserId;
    Context ctx;
    LayoutInflater lInflater;
    public List<Friend> objects;
    private HttpClientHelper httpClientHelper = new HttpClientHelper();


    public InvitesAdapter(Context context, List<Friend> friendList, Long fromUserId) {
        ctx = context;
        objects = friendList;
        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        currentUserId = fromUserId;
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
        View row = convertView;
        FriendHolder holder = null;
        int pos = position;
        if (row == null) {
            row = lInflater.inflate(R.layout.invite_item, null);
            holder = new FriendHolder();

            holder.imageView = (ImageView) row.findViewById(R.id.inviteFofo);
            holder.name = (TextView) row.findViewById(R.id.tv_name);
            holder.imageButtonAdd = (ImageButton) row.findViewById(R.id.inviteAdd);
            holder.imageButtonCancel = (ImageButton) row.findViewById(R.id.inviteReject);
            row.setTag(holder);

        } else {
            holder = (FriendHolder) row.getTag();
        }

        final Friend friend = getFriendItem(position);
        holder.name.setText(friend.getFirstName() + " " + friend.getLastName());
        holder.setId(friend.getId());
        holder.setEmail(friend.getEmail());
        final FriendHolder finalHolder = holder;
        holder.imageButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(ctx, String.valueOf(finalHolder.getId()), Toast.LENGTH_LONG).show();
                httpClientHelper.AddToFriend(currentUserId, finalHolder.getId());
                removeItem(friend);
                notifyDataSetChanged();

            }
        });

        holder.imageButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(ctx, String.valueOf(finalHolder.getId()), Toast.LENGTH_LONG).show();
                httpClientHelper.deteleInvite(currentUserId, finalHolder.getId());
                removeItem(friend);
                notifyDataSetChanged();
            }
        });

        new LoadImage(holder.getImageView(), friend.getId()).execute();

        //tvFirstName.setText(friend.getFirstName());
        //tvLastName.setText(friend.getLastName());
        //tvDate.setText(String.valueOf(friend.getCountNotReadMessages()));

        return row;
    }

    public Friend getFriendItem(int position) {
        return ((Friend) getItem(position));
    }

    public void addItem(Friend friend) {
        objects.add(new Friend(friend.getId(), friend.getFirstName(), friend.getLastName(), friend.getEmail(), friend.isOnLine(), null, friend.getCountNotReadMessages()));
    }

    public void removeItem(Friend friend) {
        objects.remove(friend);
    }

    class FriendHolder {
        private Long id;
        private TextView name;
        private String email;
        private boolean onLine;
        private ImageButton imageButtonAdd;
        private ImageButton imageButtonCancel;
        private ImageView imageView;

        Long getId() {
            return id;
        }

        void setId(Long id) {
            this.id = id;
        }

        TextView getName() {
            return name;
        }

        void setName(TextView name) {
            this.name = name;
        }

        String getEmail() {
            return email;
        }

        void setEmail(String email) {
            this.email = email;
        }

        boolean isOnLine() {
            return onLine;
        }

        void setOnLine(boolean onLine) {
            this.onLine = onLine;
        }

        ImageButton getImageButtonAdd() {
            return imageButtonAdd;
        }

        void setImageButtonAdd(ImageButton imageButtonAdd) {
            this.imageButtonAdd = imageButtonAdd;
        }

        ImageButton getImageButtonCancel() {
            return imageButtonCancel;
        }

        void setImageButtonCancel(ImageButton imageButtonCancel) {
            this.imageButtonCancel = imageButtonCancel;
        }

        ImageView getImageView() {
            return imageView;
        }

        void setImageView(ImageView imageView) {
            this.imageView = imageView;
        }
    }
}
