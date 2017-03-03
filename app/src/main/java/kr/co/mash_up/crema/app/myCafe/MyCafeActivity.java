package kr.co.mash_up.crema.app.myCafe;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bigstark.cycler.CyclerActivity;

import java.util.ArrayList;
import java.util.List;

import kr.co.mash_up.crema.R;
import kr.co.mash_up.crema.app.activity.WriteReviewActivity;
import kr.co.mash_up.crema.app.adapter.CafeListAdapter;
import kr.co.mash_up.crema.model.BaseListModel;
import kr.co.mash_up.crema.model.cafe.CafeModel;
import kr.co.mash_up.crema.rest.CremaClient;
import kr.co.mash_up.crema.rest.cafe.CafeService;
import kr.co.mash_up.crema.util.Defines;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sun on 2017. 3. 3..
 */

public class MyCafeActivity extends CyclerActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Context mContext;

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mycafe_list);

        init();

        mContext = getApplicationContext();

        recyclerView = (RecyclerView) findViewById(R.id.rv_nearby_recyclerview);
        recyclerView.setHasFixedSize(true);

        CremaClient.getService(CafeService.class)
                .getCafes(30, 30, "1")
                .enqueue(new Callback<BaseListModel<CafeModel>>() {
                    @Override
                    public void onResponse(Call<BaseListModel<CafeModel>> call, Response<BaseListModel<CafeModel>> response) {
                        BaseListModel<CafeModel> body = response.body();
                        List<CafeModel> datas = body.getDatas();

                        adapter = new MyCafeListAdapter(datas, mContext);
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

        if(id==R.id.nav_nearby){
            Intent intent = new Intent(Defines.INTENT_NEARBY_CAFE_ACTIVITY);
            startActivity(intent);
            finish();
        }
        else if (id == R.id.nav_picked) {
            Intent intent = new Intent(Defines.INTENT_PICK_CAFE_ACTIVITY);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_written) {
            Intent intent = new Intent(Defines.INTENT_MY_CAFE_ACTIVITY);
            startActivity(intent);
            finish();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.dl_nearby_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.tb_nearby_toolbar);
        toolbar.setTitle("내가 쓴 리뷰");
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.dl_nearby_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nv_nearby_nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }


}
