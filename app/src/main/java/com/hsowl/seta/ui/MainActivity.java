package com.hsowl.seta.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.hsowl.seta.R;
import com.hsowl.seta.data.HouseData;
import com.hsowl.seta.data.Storage;
import com.hsowl.seta.logic.EnergySuggestion;

public class MainActivity extends AppCompatActivity implements OnHouseDataSaveListener{
    private static final String TAG = "MainActivity";

    // Variables
    HouseData houseData;
    EnergySuggestion energySuggestion;
    Storage storage;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Load data from storage
        storage = new Storage(this);

        try{
            storage.readHouseData();
            houseData = storage.getHouseData();
        }catch (Exception e){
            Log.e(TAG, e.getMessage());
        }

        setTheme(R.style.Theme_AppCompat_NoActionBar);
        //setTheme(R.style.AppTheme);

        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: Starting");

        // Set up the ViewPager with the sections adapter
        mViewPager = findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        // Get shared preferences
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        // First time run?
        if (pref.getBoolean("firstTimeRun", true)) {

            // Start the  CustomSettingsFragment
            tabLayout.getTabAt(1).select();

            // Get the preferences editor
            SharedPreferences.Editor editor = pref.edit();

            // Avoid for next run
            editor.putBoolean("firstTimeRun", false);
            editor.commit();
        }

        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d(TAG, "onPause: Pausing");

        // Store data in Storage
        try{
            storage.setHouseData(houseData);
            storage.storeHouseData();
        }catch(Exception e){
            Log.e(TAG, e.getMessage());
            Toast.makeText(this, R.string.exception_error_store_data, Toast.LENGTH_SHORT)
                .show();
        }
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: Ending");

        super.onDestroy();
    }

    private void setupViewPager(ViewPager viewPager){
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new HomeFragment(), "HOME");
        adapter.addFragment(new CustomSettingsFragment(), "EINSTELLUNGEN");
        adapter.addFragment(new AboutFragment(), "ÜBER");

        viewPager.setAdapter(adapter);
    }

    @Override
    public void save(HouseData houseData) {

        this.houseData = houseData;
        this.energySuggestion = null;
    }
}
