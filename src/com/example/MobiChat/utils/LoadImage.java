package com.example.MobiChat.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import com.example.MobiChat.R;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: sergey
 * Date: 4/7/14
 * Time: 10:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class LoadImage extends AsyncTask<Object, Void, Bitmap> {

    private ImageView imv;
    private Long friendId;

    public LoadImage(ImageView imv, Long friendId) {
        this.imv = imv;
        this.friendId = friendId;
    }

    @Override
    protected Bitmap doInBackground(Object... params) {
        Bitmap bitmap = null;

        HttpClientHelper httpClientHelper = new HttpClientHelper();
        byte[] fotoAsByte = httpClientHelper.getFotoByUserId(friendId);
        bitmap = BitmapFactory.decodeByteArray(fotoAsByte, 0, fotoAsByte.length);

        return bitmap;
    }
    @Override
    protected void onPostExecute(Bitmap result) {
        if(result != null && imv != null){
            imv.setVisibility(View.VISIBLE);
            imv.setImageBitmap(result);
        }else{
            imv.setImageResource(R.drawable.user_foto);
        }
    }

}
