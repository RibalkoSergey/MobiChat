package com.example.MobiChat.utils;

import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import com.example.MobiChat.R;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

/**
 * Created with IntelliJ IDEA.
 * User: sergey
 * Date: 4/7/14
 * Time: 9:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class ImageMeneger extends AsyncTask<Object, Void, byte[]> {

        private byte[] foto;
        private Long friendId;

        public ImageMeneger(byte[] foto, Long friendId) {
            this.foto = foto;
            this.friendId = friendId;
        }

        @Override
        protected byte[] doInBackground(Object... params) {
            Bitmap bitmap = null;

            HttpClientHelper httpClientHelper = new HttpClientHelper();
            byte[] fotoAsByte = httpClientHelper.getFotoByUserId(friendId);


            return fotoAsByte;
        }
        @Override
        protected void onPostExecute(byte[] result) {

        }

    }
