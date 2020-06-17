package com.example.campagnon;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.campagnon.Class.LesUsers;
import com.example.campagnon.Class.User;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements GoogleMap.OnMarkerClickListener,OnMapReadyCallback {

    private GoogleMap mMap;

    User monUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        monUser= LesUsers.getUserID(getIntent().getStringExtra("identifiant"));
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
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //On place le point du consommateur sur la carte
        LatLng maPosition = new LatLng(Double.parseDouble(monUser.getY()), Double.parseDouble(monUser.getX()));
        mMap.addMarker(new MarkerOptions().position(maPosition).title("Ma maison"));
        Marker maMaison = mMap.addMarker(new MarkerOptions()
                .position(maPosition)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(maPosition, 10));

        //On parcourt la liste des Producteurs
        for(User unUser : LesUsers.getListProducteurs()){
            mMap = googleMap;
            //On place un point un à un pour chaque producteur
            LatLng lesPosition = new LatLng(Double.parseDouble(unUser.getY()), Double.parseDouble(unUser.getX()));
            Marker mBrisbane = mMap.addMarker(new MarkerOptions()
                    .position(lesPosition)
                    .title(unUser.getIdentifiant()));
            mBrisbane.setTag(0);
        }


        mMap.setOnMarkerClickListener(this);
    }

    @Override
    public boolean onMarkerClick(final Marker marker) { //Lorsque l'on clique sur un marker

        // Retrieve the data from the marker.
        Integer clickCount = (Integer) marker.getTag();

        // Check if a click count was set, then display the click count.
        if (clickCount != null) {
            //On affiche la page correspondant au producteur selectionné.
            Intent intent = new Intent(MapsActivity.this, LeProducteur.class);
            intent.putExtra("Prod", marker.getTitle());
            intent.putExtra("identifiant", monUser.getIdentifiant());
            startActivity(intent);
        }

        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false;
    }


}
