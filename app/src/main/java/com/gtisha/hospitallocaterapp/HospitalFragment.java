package com.gtisha.hospitallocaterapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class HospitalFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hospital, container, false);

        // Initialize RadioButtons
        RadioGroup segmentedControl = view.findViewById(R.id.toggle_btn);
        RadioButton btnFind = view.findViewById(R.id.btn_find);
        RadioButton btnLocate = view.findViewById(R.id.btn_locate);

        // Load Fragment 1 initially (Find)
        loadFragment(new HospitalListFragment());

        // Listener for switching fragments
        segmentedControl.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.btn_find) {
                // Load Fragment 1 (Find)
                loadFragment(new HospitalListFragment());
            } else if (checkedId == R.id.btn_locate) {
                // Load Fragment 2 (Locate)
                loadFragment(new MapFragment());
            }
        });

        return view;
    }

    // Helper method to load fragments
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container2, fragment);
        transaction.commit();
    }
}

