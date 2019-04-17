package com.example.user_pc.remotedavidmanager_pi;

import android.nfc.Tag;
import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BoardController {
    private static final String TAG = "Client";
    private static BoardController me;
    private OkHttpClient client;

    private static final String baseURL = "http://192.168.0.5/";

    private BoardController(){
        client = new OkHttpClient();
    }

    public static BoardController getInstance(){
        if (me == null)
            me = new BoardController();

        return me;
    }

    public void sendData(int action){
        String url = baseURL;
        Request request = new Request.Builder().url(url + "?params=" + action).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "Response [" + response.body().string() + "]");
            }
        });

    }

}
