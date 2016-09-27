package com.example.aboieriu.felipe;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

public class SocketService extends IntentService {

    public SocketService(){
        super("SocketService");
    }


    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        try {
            final Socket socket = IO.socket("http://192.168.0.104:9000");
            socket.on("command", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    //args[0];
                    Globals.BT_CONNECTED_THREAD.write(args[0].toString().getBytes());
                    Log.i("Send command", args[0].toString());
                }

            });
            socket.connect();
        } catch (Exception e){
            Log.e("Socket", e.toString());
        }
    }

    @Override
    protected void onHandleIntent(Intent workIntent) {

    }
}