package com.xc.lovelife.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.xc.lovelife.R;
import com.xc.lovelife.base.BaseFragment;

/**
 * Created by xum19 on 2017/11/16.
 */

public class ForumFragment extends BaseFragment {

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setTitle("理财");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_forum;
    }

    @Override
    protected void findViews() {

    }

    @Override
    protected void formatViews() {

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
