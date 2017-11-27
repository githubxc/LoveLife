package com.xc.lovelife.ui;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.xc.lovelife.R;
import com.xc.lovelife.apimanager.Api;
import com.xc.lovelife.base.BaseFragment;
import com.xc.lovelife.bean.Bill;
import com.xc.lovelife.widget.BillRecycleViewAdapter;
import com.xc.lovelife.widget.MyDividerItemDecoration;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;


/**
 * Created by xum19 on 2017/11/16.
 */

public class BillFragment extends BaseFragment {

    private String TAG = "BillFragment";

    private RecyclerView mRecyclerView;
    private ArrayList<Bill> bill;
    private ArrayList<Bill> billData;
    private BillRecycleViewAdapter mAdapter;
    private Context context;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        billData = new ArrayList<>();
        context = getContext();

        initBillData();

        setTitle("账单");
        setBaseLeftIcon(R.mipmap.add, "addRecord", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpTo(AddBillActivity.class);
            }
        });
        setBaseRightIcon1(R.mipmap.diagram, "扇形图", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toast("扇形图");
            }
        });
//        setBaseRightIcon2(R.mipmap.add, "添加记录", new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                toast("添加记录");
//            }
//        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_bill;
    }

    @Override
    protected void findViews() {
        mRecyclerView = getView(R.id.id_recyclerview);
    }

    @Override
    protected void formatViews() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new MyDividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));


    }
    public void initBillData() {
        bill = new ArrayList<>();

        BmobQuery<Bill> billBmobQuery = new BmobQuery<>();
        billBmobQuery.addWhereEqualTo("userName", Api.username);
        billBmobQuery.findObjects(new FindListener<Bill>() {
            @Override
            public void done(List<Bill> list, BmobException e) {
                if (e == null) {
                    for (Bill billQuery : list) {
                        //toast("查询成功，返回" + list.size() + "条数据");
                        bill.add(billQuery);
                        mRecyclerView.setAdapter(new BillRecycleViewAdapter(context, bill));
                        //bill.add(new Bill(billQuery.getUserName(), billQuery.getPic(), billQuery.getType(), billQuery.getData(), billQuery.getNote(), billQuery.getAmount()));
                    }

                    billData = bill;
                } else {
                    Log.i(TAG, "FaiedBmobQuery: " + e.getMessage() + e.getErrorCode());
                }
            }
        });
        Log.i(TAG, "initBillData: " + bill.size());

    }

    @Override
    protected void formatData() {

    }

    @Override
    protected void getBundle() {

    }

    @Override
    public void onClick(View view) {

    }

}
