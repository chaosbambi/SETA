package com.hsowl.seta.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hsowl.seta.R;

public class DeviceFragment extends Fragment {

    // Variables
    ScrollView svDevices;
    ImageView ivIcon; //TODO Adapter/Liste nutzen
    TextView tvDescriptionDevide;
    TextView tvName;
    EditText etDeviceOnTime;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_device, container, false);

        return rootView;
    }
}
