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

        IconGenerator factory = new IconGenerator(this);
        factory.setStyle(factory.STYLE_BLUE);
        mMap = googleMap;

        Bitmap mg = factory.makeIcon("Main Gate");
        LatLng maingate = new LatLng(17.547152, 78.572481);
        mMap.addMarker(new MarkerOptions().
                icon(BitmapDescriptorFactory.fromBitmap(mg)).position(maingate));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(maingate));

        Bitmap bus = factory.makeIcon("Bus Stop");
        LatLng busstop = new LatLng(17.547400, 78.572387);
        mMap.addMarker(new MarkerOptions().
                icon(BitmapDescriptorFactory.fromBitmap(bus)).position(busstop));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(busstop));

        Bitmap cafe = factory.makeIcon("Cafetaria");
        LatLng cafeteria = new LatLng(17.544982, 78.570834);
        mMap.addMarker(new MarkerOptions().
                icon(BitmapDescriptorFactory.fromBitmap(cafe)).position(cafeteria));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(cafeteria));

        Bitmap audi = factory.makeIcon("Auditorium");
        LatLng auditorium = new LatLng(17.545510, 78.570511);
        mMap.addMarker(new MarkerOptions().
                icon(BitmapDescriptorFactory.fromBitmap(audi)).position(auditorium));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(auditorium));

        Bitmap s1 = factory.makeIcon("Stage 1");
        LatLng stage1 = new LatLng(17.545019, 78.571561);
        mMap.addMarker(new MarkerOptions().
                icon(BitmapDescriptorFactory.fromBitmap(s1)).position(stage1));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(stage1));

        Bitmap s2 = factory.makeIcon("Stage 2");
        LatLng stage2 = new LatLng(17.544013, 78.573877);
        mMap.addMarker(new MarkerOptions().
                icon(BitmapDescriptorFactory.fromBitmap(s2)).position(stage2));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(stage2));

        Bitmap cpl = factory.makeIcon("Connaught Place");
        LatLng cp = new LatLng(17.542024, 78.575848);
        mMap.addMarker(new MarkerOptions().
                icon(BitmapDescriptorFactory.fromBitmap(cpl)).position(cp));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(cp));

        Bitmap sh = factory.makeIcon("SBH & ATM");
        LatLng sbh = new LatLng(17.542241, 78.575974);
        mMap.addMarker(new MarkerOptions().
                icon(BitmapDescriptorFactory.fromBitmap(sh)).position(sbh));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sbh));

        Bitmap viceroy = factory.makeIcon("Viceroy Restaurant");
        LatLng vice = new LatLng(17.541928, 78.575802);
        mMap.addMarker(new MarkerOptions().
                icon(BitmapDescriptorFactory.fromBitmap(viceroy)).position(vice));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(vice));

        Bitmap mshop = factory.makeIcon("More Shopping mall");
        LatLng more = new LatLng(17.542021, 78.576085);
        mMap.addMarker(new MarkerOptions().
                icon(BitmapDescriptorFactory.fromBitmap(mshop)).position(more));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(more));

        Bitmap m1 = factory.makeIcon("Mess 1");
        LatLng mess1 = new LatLng(17.542428, 78.574010);
        mMap.addMarker(new MarkerOptions().
                icon(BitmapDescriptorFactory.fromBitmap(m1)).position(mess1));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mess1));

        Bitmap m2 = factory.makeIcon("Mess 2");
        LatLng mess2 = new LatLng(17.544771, 78.575194);
        mMap.addMarker(new MarkerOptions().
                icon(BitmapDescriptorFactory.fromBitmap(m2)).position(mess2));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mess2));

        Bitmap rg = factory.makeIcon("Rock Garden");
        LatLng gar = new LatLng(17.544141, 78.572706);
        mMap.addMarker(new MarkerOptions().
                icon(BitmapDescriptorFactory.fromBitmap(rg)).position(gar));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(gar));

        Bitmap fb = factory.makeIcon("F-Block");
        LatLng fblock = new LatLng(17.544772, 78.571040);
        mMap.addMarker(new MarkerOptions().
                icon(BitmapDescriptorFactory.fromBitmap(fb)).position(fblock));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(fblock));

        Bitmap sc = factory.makeIcon("Student Activity Center");
        LatLng sac = new LatLng(17.540799, 78.575273);
        mMap.addMarker(new MarkerOptions().
                icon(BitmapDescriptorFactory.fromBitmap(sc)).position(sac));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sac));

        Bitmap gb = factory.makeIcon("G-Block");
        LatLng gblock = new LatLng(17.545665, 78.571507);
        mMap.addMarker(new MarkerOptions().
                icon(BitmapDescriptorFactory.fromBitmap(gb)).position(gblock));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(gblock));

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(maingate)
                .zoom(18)
                .bearing(180)
                .tilt(60)
                .build();
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


    }
}
