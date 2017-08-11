package br.com.secompufscar.secomp_ufscar;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback{

    private static final String TAG = "MapsActivity";
    private GoogleMap mMap;
    private LocationManager locationManager;
    private int Local = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();
        if(intent != null){
            Bundle params = intent.getExtras();
            if(params != null){
                Local = params.getInt("Local");
            }
        }
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
    @Override
    public void onMapReady(GoogleMap googleMap) {

        try {
            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            mMap = googleMap;

            Criteria criteria = new Criteria();

            locationManager.getBestProvider(criteria, true);

            //botoes de zoom e sua localizacao
            mMap.getUiSettings().setZoomControlsEnabled(true);
            mMap.setMyLocationEnabled(true);
        } catch (SecurityException ex) {
            Log.e(TAG, "Error while acquiring location", ex);
        }

        LatLng DC = new LatLng(-21.979509, -47.880528);
        LatLng BentoPrado = new LatLng(-21.983667, -47.881668);
        LatLng Centro_UFSCar = new LatLng(-21.984163, -47.880243);


        mMap.addMarker(new MarkerOptions().position(DC).title("Departamento de Computação"));
        mMap.addMarker(new MarkerOptions().position(BentoPrado).title("Anfiteatro Bento Prado Júnior"));

        switch(Local){
            case 0:
                mMap.moveCamera(CameraUpdateFactory.newLatLng(Centro_UFSCar));break;
            case 1:
                mMap.moveCamera(CameraUpdateFactory.newLatLng(DC));break;
            case 2:
                mMap.moveCamera(CameraUpdateFactory.newLatLng(BentoPrado));break;
        }
    }
}
