package com.mahmoudrashad.websocket;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.net.URI;
import java.net.URISyntaxException;

import tech.gusavila92.websocketclient.WebSocketClient;

public class MainActivity extends AppCompatActivity {

    private WebSocketClient webSocketClient;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        createWebSocketClient();
    }

    private void createWebSocketClient() {

        URI uri;
        try {
            // Connect to local host
            uri = new URI("ws://10.0.2.2:8080/websocket");
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }


        webSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen() {

                Log.i("WebSocket", "Session is starting");
                webSocketClient.send("Hello World!");

            }

            @Override
            public void onTextReceived(String s) {

                Log.i("WebSocket", "Message received");
                final String message = s;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            TextView textView = findViewById(R.id.T_V);
                            textView.setText(message);
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });

            }

            @Override
            public void onBinaryReceived(byte[] data) {

            }

            @Override
            public void onPingReceived(byte[] data) {

            }

            @Override
            public void onPongReceived(byte[] data) {

            }

            @Override
            public void onException(Exception e) {
                System.out.println(e.getMessage());


            }

            @Override
            public void onCloseReceived() {
                Log.i("WebSocket", "Closed ");
                System.out.println("onCloseReceived");

            }
        };
        webSocketClient.setConnectTimeout(10000);
        webSocketClient.setReadTimeout(60000);
        webSocketClient.enableAutomaticReconnection(5000);
        webSocketClient.connect();

    }
    public void sendMessage(View view) {
        Log.i("WebSocket", "Button was clicked");
        // Send button id string to WebSocket Server
        switch(view.getId()){
            case(R.id.bt_1):
                webSocketClient.send("1");
                break;
            case(R.id.bt_2):
                webSocketClient.send("2");
                break;
            case(R.id.bt_3):
                webSocketClient.send("3");
                break;
            case(R.id.bt_4):
                webSocketClient.send("4");
                break;
        }
    }

}

