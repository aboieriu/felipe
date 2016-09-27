package com.example.aboieriu.felipe;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by aboieriu on 3/16/15.
 */
public class PairedDevicesAdapter extends ArrayAdapter<BluetoothDevice> {
    public PairedDevicesAdapter(Context context, ArrayList<BluetoothDevice> devices) {
        super(context, 0, devices);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        BluetoothDevice bluetoothDevice = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.sample_paired_device_view, parent, false);
        }
        // Lookup view for data population
        TextView deviceItemView = (TextView) convertView.findViewById(R.id.deviceName);

        // Populate the data into the template view using the data object
        deviceItemView.setText(bluetoothDevice.getName());
        // Return the completed view to render on screen
        return convertView;
    }
}
