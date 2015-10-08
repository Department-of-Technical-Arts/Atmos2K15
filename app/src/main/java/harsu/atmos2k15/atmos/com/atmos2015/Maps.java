package harsu.atmos2k15.atmos.com.atmos2015;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class Maps extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_maps, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        IconGenerator factory = new IconGenerator(getActivity());
        factory.setStyle(factory.STYLE_BLUE);
        mMap = googleMap;


        for (int i = 0; i < ControllerConstants.names.length; i++) {
            Bitmap icon = factory.makeIcon(ControllerConstants.names[i]);
            LatLng location = new LatLng(ControllerConstants.latitudes[i], ControllerConstants.longitudes[i]);
            mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(icon)).position(location));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
        }


        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(ControllerConstants.latitudes[0], ControllerConstants.longitudes[0]))
                .zoom(18)
                .bearing(180)
                .tilt(60)
                .build();
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
}

