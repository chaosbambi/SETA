package com.hsowl.seta.ui;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hsowl.seta.R;
import com.hsowl.seta.data.HouseData;
import com.hsowl.seta.data.WeatherStation;

public class AboutFragment extends Fragment {

    // Variables
    TextView tvPoweredBy;
    TextView tvLicence;

    GsonBuilder gsonBuilder;
    Gson gson;
    HouseData houseData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);

        gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(WeatherStation.class, new InterfaceAdapter<WeatherStation>());
        gson = gsonBuilder.create();

        Intent intent = this.getActivity().getIntent();

        try{
            houseData = gson.fromJson(intent.getStringExtra("houseData"), HouseData.class);
        }catch (Exception e){
            Toast.makeText(getActivity(), R.string.exception_first_login, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_about, container, false);

        return rootView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                Toast.makeText(getActivity(),"Back button clicked", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("houseData", gson.toJson(houseData));
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                getActivity().finish();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPause() {
        super.onPause();

        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.putExtra("houseData", gson.toJson(houseData));
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        getActivity().finish();
    }
}
