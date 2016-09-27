package com.example.aboieriu.felipe;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URI;
import java.util.logging.Handler;


public class Joystick extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joystick);

        final Button forwardBtn = (Button) findViewById(R.id.goForward);
        final Button rightBtn = (Button) findViewById(R.id.goRight);
        final Button backwardsBtn = (Button) findViewById(R.id.goBackwards);
        final Button leftBtn = (Button) findViewById(R.id.goLeft);

        //final Button disconnect = (Button) findViewById(R.id.disconnect);

        //Forward
        forwardBtn.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    Globals.BT_CONNECTED_THREAD.write("0".getBytes());
                    return true;
                } else if(event.getAction() == MotionEvent.ACTION_UP) {
                    Globals.BT_CONNECTED_THREAD.write("stop".getBytes());
                    return true;
                }

                return false;
            }
        });

        //Right
        rightBtn.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    Globals.BT_CONNECTED_THREAD.write("1".getBytes());
                    return true;
                } else if(event.getAction() == MotionEvent.ACTION_UP) {
                    Globals.BT_CONNECTED_THREAD.write("stop".getBytes());
                    return true;
                }
                return false;
            }
        });

        //Backwards
        backwardsBtn.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    Globals.BT_CONNECTED_THREAD.write("2".getBytes());
                    return true;
                } else if(event.getAction() == MotionEvent.ACTION_UP) {
                    Globals.BT_CONNECTED_THREAD.write("stop".getBytes());
                    return true;
                }
                return false;
            }
        });

        //Left
        leftBtn.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    Globals.BT_CONNECTED_THREAD.write("3".getBytes());
                    return true;
                } else if(event.getAction() == MotionEvent.ACTION_UP) {
                    Globals.BT_CONNECTED_THREAD.write("stop".getBytes());
                    return true;
                }
                return false;
            }
        });

        // Handle disconnect
        /*disconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Globals.BT_CONNECTED_THREAD.cancel();
                Context context = Joystick.this.getApplicationContext();
                Intent intent = new Intent(context, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });*/

        Intent socketIntent = new Intent(this, SocketService.class);
        startService(socketIntent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_joystick, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
