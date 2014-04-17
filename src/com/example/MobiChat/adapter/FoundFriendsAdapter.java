package com.example.MobiChat.adapter;

import android.app.Activity;
import android.app.Dialog;
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
 * Date: 4/12/14
 * Time: 9:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class FoundFriendsAdapter extends BaseAdapter {
    Long currentUserId;
    Context ctx;
    LayoutInflater lInflater;
    public List<Friend> objects;
    private HttpClientHelper httpClientHelper = new HttpClientHelper();
    private Activity parentActivity;

    public FoundFriendsAdapter(Context context, List<Friend> friendList, Long fromUserId, Activity parentActivity) {
        this.ctx = context;
        this.objects = friendList;
        this.lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.currentUserId = fromUserId;
        this.parentActivity = parentActivity;
        showerDialog = (ShowerDialog) parentActivity;
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

    public interface ShowerDialog {
        public void showDialog(String text);
    }

    ShowerDialog showerDialog;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        FriendHolder holder = null;
        int pos = position;
        if (row == null) {
            row = lInflater.inflate(R.layout.found_friend_item, null);
            holder = new FriendHolder();

            holder.imageView = (ImageView) row.findViewById(R.id.imgFotoFf);
            holder.name = (TextView) row.findViewById(R.id.tv_name);
            holder.imageButton = (ImageButton) row.findViewById(R.id.sendInvite);

            row.setTag(holder);

        } else {
            holder = (FriendHolder) row.getTag();
        }


        //ImageView iv = (ImageView) row.findViewById(R.id.imgFoto);
        //TextView tvFirstName = (TextView) row.findViewById(R.id.tv_first_name);
        //TextView tvLastName = (TextView) row.findViewById(R.id.tv_last_name);
        //ImageButton imageButton = (ImageButton) row.findViewById(R.id.sendInvite);




        Friend friend = getFriendItem(position);
        holder.name.setText(friend.getFirstName() + " " + friend.getLastName());
        holder.setId(friend.getId());
        holder.setEmail(friend.getEmail());
        final FriendHolder finalHolder = holder;
        holder.getImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(ctx, String.valueOf(finalHolder.getId()), Toast.LENGTH_LONG).show();
                //showDialog();
                showerDialog.showDialog("Invation was sent seccessfuly!");
                httpClientHelper.sendInvite(currentUserId, finalHolder.getId());
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

    private void showDialog() {
        final Dialog dialog = new Dialog(parentActivity.getApplicationContext());
        dialog.setContentView(R.layout.dialog_alert_to_find_friends);
        dialog.setTitle("Information");

        // set the custom dialog components - text, image and button
        TextView text = (TextView) dialog.findViewById(R.id.text);
        text.setText("Invation was sent seccessfuly!");
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



    class FriendHolder {
        private Long id;
        private TextView name;
        private String email;
        private boolean onLine;
        private ImageButton imageButton;
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

        ImageButton getImageButton() {
            return imageButton;
        }

        void setImageButton(ImageButton imageButton) {
            this.imageButton = imageButton;
        }

        ImageView getImageView() {
            return imageView;
        }

        void setImageView(ImageView imageView) {
            this.imageView = imageView;
        }
    }
}
