package com.xc.lovelife;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.hjm.bottomtabbar.BottomTabBar;
import com.xc.lovelife.base.BaseActivity;
import com.xc.lovelife.ui.BillFragment;
import com.xc.lovelife.ui.FindFragment;
import com.xc.lovelife.ui.ForumFragment;
import com.xc.lovelife.ui.MineFragment;
import com.yanzhenjie.alertdialog.AlertDialog;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.RationaleListener;

import java.util.List;

import cn.bmob.v3.Bmob;

public class MainActivity extends BaseActivity {

    //权限请求
    private static int PermissionCode = 100;

    //导航栏
    private BottomTabBar mBottomTabBar;

    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getApplicationContext();

        //请求获取权限
        initPermission();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void findViews() {
        mBottomTabBar = (BottomTabBar) findViewById(R.id.bottom_tab_bar);
    }

    @Override
    protected void formatViews() {
        mBottomTabBar.init(getSupportFragmentManager())
                .setImgSize(75, 75)
                .setFontSize(12)
                .setChangeColor(Color.parseColor("#1E90FF"),Color.BLACK)
                .setTabPadding(4, 6, 0)
                .addTabItem("账单", R.mipmap.bill_full, R.mipmap.bill_normal, BillFragment.class)
                .addTabItem("论坛", R.mipmap.forum_full, R.mipmap.forum_normal, ForumFragment.class)
                .addTabItem("发现", R.mipmap.find_full, R.mipmap.find_normal, FindFragment.class)
                .addTabItem("我的", R.mipmap.my_full, R.mipmap.my_normal, MineFragment.class)
                .isShowDivider(false)
                .setOnTabChangeListener(new BottomTabBar.OnTabChangeListener() {
                    @Override
                    public void onTabChange(int position, String name, View view) {
                        Log.i("TGA", "位置：" + position + "      选项卡：" + name);
                    }
                });

    }

    //初始化权限请求管理
    private void initPermission(){
        AndPermission.with(context)
                .requestCode(PermissionCode)
                .permission(Manifest.permission.INTERNET,
                        Manifest.permission.ACCESS_NETWORK_STATE,
                        Manifest.permission.WAKE_LOCK,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_PHONE_STATE)
                .rationale(rationaleListener)
                .callback(listener)
                .start();
    }

    //权限获取回调
    private PermissionListener listener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, List<String> grantedPermissions) {
            // Successfully.
            if(requestCode == PermissionCode) {
                // TODO ...
            }
        }

        @Override
        public void onFailed(int requestCode, List<String> deniedPermissions) {
            // Failure.
            if(requestCode == PermissionCode) {
                // 用自定义的提示语。
                AndPermission.defaultSettingDialog(activity, PermissionCode)
                        .setTitle("权限申请失败")
                        .setMessage("您拒绝了我们必要的一些权限，请在设置中授权！")
                        .setPositiveButton("好，去设置")
                        .show();
            }
        }
    };

    /**
     * Rationale支持，这里自定义对话框。
     */
    private RationaleListener rationaleListener = (requestCode, rationale) -> {
        AlertDialog.newBuilder(this)
                .setTitle("Tips")
                .setMessage("Request permission to recommend content for you.")
                .setPositiveButton("OK", (dialog, which) -> {
                    rationale.resume();
                })
                .setNegativeButton("NO", (dialog, which) -> {
                    rationale.cancel();
                }).show();
    };

    @Override
    protected void formatData() {

    }

    @Override
    protected void getBundle(Bundle bundle) {

    }

    @Override
    public void onClick(View view) {

    }
}
