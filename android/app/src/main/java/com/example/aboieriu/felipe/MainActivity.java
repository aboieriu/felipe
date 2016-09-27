package com.example.aboieriu.felipe;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;


import java.util.ArrayList;
import java.util.Set;


public class MainActivity extends ActionBarActivity {
    private BluetoothAdapter mBluetoothAdapter;
    private int REQUEST_ENABLE_BT = 1;
    private ConnectThread deviceConnected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Globals.BT_ADAPTER = this.mBluetoothAdapter;
        if (this.mBluetoothAdapter == null) {
            // Device does not support Bluetooth
        }
        if (!this.mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

        ArrayList<BluetoothDevice> deviceList = new ArrayList<>();
        final PairedDevicesAdapter pairedDevicesAdapter = new PairedDevicesAdapter(this.getApplicationContext(), deviceList);

        final Button getPairedBtn = (Button) findViewById(R.id.getPairedDevices);

        final ListView pairedDevicesList = (ListView)findViewById(R.id.pairedDevicesList);
        pairedDevicesList.setAdapter(pairedDevicesAdapter);
        getPairedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
                pairedDevicesAdapter.clear();
                // If there are paired devices
                if (pairedDevices.size() > 0) {
                    // Loop through paired devices
                    for (BluetoothDevice device : pairedDevices) {
                        // Add the name and address to an array adapter to show in a ListView
                        pairedDevicesAdapter.add(device);
                    }
                }
            }
        });

        // Click handler
        pairedDevicesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                BluetoothDevice device = (BluetoothDevice) a.getAdapter().getItem(position);
                if (MainActivity.this.deviceConnected == null) {
                    MainActivity.this.deviceConnected = new ConnectThread(MainActivity.this, device, MainActivity.this.mBluetoothAdapter);
                    MainActivity.this.deviceConnected.run();
                }
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
