package com.example.projetofinalcep.activity;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.projetofinalcep.R;
import com.example.projetofinalcep.databinding.ActivityMapsBinding;
import com.example.projetofinalcep.modelos.CEP;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private Button voltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        voltar = findViewById(R.id.btnVoltar);

        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.clear();
        Intent intent = getIntent();
        String endereco = intent.getStringExtra("endereco").toString();
        LatLng localizacao = determineLatLngFromAddress(getApplicationContext(), endereco);
        if (localizacao == null) {
            Toast.makeText(this, "falha ao buscar localização", Toast.LENGTH_SHORT).show();
        } else {
            mMap.addMarker(new MarkerOptions().position(localizacao).title("CEP"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(localizacao, 16));
        }
    }

    public LatLng determineLatLngFromAddress(Context appContext, String strAddress) {
        LatLng latLng = null;
        Geocoder geocoder = new Geocoder(appContext, Locale.getDefault());
        List<Address> geoResults = null;

        try {
            geoResults = geocoder.getFromLocationName(strAddress, 1);
            while (geoResults.size() == 0) {
                geoResults = geocoder.getFromLocationName(strAddress, 1);
            }
            if (geoResults.size() > 0) {
                Address addr = geoResults.get(0);
                latLng = new LatLng(addr.getLatitude(), addr.getLongitude());
            }
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }

        return latLng;
    }
}