package com.gtisha.hospitallocaterapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check if the fragment container is empty
        if (savedInstanceState == null) {
            // Create an instance of the Fragment
            HospitalFragment hospitalFragment = new HospitalFragment();
            // Redirect to the Fragment
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container1, hospitalFragment) // Replace with the fragment you want
                    .commit();
        }
    }
}