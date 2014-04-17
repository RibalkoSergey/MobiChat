package com.example.MobiChat.utils;

import com.example.MobiChat.entity.Friend;
import com.example.MobiChat.entity.Message;
import com.example.MobiChat.entity.User;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.util.support.Base64;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: sergey
 * Date: 2/27/14
 * Time: 7:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class HttpClientHelper {
    private final String host = "http://109.227.103.70:8080/chat/";

    public JSONObject execRequest(HashMap<String, String> params, String url) throws IOException, JSONException {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        InputStream is = null;
        String result = "";
        JSONObject jsonObject = null;

        for (Map.Entry<String, String> entry : params.entrySet()) {
            nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }

        HttpPost httppost = new HttpPost(host + url);
        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));

        //HttpResponse response = HttpClientSingle.getInstance().execute(httppost);
        DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
        HttpResponse response = defaultHttpClient.execute(httppost);

        HttpEntity entity = response.getEntity();
        is = entity.getContent();

        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"), 8);
        StringBuilder sb = new StringBuilder();
        String line = null;

        while ((line = reader.readLine()) != null) {
            sb.append(line + "\n");
        }
        is.close();
        result = sb.toString();
        jsonObject = new JSONObject(result);

        //String s = EntityUtils.toString(response.getEntity(), "UTF-8");

        return jsonObject;
    }

    public List<Friend> getFriendsListByUserId(Long currentUserId) throws JSONException {
        List<Friend> friendList = new ArrayList<Friend>();

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("currentUserId", String.valueOf(currentUserId));
        JSONObject resultFromServer = new JSONObject();
        try {
            resultFromServer = execRequest(params, "/get/friends/list");

            if (!resultFromServer.has("error")) {
                JSONArray objects = new JSONArray();
                objects = resultFromServer.getJSONArray("result");
                for (int i = 0; i < objects.length(); i++) {
                    JSONObject oneObject = objects.getJSONObject(i);
                    // Pulling items from the array
                    Long id = oneObject.getLong("id");
                    String firstName = oneObject.getString("firstName");
                    String lastName = oneObject.getString("lastName");
                    String email = oneObject.getString("email");
                    Boolean onLine = oneObject.getBoolean("isOnline");
                    String photo = oneObject.getString("photo");
                    Long countNotReadMessages = oneObject.getLong("countNotReadMessages");
                    byte[] photoBytes = new byte[0];
                    try {
                        photoBytes = Base64.decode(photo);
                    } catch (IOException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }

                    Friend friend = new Friend(id, firstName, lastName, email, onLine, photoBytes, countNotReadMessages);
                    friendList.add(friend);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return friendList;
    }

    public List<Friend> getInvitesByUserId(Long currentUserId) throws JSONException {
        List<Friend> friendList = new ArrayList<Friend>();

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("currentUserId", String.valueOf(currentUserId));
        JSONObject resultFromServer = new JSONObject();
        try {
            resultFromServer = execRequest(params, "/get/invites");

            if (!resultFromServer.has("error")) {
                JSONArray objects = new JSONArray();
                objects = resultFromServer.getJSONArray("result");
                for (int i = 0; i < objects.length(); i++) {
                    JSONObject oneObject = objects.getJSONObject(i);
                    // Pulling items from the array
                    Long id = oneObject.getLong("id");
                    String firstName = oneObject.getString("firstName");
                    String lastName = oneObject.getString("lastName");
                    String email = oneObject.getString("email");
                    Boolean onLine = oneObject.getBoolean("isOnline");

                    Friend friend = new Friend(id, firstName, lastName, email, onLine, null, 0L);
                    friendList.add(friend);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return friendList;
    }

    public List<Friend> getFriendsListByName(String name) throws JSONException {
        List<Friend> friendList = new ArrayList<Friend>();

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("name", String.valueOf(name));
        JSONObject resultFromServer = new JSONObject();
        try {
            resultFromServer = execRequest(params, "/get/friends/list/by/name");

            if (!resultFromServer.has("error")) {
                JSONArray objects = new JSONArray();
                objects = resultFromServer.getJSONArray("result");
                for (int i = 0; i < objects.length(); i++) {
                    JSONObject oneObject = objects.getJSONObject(i);
                    // Pulling items from the array
                    Long id = oneObject.getLong("id");
                    String firstName = oneObject.getString("firstName");
                    String lastName = oneObject.getString("lastName");
                    String email = oneObject.getString("email");
                    Boolean onLine = oneObject.getBoolean("isOnline");

                    Friend friend = new Friend(id, firstName, lastName, email, onLine, null, 0L);
                    friendList.add(friend);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return friendList;
    }

    public User registration(String image, String first_name, String last_name, String login, String password, String email) throws JSONException {
        User user = null;

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("image", image);
        params.put("first_name", first_name);
        params.put("last_name", last_name);
        params.put("login", login);
        params.put("password", password);
        params.put("email", email);

        JSONObject object;
        try {
            object = execRequest(params, "/administration/register1");

            if (!object.has("error")) {

                Long _id = object.getLong("id");
                String _firstName = object.getString("firstName");
                String _lastName = object.getString("lastName");
                String _email = object.getString("email");
                Boolean _onLine = object.getBoolean("isOnline");

                user = new User(_id, _firstName, _lastName, _email, _onLine, null);
            }

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return user;
    }

    public Boolean sendMessage(Message message) {
        Boolean result = false;
        try {
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("fromUserId", String.valueOf(message.getFromUserId()));
            params.put("toUserId", String.valueOf(message.getToUserId()));
            params.put("messageTxt", message.getText());
            JSONObject resultFromServer;

            resultFromServer = execRequest(params, "/message/save");
            if (!resultFromServer.has("error")) {
                result = resultFromServer.getBoolean("result");
            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return result;
    }

    public Boolean sendInvite(Long fromUserId, Long toUserId) {
        Boolean result = false;
        try {
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("fromUserId", String.valueOf(fromUserId));
            params.put("toUserId", String.valueOf(toUserId));
            JSONObject resultFromServer;

            resultFromServer = execRequest(params, "/send/invite");
            if (!resultFromServer.has("error")) {
                result = resultFromServer.getBoolean("result");
            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return result;
    }

    public Boolean AddToFriend(Long currentUserId, Long friendId) {
        Boolean result = false;
        try {
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("currentUserId", String.valueOf(currentUserId));
            params.put("friendId", String.valueOf(friendId));
            JSONObject resultFromServer;

            resultFromServer = execRequest(params, "/add/friend");
            if (!resultFromServer.has("error")) {
                result = resultFromServer.getBoolean("result");
            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return result;
    }

    public Boolean deteleInvite(Long currentUserId, Long friendId) {
        Boolean result = false;
        try {
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("currentUserId", String.valueOf(currentUserId));
            params.put("friendId", String.valueOf(friendId));
            JSONObject resultFromServer;

            resultFromServer = execRequest(params, "/delete/invite");
            if (!resultFromServer.has("error")) {
                result = resultFromServer.getBoolean("result");
            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return result;
    }

    public Boolean markMessagesAsRead(Long[] ids) {
        Boolean result = false;
        try {
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("messageIds", StringUtils.join(ids, ";"));
            JSONObject resultFromServer;

            resultFromServer = execRequest(params, "/mark/messages/as/read");
            if (!resultFromServer.has("error")) {
                result = resultFromServer.getBoolean("result");
            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return result;
    }



    private String arrayToString(List<Long> listIds) {
        String result = "";
        for (Long id : listIds) {
            result += String.valueOf(id) + ";";
        }
        return "";
    }

    public List<Message> getNotReadMessages(Long fromUserId, Long toUserId) {
        List<Message> messages = new ArrayList<Message>();

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("fromUserId", String.valueOf(fromUserId));
        params.put("toUserId", String.valueOf(toUserId));
        JSONObject resultFromServer = new JSONObject();
        try {
            resultFromServer = execRequest(params, "/get/message/by/user/id");

            if (!resultFromServer.has("error")) {
                JSONArray objects = new JSONArray();
                objects = resultFromServer.getJSONArray("result");
                for (int i = 0; i < objects.length(); i++) {
                    JSONObject oneObject = objects.getJSONObject(i);
                    Long id = oneObject.getLong("id");
                    String text = oneObject.getString("message");
                    String fromUserName = oneObject.getString("fromUserName");
                    String toUserName = oneObject.getString("toUserName");
                    Date dateMessage = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(oneObject.getString("dateMessage"));

                    Message message = new Message(id, fromUserId, fromUserName, toUserId, toUserName, text, dateMessage);
                    messages.add(message);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


        return messages;
    }

    public byte[] getFotoByUserId(Long id) {
        byte[] bytes = new byte[0];
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("id", String.valueOf(id));
        JSONObject resultFromServer;
        try {
            resultFromServer = execRequest(params, "/get/foto");

            if (!resultFromServer.has("error")) {
                String s = resultFromServer.getString("result");
                bytes = Base64.decode(s);
            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (JSONException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return bytes;

    }
}
