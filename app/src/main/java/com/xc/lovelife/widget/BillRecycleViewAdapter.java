package com.xc.lovelife.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.StrictMode;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xc.lovelife.R;
import com.xc.lovelife.apimanager.Api;
import com.xc.lovelife.bean.Bill;
import com.xc.lovelife.utils.RetrofitUtil;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;


/**
 * Created by xum19 on 2017/11/17.
 */

public class BillRecycleViewAdapter extends RecyclerView.Adapter<BillRecycleViewAdapter.MyViewHolder>
{

    private static String TAG = "BillRecycleViewAdapter";

    private List<Bill> mDatas;
    private Bitmap bitmap;
    private Context context;

    public BillRecycleViewAdapter(Context context, ArrayList<Bill> data) {
        this.context = context;
        this.mDatas = data;
    }

    public void updateData(ArrayList<Bill> data) {
        this.mDatas = data;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        // 实例化展示的view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.bill_item_layout, parent, false);
        // 实例化viewholder
        MyViewHolder viewHolder = new MyViewHolder(v);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork()
                .penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
                .penaltyLog().penaltyDeath().build());

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {

        Glide.with(context).load(mDatas.get(position).getPic().getFileUrl()).into(holder.billCircleImageView);
        //holder.billCircleImageView.setImageBitmap(getPicture(mDatas.get(position).getPic().getFileUrl()));
        holder.note.setText(mDatas.get(position).getNote());
        holder.data.setText(mDatas.get(position).getDate());

        if ((mDatas.get(position).getInOrout()).equals("收入")) {
            holder.amount.setText(" + " + mDatas.get(position).getAmount().toString());
            holder.amount.setTextColor(context.getResources().getColor(R.color.red_dark));
        }
        else {
            holder.amount.setText(" - " + mDatas.get(position).getAmount().toString());
            holder.amount.setTextColor(context.getResources().getColor(R.color.colorAccent));
        }
    }

    @Override
    public int getItemCount()
    {

        return mDatas == null ? 0 : mDatas.size();

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {

        CircleImageView billCircleImageView;
        TextView note;
        TextView data;
        TextView amount;

        public MyViewHolder(View view)
        {
            super(view);
            billCircleImageView = (CircleImageView) view.findViewById(R.id.bill_iv);
            note = (TextView) view.findViewById(R.id.bill_tv_note);
            data = (TextView) view.findViewById(R.id.bill_tv_data);
            amount = (TextView) view.findViewById(R.id.bill_tv_amount);

        }
    }

    public Bitmap getPicture(String path){
        Bitmap bm=null;

        try{
            URL url=new URL(path);
            URLConnection connection=url.openConnection();
            connection.connect();
            InputStream inputStream=connection.getInputStream();
            bm= BitmapFactory.decodeStream(inputStream);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  bm;
    }
}
