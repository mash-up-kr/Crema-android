package kr.co.mash_up.crema.app.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import kr.co.mash_up.crema.R;
import kr.co.mash_up.crema.model.cafe.CafeModel;

/**
 * Created by sun on 2017. 1. 24..
 */

public class CafeListAdapter extends RecyclerView.Adapter<CafeListAdapter.ViewHolder>{
    private Context context;
    private List<CafeModel> mItems;

    // Allows to remember the last item shown on screen
    private int lastPosition = -1;

    public CafeListAdapter(List<CafeModel> items, Context mContext)
    {
        mItems = items;
        context = mContext;
    }

    // 필수로 Generate 되어야 하는 메소드 1 : 새로운 뷰 생성
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // 새로운 뷰를 만든다
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_icon,parent,false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    // 필수로 Generate 되어야 하는 메소드 2 : ListView의 getView 부분을 담당하는 메소드
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        try {
            URL url = new URL(mItems.get(position).getThumbnail().getUrl());
            URLConnection conn = url.openConnection();
            conn.connect();
            BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
            Bitmap bm = BitmapFactory.decodeStream(bis);
            bis.close();
            holder.main.setImageBitmap(bm);
        } catch (Exception e) {

        }

        holder.name.setText(mItems.get(position).getName());
        holder.addr.setText(mItems.get(position).getLocation().getAddress());
        holder.hours.setText(mItems.get(position).isOpened()+"");

        setAnimation(holder.main, position);
    }

    // // 필수로 Generate 되어야 하는 메소드 3
    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder {

        public ImageView main;
        public TextView name;
        public TextView addr;
        public TextView hours;

        public ViewHolder(View view) {
            super(view);
            main=(ImageView)view.findViewById(R.id.iv_main) ;
            name=(TextView)view.findViewById(R.id.tv_main);
            addr=(TextView)view.findViewById(R.id.tv_addr);
            hours=(TextView)view.findViewById(R.id.tv_hours);
        }
    }

    private void setAnimation(View viewToAnimate, int position)
    {

        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }
}
