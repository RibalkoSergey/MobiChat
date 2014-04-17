package com.example.MobiChat.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.MobiChat.R;
import com.example.MobiChat.entity.Friend;
import com.example.MobiChat.entity.User;
import com.example.MobiChat.utils.HttpClientHelper;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LoginActivity extends Activity {
    private Context self = this;
    private TextView tvGetAccountNow;
    private Button btnSignIn;
    private HttpClientHelper httpClientHelper;
    private TextView editTxtLogin;
    private TextView editTxtPassword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        initElementOfPage();
        setActions();
        getSavedData();
    }

    private void getSavedData() {
        SharedPreferences sPref = getSharedPreferences("currentUser", Context.MODE_PRIVATE);
        String login = sPref.getString("login", "");
        String password = sPref.getString("password", "");
        editTxtLogin.setText(login);
        editTxtPassword.setText(password);
    }

    private void initElementOfPage() {
        tvGetAccountNow = (TextView) this.findViewById(R.id.tvGetAccountNow);
        btnSignIn = (Button) this.findViewById(R.id.btnSignIn);
        httpClientHelper = new HttpClientHelper();
        editTxtLogin = (EditText) findViewById(R.id.editTxtLogin);
        editTxtPassword = (EditText) findViewById(R.id.editTxtPassword);
    }

    private void setActions() {
        tvGetAccountNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(self, RegistrationActivity.class);
                startActivity(intent);
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkAvailable(getApplicationContext()) != true) {
                    Toast.makeText(self.getApplicationContext(), "Internet is not connected.", Toast.LENGTH_SHORT).show();
                    return;
                }

                String login = editTxtLogin.getText().toString();
                String password = editTxtPassword.getText().toString();

                if (!validateLogin(login, password)) {
                    Toast.makeText(self.getApplicationContext(), "Login or Password is empty.", Toast.LENGTH_SHORT).show();
                    return;
                }

                HashMap<String, String> params = new HashMap<String, String>();
                params.put("login", login);
                params.put("password", password);

                JSONObject result = new JSONObject();
                try {
                    result = httpClientHelper.execRequest(params, "login");

                    if (!result.has("error")) {

                        User currentUser = createUser(result);
                        saveUserToLocalStore(currentUser);

                        Intent intent = new Intent(self, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(self.getApplicationContext(), result.getString("error"), Toast.LENGTH_SHORT).show();
                    }

                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (JSONException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        });
    }

    private Boolean validateLogin(String login, String password){
        if (StringUtils.isBlank(login) || StringUtils.isBlank(password)) {
            return false;
        }
        return true;
    }

    private User createUser(JSONObject jsonObject) throws JSONException {
        User user = new User();
        user.setId(jsonObject.getLong("id"));
        user.setFirstName(jsonObject.getString("firstName"));
        user.setLastName(jsonObject.getString("lastName"));
        user.setEmail(jsonObject.getString("email"));
        user.setPassword(jsonObject.getString("password"));
        user.setLogin(jsonObject.getString("login"));
        return user;
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

    public static boolean isNetworkAvailable(Context context) {
        Boolean result = false;
        ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = conMgr.getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isAvailable() && activeNetworkInfo.isConnected())
            result = true;
        return result;
    }


}
