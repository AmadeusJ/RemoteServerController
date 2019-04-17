package com.example.user_pc.remotedavidmanager_pi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onButtonClicked(View v){
        // Button Event
        Button button1 = (Button) findViewById(R.id.socket);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectThread thread = new ConnectThread();
                thread.start();
            }
        });
    }

    // Socket Connection to Raspberry pi
    class ConnectThread extends Thread{
        String hostname;

        private ConnectThread(){
            hostname = "192.168.0.5";
        }

        public void SocekConnectRun(){
            try{
                int port = 5555;

                Socket sock = new Socket(hostname, port);

                ObjectOutputStream outstream = new ObjectOutputStream(sock.getOutputStream());
                outstream.writeObject("Hello Raspberry pi!");
                outstream.flush();

                ObjectInputStream instream = new ObjectInputStream(sock.getInputStream());
                String obj = (String) instream.readObject();

                System.out.println("Tried to connect");
                System.out.print("Msg from raspberry pi: " + obj);

                Log.d("MainActivity", "Message from Raspberry pi: " + obj);
                sock.close();

            } catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }
}
