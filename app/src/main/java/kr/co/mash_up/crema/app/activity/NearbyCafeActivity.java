package kr.co.mash_up.crema.app.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.*;
import android.os.Bundle;
import android.support.annotation.BinderThread;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bigstark.cycler.CyclerActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindDimen;
import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kr.co.mash_up.crema.app.adapter.CafeListAdapter;
import kr.co.mash_up.crema.PermissionRequester;
import kr.co.mash_up.crema.R;
import kr.co.mash_up.crema.model.BaseListModel;
import kr.co.mash_up.crema.model.cafe.CafeModel;
import kr.co.mash_up.crema.rest.CremaClient;
import kr.co.mash_up.crema.rest.cafe.CafeService;
import kr.co.mash_up.crema.util.Defines;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sun on 2017. 1. 24..
 */

public class NearbyCafeActivity extends CyclerActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Context mContext;

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    LocationManager locationmanager;

    TextView tvAddr;

    Double latitude = 0.0;
    Double longtitude = 0.0;

    @OnClick(R.id.ll_nearby_addr)
    void onAddrClicked() {
        Intent intent = new Intent(Defines.INTENT_SEARCH_REGION_ACTIVITY);
        startActivity(intent);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nearby_cafe_list);
        setUnbinder(ButterKnife.bind(this));

        init();

        int result = new PermissionRequester.Builder(NearbyCafeActivity.this)
                .setTitle("권한 요청")
                .setMessage("권한을 요청합니다.")
                .setPositiveButtonName("네")
                .setNegativeButtonName("아니요")
                .create()
                .request(Manifest.permission.ACCESS_FINE_LOCATION, 1000, new PermissionRequester.OnClickDenyButtonListener() {
                    @Override
                    public void onClick(Activity activity) {
                        Log.d("xxx", "cancel");
                    }
                });
        Log.e("Permission", result + " / " + PermissionRequester.ALREADY_GRANTED);
        if (result == PermissionRequester.ALREADY_GRANTED) {
            //      Log.d("result", "already granted");
            //    if (ActivityCompat.checkSelfPermission(NearbyCafeActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            startLocationService();
            //todo 권한이 이미존재함
        } else if (result == PermissionRequester.NOT_SUPPORT_VERSION) {
            startLocationService();
            //todo 마시멜로우 이상 버젼 아님
        } else if (result == PermissionRequester.REQUEST_PERMISSION) {
            //todo 요청함 응답기다림
        }


        mContext = getApplicationContext();

        recyclerView = (RecyclerView) findViewById(R.id.rv_nearby_recyclerview);
        recyclerView.setHasFixedSize(true);

        ArrayList<CafeModel> items = new ArrayList<>();

        CremaClient.getService(CafeService.class)
                .getCafes(30, 30, "1")
                .enqueue(new Callback<BaseListModel<CafeModel>>() {
                    @Override
                    public void onResponse(Call<BaseListModel<CafeModel>> call, Response<BaseListModel<CafeModel>> response) {
                        BaseListModel<CafeModel> body = response.body();
                        List<CafeModel> datas = body.getDatas();

                        adapter = new CafeListAdapter(datas, mContext);
                        recyclerView.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure(Call<BaseListModel<CafeModel>> call, Throwable t) {

                    }
                });

        layoutManager = new LinearLayoutManager(this);

        // 지정된 레이아웃매니저를 RecyclerView에 Set 해주어야한다.
        recyclerView.setLayoutManager(layoutManager);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.dl_nearby_drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nearcafemenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_search) {
//            Intent intent = new Intent();
//
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_packed) {
            // todo
        } else if (id == R.id.nav_written) {
            //todo
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.dl_nearby_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.tb_nearby_toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_nearby_floatingbutton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NearbyCafeActivity.this, WriteReviewActivity.class));
            }
        });



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.dl_nearby_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nv_nearby_nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        tvAddr = (TextView) findViewById(R.id.tv_nearby_addr);

    }

    private void startLocationService() {
        locationmanager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        Log.e("startLocationService", "startLocationService");
        long minTime = 1000;
        float minDistance = 1;

        if (ActivityCompat.checkSelfPermission(NearbyCafeActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationmanager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, mLocationListener);
//        locationmanager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, minTime, minDistance, mLocationListener);
        Criteria c = new Criteria();
        //최적의 하드웨어 이름을 리턴받는다.
        //  String provider = locationmanager.getBestProvider(c, true);
        //   Location location = locationmanager.getLastKnownLocation(provider);
        //   Log.e("onLocationChanged", location.getLatitude() + " / " + location.getLongitude());
    }

    private void stopLocationService() {
        if (ActivityCompat.checkSelfPermission(NearbyCafeActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationmanager.removeUpdates(mLocationListener);
    }

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            latitude = location.getLatitude();
            longtitude = location.getLongitude();

            if (tvAddr != null)
                tvAddr.setText(latitude + "  " + longtitude);
            Log.e("onLocationChanged", "===== On Location Changed ===== ");
            Log.e("onLocationChanged", location.getLatitude() + " / " + location.getLongitude());

            tvAddr.setText( location.getLatitude()+"  "+location.getLongitude());
            stopLocationService();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

}
