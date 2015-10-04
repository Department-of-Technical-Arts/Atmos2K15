package harsu.atmos2k15.atmos.com.atmos2k15;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;

import app.ControllerConstants;

public class Maps extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
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

        //todo change to a static array
        //todo change to a fragment

        IconGenerator factory = new IconGenerator(this);
        factory.setStyle(factory.STYLE_BLUE);
        mMap = googleMap;


        for (int i = 0; i < ControllerConstants.names.length; i++) {
            Bitmap icon = factory.makeIcon(ControllerConstants.names[i]);
            LatLng location = new LatLng(ControllerConstants.latitudes[i], ControllerConstants.longitudes[i]);
            mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(icon)).position(location));
        }


        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(ControllerConstants.latitudes[0], ControllerConstants.longitudes[0]))
                .zoom(18)
                .bearing(180)
                .tilt(60)
                .build();
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
}

