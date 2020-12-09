package com.example.troca;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class ChatBoxActivity extends AppCompatActivity
{
    public RecyclerView myRecylerView ;
    public List<Message> MessageList = new ArrayList<>();
    //public ChatBoxAdapter chatBoxAdapter = new ChatBoxAdapter(MessageList);
    public EditText messagetxt ;
    public Button send ;
    private User user;
    public NewChatboxAdapter chatBoxAdapter;
    public static final String sharedPrefFile = "com.example.troca";
    private SharedPreferences sharedPrefs;
    int chatid;
    //declare socket object
    private Socket socket;

    public String Nickname ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_box);
        messagetxt = (EditText) findViewById(R.id.message) ;
        send = (Button)findViewById(R.id.send);
        // get the nickame of the user
        sharedPrefs = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPrefs.getString("SmartMunicipalityUser", "");
        user = gson.fromJson(json, User.class);
        chatBoxAdapter = new NewChatboxAdapter(ChatBoxActivity.this, MessageList, user);
        Nickname = user.getNomPrenomClient() ;
        chatid = (Integer) getIntent().getExtras().getInt("roomid");


        //connect you socket client to the server
        try {
            socket = IO.socket(PublicVars.chat);
            socket.connect();
            socket.emit("join", Nickname,chatid);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

       //setting up recyler
        myRecylerView = (RecyclerView) findViewById(R.id.messagelist);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        myRecylerView.setLayoutManager(mLayoutManager);
        myRecylerView.setItemAnimator(new DefaultItemAnimator());



        // message send action
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //retrieve the nickname and the message content and fire the event messagedetection
                if(!messagetxt.getText().toString().isEmpty()){
                    socket.emit("messagedetection",user.getIdClient(),messagetxt.getText().toString(),chatid, Nickname);

                    messagetxt.setText(" ");
                }


            }
        });

        //implementing socket listeners
        socket.on("userjoinedthechat", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String data = (String) args[0];

                        Toast.makeText(ChatBoxActivity.this,data, Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
        /*socket.on("userdisconnect", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String data = (String) args[0];

                        Toast.makeText(com.miniprojet.smartmunicipality.ChatBoxActivity.this,data,Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });*/

        socket.on("retrieve_msg", new Emitter.Listener() {
            @Override
            public void call(final Object... objects) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String data = objects[0].toString();
                        try
                        {
                            JSONArray jsonarray = new JSONArray(data);
                            for (int i = 0; i < jsonarray.length(); i++)
                            {
                                JSONObject obj = (JSONObject) jsonarray.get(i);
                                String nickname = obj.getString("first_name") + " " + obj.getString("last_name");
                                String message = obj.getString("messagecontent");
                                int idc = obj.getInt("propid");
                                Message m = new Message(nickname,message,idc,obj.getInt("id"));
                                MessageList.add(m);
                            }

                            chatBoxAdapter.notifyDataSetChanged();
                            myRecylerView.setAdapter(chatBoxAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });
            }
        });

        socket.on("message", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject data = (JSONObject) args[0];
                        try {
                            int nickname = data.getInt("senderNickname");
                            String message = data.getString("message");
                            int idc = data.getInt("Chatid");
                            String actualName = data.getString("actualName");
                            // make instance of message
                            Message m = new Message(actualName,message,idc,nickname);


                            if(m.getChatid()==chatid)
                            {
                                MessageList.add(m);
                            }
                            // add the new updated list to the dapter
                            //chatBoxAdapter = new ChatBoxAdapter(MessageList);

                            // notify the adapter to update the recycler view

                            chatBoxAdapter.notifyDataSetChanged();

                            //set the adapter for the recycler view

                            myRecylerView.setAdapter(chatBoxAdapter);
                            myRecylerView.scrollToPosition(MessageList.size() - 1);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        socket.disconnect();
  }
}
