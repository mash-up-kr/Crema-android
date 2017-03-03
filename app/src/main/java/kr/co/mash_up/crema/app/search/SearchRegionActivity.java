package kr.co.mash_up.crema.app.search;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;

import com.bigstark.cycler.CyclerActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.mash_up.crema.R;
import kr.co.mash_up.crema.util.Defines;
import kr.co.mash_up.crema.util.ToastUtil;

/**
 * Created by bigstark on 2017. 3. 4..
 */

public class SearchRegionActivity extends CyclerActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {


    private GoogleMap map;
    private GoogleApiClient googleApiClient;


    @BindView(R.id.mv_search_region) MapView mvSearchRegion;
    @BindView(R.id.et_search_region) EditText etSearch;


    @OnClick(R.id.btn_search_region_search)
    void onSearchClicked() {
        String keyword = etSearch.getText().toString();
        if (TextUtils.isEmpty(keyword.trim())) {
            ToastUtil.toast("주소를 입력해주세요");
            return;
        }

        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addressList = geocoder.getFromLocationName(keyword, 5);
            if (addressList.size() > 0) {
                Address address = addressList.get(0);
                LatLng center = new LatLng(address.getLatitude(), address.getLongitude());
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(center, 16));
            }
        } catch (IOException e) {}

    }


    @OnClick(R.id.btn_search_region_ok)
    void onOkClicked() {
        LatLng latlng = map.getCameraPosition().target;

        Intent intent = new Intent();
        intent.putExtra(Defines.KEY_LATITUDE, latlng.latitude);
        intent.putExtra(Defines.KEY_LONGITUDE, latlng.longitude);
        setResult(RESULT_OK, intent);

        finish();
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_region_main);
        setUnbinder(ButterKnife.bind(this));
        mvSearchRegion.onCreate(savedInstanceState);
        mvSearchRegion.getMapAsync(this);

        googleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;
        this.map.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                LatLng center = map.getCameraPosition().target;
                Log.v("CREMA_TAG", getCurrentAddressString(center.latitude, center.longitude));
            }
        });

        setCurrentPosition();
    }

    private void setCurrentPosition() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try {
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            LatLng center = new LatLng(location.getLatitude(), location.getLongitude());
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(center, 16));
        } catch (SecurityException e) {
        }
    }


    private String getCurrentAddressString(double latitude, double longitude) {
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
            if (addressList.size() > 0) {
                String address = addressList.get(0).getAddressLine(0);
                String[] addressSplit = address.split(" ");

                return String.format("%s %s", addressSplit[2], addressSplit[3]);
            }
        } catch (IOException e) {}

        return "위치를 찾을 수 없습니다.";
    }



    @Override
    public void onConnected(@Nullable Bundle bundle) {
        try {
            PendingResult<PlaceLikelihoodBuffer> result = Places.PlaceDetectionApi.getCurrentPlace(googleApiClient, null);
            result.setResultCallback(new ResultCallback<PlaceLikelihoodBuffer>() {
                @Override
                public void onResult(PlaceLikelihoodBuffer likelyPlaces) {
                    for (PlaceLikelihood placeLikelihood : likelyPlaces) {
                        Log.i("CREMA_TAG", String.format("Place '%s' has likelihood: %g",
                                placeLikelihood.getPlace().getAddress(),
                                placeLikelihood.getLikelihood()));
                    }
                    likelyPlaces.release();
                }
            });
        } catch (SecurityException e) {}
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
        mvSearchRegion.onStart();
    }


    @Override
    protected void onStop() {
        mvSearchRegion.onStop();
        googleApiClient.disconnect();
        super.onStop();
    }


    @Override
    protected void onDestroy() {
        mvSearchRegion.onDestroy();
        super.onDestroy();
    }

}
