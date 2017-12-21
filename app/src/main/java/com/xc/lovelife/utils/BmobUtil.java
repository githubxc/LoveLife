package com.xc.lovelife.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.xc.lovelife.MainActivity;
import com.xc.lovelife.ui.LoginActivity;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by xum19 on 2017/11/20.
 */

public class BmobUtil {

    private static final String TAG = "BmobUtil";

    private SharedPreferences sp;

    private SharedPreferences.Editor editor;

    private boolean isLogin;

    private SharedPreferencesUtil sharedPreferencesUtil;


    /**
     * 添加数据
     */
    public static void insertObject(final BmobObject obj, Context context){
        obj.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e==null){
                    //cancelProgressDialog();
                    //
                    Toast.makeText(context, "上传成功", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    /**
     * 用户登录
     * @param obj
     * @param activity
     */
    public static void login(final BmobUser obj, Activity activity){
        obj.login(new SaveListener<BmobUser>() {
            @Override
            public void done(BmobUser bmobUser, BmobException e) {
                if(e==null){
                    Toast.makeText(activity, "登录成功" + bmobUser.getObjectId(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(activity,MainActivity.class);
                    activity.startActivity(intent);
                    activity.finish();
                    //通过BmobUser user = BmobUser.getCurrentUser()获取登录成功后的本地用户信息
                    //如果是自定义用户对象MyUser，可通过MyUser user = BmobUser.getCurrentUser(MyUser.class)获取自定义用户信息
                }else{
                    Log.i(TAG, "error: " + e.getMessage());
                }
            }
        });
    }



}
