package com.example.user_pc.remotedavidmanager_pi;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import fi.iki.elonen.NanoHTTPD;

public class AndroidWebServer extends NanoHTTPD {
    private static final String TAG = "HTTPServer";
    private Context ctx;
    private WebserverListener listener;
    private static final String baseURL = "http://192.168.0.5:8180/";

    public AndroidWebServer(int PORT, Context ctx ){
        super(PORT);
        this.ctx = ctx;
        try {
            Log.d(TAG, "Starting web server!");
            start();
        } catch (IOException ioe){
            Log.e(TAG, "Unable to start the server");
            ioe.printStackTrace();
        }
    }

    public void setListener(WebserverListener listener){
        this.listener = listener;
    }

    @Override
    public Response serve(IHTTPSession session){
        Map<String, String> params = session.getParms();

        String action = params.get("action");
        String content = null;

        if (action == null){
            content = readFile().toString();

        } else {
            Log.d(TAG, "Action ["+ action + "]");
            listener.handleCommand(Integer.parseInt(action));

        }

        return newFixedLengthResponse(content);

    }

    private StringBuffer readFile() {
        BufferedReader reader = null;
        StringBuffer buffer = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(ctx.getAssets().open("home.html"), "UTF-8"));
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                buffer.append(mLine);
                buffer.append("\n");
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ioe) {
                }
            }
        }

        return buffer;
    }

    public void stopServer(){
        this.stop();
    }

    public interface WebserverListener {
        public void handleCommand(int action);
    }
}
