package com.gtisha.hospitallocaterapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HospitalListFragment extends Fragment {

    private EditText searchBar;
    private Button searchButton, locationFilterButton;
    private Spinner locationDropdown;
    private RecyclerView hospitalList;
    private HospitalAdapter hospitalAdapter;
    private List<Hospital> hospitalDataList;
    private DatabaseReference hospitalRef; // Firebase reference

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hospital_list, container, false);

        // Initialize views
        searchBar = view.findViewById(R.id.searchBar);
        searchButton = view.findViewById(R.id.searchButton);
        locationFilterButton = view.findViewById(R.id.locationFilterButton);
        locationDropdown = view.findViewById(R.id.locationDropdown);
        hospitalList = view.findViewById(R.id.hospitalList);

        // Initialize RecyclerView
        hospitalList.setLayoutManager(new LinearLayoutManager(getContext()));
        hospitalDataList = new ArrayList<>();
        hospitalAdapter = new HospitalAdapter(hospitalDataList);
        hospitalList.setAdapter(hospitalAdapter);

        // Firebase reference to the "hospitals" node
        hospitalRef = FirebaseDatabase.getInstance().getReference("hospitals");

        // Fetch hospital data from Firebase
        fetchHospitalData();

        // Fetch unique locations from hospitals node
        fetchLocationDataFromHospitals();

        if (searchButton != null) {
            searchButton.setOnClickListener(v -> searchByHospitalName());
        }




        if (locationFilterButton != null) {
            locationFilterButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Get the selected item from the dropdown
                    Object selectedItem = locationDropdown.getSelectedItem();
                    if (selectedItem != null) {
                        String selectedLocation = selectedItem.toString();
                        // Perform your filter logic here
                        filterByLocation(selectedLocation); // Example method to filter hospitals
                    } else {
                        // Handle null case
                        Log.e("HospitalListFragment", "No location selected");
                    }
                }
            });

        }



        return view;
    }

    private void fetchHospitalData() {
        hospitalRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                hospitalDataList.clear();
                for (DataSnapshot hospitalSnapshot : snapshot.getChildren()) {
                    Hospital hospital = hospitalSnapshot.getValue(Hospital.class);
                    if (hospital != null) {
                        hospitalDataList.add(hospital);
                        Log.d("FirebaseData", "Fetched: " + hospital.getName());
                    } else {
                        Log.d("FirebaseData", "No hospital data found.");
                    }
                }
                hospitalAdapter.notifyDataSetChanged();
                Log.d("FirebaseData", "Data list size: " + hospitalDataList.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseError", "Database error: " + error.getMessage());
            }
        });
    }




    /** private void fetchHospitalData() {
        hospitalDataList.clear();
        // Static test data
        Hospital testHospital = new Hospital("Test Hospital", "Test Location", "https://example.com/testlogo.png","h00004");
        hospitalDataList.add(testHospital);
        hospitalAdapter.notifyDataSetChanged();
    } */

    private void fetchLocationDataFromHospitals() {
        hospitalRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Set<String> locationSet = new HashSet<>(); // Use a Set to avoid duplicate locations

                for (DataSnapshot hospitalSnapshot : snapshot.getChildren()) {
                    Hospital hospital = hospitalSnapshot.getValue(Hospital.class);

                    if (hospital != null && hospital.getLocation() != null) {
                        Log.d("LocationData", "Location: " + hospital.getLocation());
                        locationSet.add(hospital.getLocation()); // Add location to the Set
                    }
                }

                // Convert Set to List for the Spinner
                List<String> locationList = new ArrayList<>(locationSet);

                // Create an ArrayAdapter and set it to the Spinner
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, locationList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                locationDropdown.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error here
            }
        });
    }

    private void searchByHospitalName() {
        String query = searchBar.getText().toString().toLowerCase();
        List<Hospital> filteredList = new ArrayList<>();
        for (Hospital hospital : hospitalDataList) {
            if (hospital.getName().toLowerCase().contains(query)) {
                filteredList.add(hospital);
            }
        }
        hospitalAdapter.updateList(filteredList);
    }

    private void filterByLocation(String location) {
        String selectedLocation = locationDropdown.getSelectedItem() != null ? locationDropdown.getSelectedItem().toString() : null;
        if (selectedLocation == null) {
            Log.e("HospitalListFragment", "Selected location is null");
        } else {
            Log.d("HospitalListFragment", "Selected location: " + selectedLocation);
        }

        List<Hospital> filteredList = new ArrayList<>();
        for (Hospital hospital : hospitalDataList) {
            if (hospital.getLocation().equalsIgnoreCase(selectedLocation)) {
                filteredList.add(hospital);
            }
        }
        hospitalAdapter.updateList(filteredList);


    }
}
