package com.xc.lovelife.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.xc.lovelife.R;
import com.xc.lovelife.base.BaseFragment;

/**
 * Created by xum19 on 2017/11/16.
 */

public class FindFragment extends BaseFragment {

    private ListView listView;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setTitle("发现");

        setBaseLeftIcon(R.mipmap.add, "发表动态", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), TrendsActivity.class));
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_find;
    }

    @Override
    protected void findViews() {
        //listView = getView(R.id.find_listview);
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
