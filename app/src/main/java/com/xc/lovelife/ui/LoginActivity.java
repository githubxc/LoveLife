package com.xc.lovelife.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.xc.lovelife.MainActivity;
import com.xc.lovelife.R;
import com.xc.lovelife.base.BaseActivity;
import com.xc.lovelife.bean.MyUser;
import com.xc.lovelife.utils.SharedPreferencesUtil;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;

/**
 * Created by xum19 on 2017/12/21.
 */

public class LoginActivity extends BaseActivity {

    private EditText userEditText, psdEditText;

    private Button loginButton;

    private boolean isLogin = false;

    private SharedPreferencesUtil sharedPreferencesUtil;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferencesUtil = new SharedPreferencesUtil(LoginActivity.this, "user");
        isLogin = sharedPreferencesUtil.contain("isLogin");

        if (isLogin){
            //记住密码 方便下次登陆
            userEditText.setText(sharedPreferencesUtil.getSharedPreference("username", "").toString().trim());
            psdEditText.setText(sharedPreferencesUtil.getSharedPreference("psd", "").toString().trim());

            //第二次免登陆直接进入主界面
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void findViews() {
        userEditText = getView(R.id.et_username);
        psdEditText = getView(R.id.et_pwd);
        loginButton = getView(R.id.btn_login);

    }

    @Override
    protected void formatViews() {

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(userEditText.getText().toString(), psdEditText.getText().toString());
            }
        });
    }

    @Override
    protected void formatData() {

    }

    @Override
    protected void getBundle(Bundle bundle) {

    }

    @Override
    public void onClick(View v) {

    }

    private void login(String username, String pwd){

        BmobUser.loginByAccount(username, pwd, new LogInListener<MyUser>() {
            @Override
            public void done(MyUser user, BmobException e) {
                if(user!=null){

                    sharedPreferencesUtil.put("username", username);
                    sharedPreferencesUtil.put("psd", pwd);
                    sharedPreferencesUtil.put("isLogin", true);

                    Toast.makeText(activity, "登录成功" + user.getObjectId(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(activity,MainActivity.class);
                    activity.startActivity(intent);
                    activity.finish();
                }
            }
        });

    }
}
