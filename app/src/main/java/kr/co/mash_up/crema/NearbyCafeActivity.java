package kr.co.mash_up.crema;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;

/**
 * Created by sun on 2017. 1. 24..
 */

public class NearbyCafeActivity extends AppCompatActivity {

    Context mContext;

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nearby_cafe_list);

        mContext = getApplicationContext();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);

        ArrayList<CafeList> items = new ArrayList<>();

        items.add(new CafeList(R.drawable.cafe_main, "CAFE SEOJONG1", "서울시 강남구 역삼동 12-3", "평일 07:00 - 24:00\n주말 09:00 - 18:00"));
        items.add(new CafeList(R.drawable.cafe_main, "CAFE SEOJONG2", "서울시 강남구 역삼동 12-3", "평일 07:00 - 24:00\n주말 09:00 - 18:00"));
        items.add(new CafeList(R.drawable.cafe_main, "CAFE SEOJONG3", "서울시 강남구 역삼동 12-3", "평일 07:00 - 24:00\n주말 09:00 - 18:00"));
        items.add(new CafeList(R.drawable.cafe_main, "CAFE SEOJONG4", "서울시 강남구 역삼동 12-3", "평일 07:00 - 24:00\n주말 09:00 - 18:00"));
        items.add(new CafeList(R.drawable.cafe_main, "CAFE SEOJONG5", "서울시 강남구 역삼동 12-3", "평일 07:00 - 24:00\n주말 09:00 - 18:00"));

        // StaggeredGrid 레이아웃을 사용한다
        //layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        layoutManager = new LinearLayoutManager(this);
        //layoutManager = new GridLayoutManager(this,3);

        // 지정된 레이아웃매니저를 RecyclerView에 Set 해주어야한다.
        recyclerView.setLayoutManager(layoutManager);

        adapter = new CafeListAdapter(items,mContext);
        recyclerView.setAdapter(adapter);

    }
}
