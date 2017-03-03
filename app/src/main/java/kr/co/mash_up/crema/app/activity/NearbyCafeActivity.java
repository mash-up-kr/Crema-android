package kr.co.mash_up.crema.app.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.*;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bigstark.cycler.CyclerActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    TextView tvAddr;

    @OnClick(R.id.ll_nearby_addr)
    void onAddrClicked() {
        Intent intent = new Intent(Defines.INTENT_SEARCH_REGION_ACTIVITY);
        startActivityForResult(intent, Defines.REQUEST_SEARCH_REGION);
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
        if (result == PermissionRequester.ALREADY_GRANTED) {
            setCurrentPosition();
        }


        recyclerView = (RecyclerView) findViewById(R.id.rv_nearby_recyclerview);
        recyclerView.setHasFixedSize(true);

        CremaClient.getService(CafeService.class)
                .getCafes(30, 30, "1")
                .enqueue(new Callback<BaseListModel<CafeModel>>() {
                    @Override
                    public void onResponse(Call<BaseListModel<CafeModel>> call, Response<BaseListModel<CafeModel>> response) {
                        BaseListModel<CafeModel> body = response.body();
                        List<CafeModel> datas = body.getDatas();

                        adapter = new CafeListAdapter(datas, NearbyCafeActivity.this);
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

    private void setCurrentPosition() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try {
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            tvAddr.setText(getAddressString(location.getLatitude(), location.getLongitude()));
        } catch (SecurityException e) {
        }
    }


    private String getAddressString(double latitude, double longitude) {
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
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.dl_nearby_drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }

        if (requestCode == Defines.REQUEST_SEARCH_REGION) {
            double latitude = data.getDoubleExtra(Defines.KEY_LATITUDE, 0);
            double longitude = data.getDoubleExtra(Defines.KEY_LONGITUDE, 0);

            String address = getAddressString(latitude, longitude);
            tvAddr.setText(address);

            CremaClient.getService(CafeService.class)
                    .getCafes(latitude, longitude, "1")
                    .enqueue(new Callback<BaseListModel<CafeModel>>() {
                        @Override
                        public void onResponse(Call<BaseListModel<CafeModel>> call, Response<BaseListModel<CafeModel>> response) {
                            BaseListModel<CafeModel> body = response.body();
                            List<CafeModel> datas = body.getDatas();

                            adapter = new CafeListAdapter(datas, NearbyCafeActivity.this);
                            recyclerView.setAdapter(adapter);
                        }

                        @Override
                        public void onFailure(Call<BaseListModel<CafeModel>> call, Throwable t) {

                        }
                    });
        }
    }
}
