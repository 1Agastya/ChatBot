package com.example.chatbot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private RecyclerView chatsRV;
    private EditText userMsgEdt;
    private FloatingActionButton sendingMsgFAB;
    private final String BOT_KEY = "bot";
    private final String USER_KEY = "user";
    private ArrayList<ChatsModel> chatsModelArrayList;
    private ChatRVAdapter chatRVAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        chatsRV = findViewById(R.id.idRVChats);
        userMsgEdt = findViewById(R.id.idEdMessage);
        sendingMsgFAB = findViewById(R.id.idFABSend);

        chatsModelArrayList = new ArrayList<>();
        chatRVAdapter = new ChatRVAdapter(chatsModelArrayList, this);

        // Set up RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        chatsRV.setLayoutManager(layoutManager);
        chatsRV.setAdapter(chatRVAdapter);

        // Set onClickListener for sending messages
        sendingMsgFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userMessage = userMsgEdt.getText().toString().trim();
                if (userMessage.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter a message", Toast.LENGTH_SHORT).show();
                    return;
                }
                getResponse(userMessage);
                userMsgEdt.setText("");
            }
        });
    }

    private void getResponse(String message) {
        chatsModelArrayList.add(new ChatsModel(message, USER_KEY));
        chatRVAdapter.notifyDataSetChanged();

        String BASE_URL = "http://api.brainshop.ai/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        String apiKey = "O0rldtCP4NrVcBbf"; // Replace with your API key
      //  String url = "http://api.brainshop.ai/get?bid=178135&key=O0rldtCP4NrVcBbf&uid=[uid]&msg="+message;
        String uid = "http://api.brainshop.ai/get?bid=178135&key=O0rldtCP4NrVcBbf&uid=[uid]&msg="+message; // Replace with your UID
        Call<MsgModel> call = retrofitAPI.getMessage(apiKey,uid,message);
        call.enqueue(new Callback<MsgModel>() {
            @Override
            public void onResponse(Call<MsgModel> call, Response<MsgModel> response) {
                if (response.isSuccessful()) {
                    MsgModel model = response.body();
                    if (model != null) {
                        chatsModelArrayList.add(new ChatsModel(model.getCnt(), BOT_KEY));
                        chatRVAdapter.notifyDataSetChanged();
                    } else {
                        chatsModelArrayList.add(new ChatsModel("No response from bot", BOT_KEY));
                        chatRVAdapter.notifyDataSetChanged();
                    }
                }
            }


            @Override
            public void onFailure(Call<MsgModel> call, Throwable t) {
                t.printStackTrace();
                chatsModelArrayList.add(new ChatsModel("Failed to get a response from the bot", BOT_KEY));
                chatRVAdapter.notifyDataSetChanged();
            }

        });
    }
}
