package com.example.MobiChat.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.example.MobiChat.R;
import com.example.MobiChat.entity.User;
import com.example.MobiChat.utils.HttpClientHelper;
import com.example.MobiChat.utils.HttpClientSingle;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.collections4.map.MultiKeyMap;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.support.Base64;
import org.springframework.web.client.RestTemplate;


import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: sergey
 * Date: 2/12/14
 * Time: 11:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class RegistrationActivity extends Activity {
    private Context self = this;
    private HttpClient httpClient;
    private Button btnSignIn;
    private Button btnLoad;
    private EditText edTxtFirstName;
    private EditText edTxtLastName;
    private EditText edTxtEmail;
    private EditText edTxtLogin;
    private EditText editTxtPassword;
    private ImageView imageView;
    private HttpClientHelper httpClientHelper;
    final Context context = this;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_page);
        httpClient = HttpClientSingle.getInstance();
        httpClientHelper = new HttpClientHelper();

        initEelmentOfPage();
        setActions();
    }

    private void initEelmentOfPage() {
        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        //btnLoad = (Button) findViewById(R.id.load);
        edTxtFirstName = (EditText) findViewById(R.id.edTxtFirstName);
        edTxtLastName = (EditText) findViewById(R.id.edTxtLastName);
        edTxtEmail = (EditText) findViewById(R.id.edTxtEmail);
        edTxtLogin = (EditText) findViewById(R.id.edTxtLogin);
        editTxtPassword = (EditText) findViewById(R.id.editTxtPassword);
        imageView = (ImageView) findViewById(R.id.imViewPhoto);
    }

    private void setActions() {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 100);
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = null;


                showDialogToFindFriends();


                BitmapDrawable bitmapDrawable = ((BitmapDrawable) imageView.getDrawable());
                Bitmap bitmap = bitmapDrawable .getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG , 100, stream);
                byte[] imageInByte = stream.toByteArray();



                String image_str = Base64.encodeBytes(imageInByte);
                String first_name = edTxtFirstName.getText().toString();
                String last_name = edTxtLastName.getText().toString();
                String login = edTxtLogin.getText().toString();
                String password = editTxtPassword.getText().toString();
                String email = edTxtEmail.getText().toString();

                try {
                    user = httpClientHelper.registration(image_str, first_name, last_name, login, password, email);
                } catch (JSONException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }

                if (user != null) {
                    saveUserToLocalStore(user);
                    showDialogToFindFriends();
                }








                // работающая штука
                /*ArrayList<NameValuePair> nameValuePairs = new  ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("image",image_str));
                nameValuePairs.add(new BasicNameValuePair("first_name", edTxtFirstName.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("last_name", edTxtLastName.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("login", edTxtLogin.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("password", editTxtPassword.getText().toString()));
                nameValuePairs.add(new BasicNameValuePair("email", edTxtEmail.getText().toString()));

                HttpPost httppost = new HttpPost("http://109.227.103.150:8080/chat/administration/register1");
                try {
                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }

                try {
                    HttpResponse response = httpClient.execute(httppost);
                    Toast.makeText(getApplicationContext(), "!!!", Toast.LENGTH_SHORT);
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } */

                //startFriendsListActivity();
            }
        });


        /*btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // -------------------------- get image ---------------------

                 //-------- работает ----------

                String s = "";
                HttpResponse response = null;
                HttpPost httppost = new HttpPost("http://37.139.177.111:8080/chat/administration/register2");
                try {
                    response = httpClient.execute(httppost);
                    s = EntityUtils.toString(response.getEntity(), "UTF-8");
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }


                byte[] bytes = new byte[0];
                try {
                    bytes = Base64.decode(s);
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                imageView.setImageBitmap(bitmap);
                imageView.refreshDrawableState();









                String result = "";
                JSONObject jsonObject = null;
                InputStream is = null;
                String s = "";
                HttpResponse response = null;

                HttpPost httppost = new HttpPost("http://37.139.177.111:8080/chat/administration/register4");
                try {
                    response = httpClient.execute(httppost);
                    HttpEntity entity = response.getEntity();
                    is = entity.getContent();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(is,"utf-8"),8);
                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    is.close();
                    result = sb.toString();
                    jsonObject = new JSONObject(result);
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (JSONException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }

                assert jsonObject != null;
                try {
                    s = jsonObject.getString("foto");
                } catch (JSONException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }

                byte[] bytes = new byte[0];
                try {
                    bytes = Base64.decode(s);
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                imageView.setImageBitmap(bitmap);
                imageView.refreshDrawableState();


            }

        }); */
    }

    private void showDialogToFindFriends() {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_alert_to_find_friends);
        dialog.setTitle("Information");

        // set the custom dialog components - text, image and button
        TextView text = (TextView) dialog.findViewById(R.id.text);
        text.setText("Your friend's list is empty!");
        ImageView image = (ImageView) dialog.findViewById(R.id.image);
        image.setImageResource(R.drawable.info);

        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFindFriendsListActivity();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void startFriendsListActivity() {
        Intent intent = new Intent(self , MainActivity.class);
        startActivity(intent);
    }

    private void startFindFriendsListActivity() {
        Intent intent = new Intent(self , FindFrendsActivity.class);
        startActivity(intent);
    }
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch(requestCode) {
            case 100:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(
                            selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String filePath = cursor.getString(columnIndex);
                    cursor.close();

                    ImageView imageView = (ImageView) findViewById(R.id.imViewPhoto);

                    //Bitmap yourSelectedImage = BitmapFactory.decodeFile(filePath);
                    Bitmap yourSelectedImage = ShrinkBitmap(filePath, 100, 100);
                    imageView.setImageBitmap(yourSelectedImage);

                }
        }
    }

    private Bitmap ShrinkBitmap(String file, int width, int height){

        BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
        bmpFactoryOptions.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(file, bmpFactoryOptions);

        int heightRatio = (int)Math.ceil(bmpFactoryOptions.outHeight/(float)height);
        int widthRatio = (int)Math.ceil(bmpFactoryOptions.outWidth/(float)width);

        if (heightRatio > 1 || widthRatio > 1)
        {
            if (heightRatio > widthRatio)
            {
                bmpFactoryOptions.inSampleSize = heightRatio;
            } else {
                bmpFactoryOptions.inSampleSize = widthRatio;
            }
        }

        bmpFactoryOptions.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(file, bmpFactoryOptions);
        return bitmap;
    }



    private int dpToPx(int dp)
    {
        float density = getApplicationContext().getResources().getDisplayMetrics().density;
        return Math.round((float)dp * density);
    }

    private boolean validation() {
        return false;
    }

    private void saveUserToLocalStore(User user) {
        SharedPreferences sPref = getSharedPreferences("currentUser", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putLong("id", user.getId());
        ed.putString("firstName", user.getFirstName());
        ed.putString("lastName", user.getLastName());
        ed.putString("email", user.getEmail());
        ed.putString("password", user.getPassword());
        ed.putString("login", user.getLogin());

        ed.commit();
    }
}
