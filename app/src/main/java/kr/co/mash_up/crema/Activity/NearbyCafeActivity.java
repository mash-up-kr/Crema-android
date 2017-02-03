package kr.co.mash_up.crema.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import kr.co.mash_up.crema.adapter.CafeListAdapter;
import kr.co.mash_up.crema.PermissionRequester;
import kr.co.mash_up.crema.R;
import kr.co.mash_up.crema.model.cafe.CafeModel;

/**
 * Created by sun on 2017. 1. 24..
 */

public class NearbyCafeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    Context mContext;

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nearby_cafe_list);

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

        if(result == PermissionRequester.ALREADY_GRANTED){
            Log.d("result","already granted");
            if(ActivityCompat.checkSelfPermission(NearbyCafeActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                //todo 권한이 이미존재함
            }
            else if(result == PermissionRequester.NOT_SUPPORT_VERSION){
                //todo 마시멜로우 이상 버젼 아님
            }
            else if(result==PermissionRequester.REQUEST_PERMISSION){
                //todo 요청함 응답기다림
            }
        }



        mContext = getApplicationContext();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);

        ArrayList<CafeModel> items = new ArrayList<>();

//        items.add(new CafeList(R.drawable.cafe_main, "CAFE SEOJONG1", "서울시 강남구 역삼동 12-3", "평일 07:00 - 24:00\n주말 09:00 - 18:00"));
//        items.add(new CafeList(R.drawable.cafe_main, "CAFE SEOJONG2", "서울시 강남구 역삼동 12-3", "평일 07:00 - 24:00\n주말 09:00 - 18:00"));
//        items.add(new CafeList(R.drawable.cafe_main, "CAFE SEOJONG3", "서울시 강남구 역삼동 12-3", "평일 07:00 - 24:00\n주말 09:00 - 18:00"));
//        items.add(new CafeList(R.drawable.cafe_main, "CAFE SEOJONG4", "서울시 강남구 역삼동 12-3", "평일 07:00 - 24:00\n주말 09:00 - 18:00"));
//        items.add(new CafeList(R.drawable.cafe_main, "CAFE SEOJONG5", "서울시 강남구 역삼동 12-3", "평일 07:00 - 24:00\n주말 09:00 - 18:00"));

        // StaggeredGrid 레이아웃을 사용한다
        //layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        layoutManager = new LinearLayoutManager(this);
        //layoutManager = new GridLayoutManager(this,3);

        // 지정된 레이아웃매니저를 RecyclerView에 Set 해주어야한다.
        recyclerView.setLayoutManager(layoutManager);

        adapter = new CafeListAdapter(items,mContext);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void init(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NearbyCafeActivity.this, WriteReviewActivity.class));
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }
}
