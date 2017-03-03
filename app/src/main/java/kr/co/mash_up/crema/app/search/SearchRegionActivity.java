package kr.co.mash_up.crema.app.search;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.bigstark.cycler.CyclerActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.co.mash_up.crema.PermissionRequester;
import kr.co.mash_up.crema.R;
import kr.co.mash_up.crema.app.activity.NearbyCafeActivity;

/**
 * Created by bigstark on 2017. 3. 4..
 */

public class SearchRegionActivity extends CyclerActivity implements OnMapReadyCallback {

    private static final long MIN_TIME = 5000;
    private static final float MIN_DISTANCE = 10f;


    private GoogleMap map;

    @BindView(R.id.mv_search_region) MapView mvSearchRegion;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_region_main);
        setUnbinder(ButterKnife.bind(this));
        mvSearchRegion.onCreate(savedInstanceState);
        mvSearchRegion.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try {
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            LatLng center = new LatLng(location.getLatitude(), location.getLongitude());
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(center, 16));
        } catch (SecurityException e) {}
    }


    @Override
    protected void onStart() {
        super.onStart();
        mvSearchRegion.onStart();
    }


    @Override
    protected void onStop() {
        mvSearchRegion.onStop();
        super.onStop();
    }


    @Override
    protected void onDestroy() {
        mvSearchRegion.onDestroy();
        super.onDestroy();
    }

}
