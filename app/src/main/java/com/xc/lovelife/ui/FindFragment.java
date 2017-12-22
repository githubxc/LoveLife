package com.xc.lovelife.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.xc.lovelife.R;
import com.xc.lovelife.adapter.TrendsAdapter;
import com.xc.lovelife.base.BaseFragment;
import com.xc.lovelife.bean.CommentsBean;
import com.xc.lovelife.bean.Image;
import com.xc.lovelife.bean.Post;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by xum19 on 2017/11/16.
 */

public class FindFragment extends BaseFragment {

    private static final String TAG = "FindFragment";

    private ListView listView;

    private List<List<Image>> imagesList;

    private String[][] images=new String[][]{
            {"http://bmob-cdn-15177.b0.upaiyun.com/2017/12/21/8863f9d44085c8b680ae0c6e89af60e8.png","250","250"}
            ,{"http://bmob-cdn-15177.b0.upaiyun.com/2017/12/21/8863f9d44085c8b680ae0c6e89af60e8.png","640","960"}
            ,{"http://bmob-cdn-15177.b0.upaiyun.com/2017/12/21/8863f9d44085c8b680ae0c6e89af60e8.png","250","250"}
            ,{"http://bmob-cdn-15177.b0.upaiyun.com/2017/12/21/8863f9d44085c8b680ae0c6e89af60e8.png","250","250"}
            ,{"http://bmob-cdn-15177.b0.upaiyun.com/2017/12/21/8863f9d44085c8b680ae0c6e89af60e8.png","250","250"}
            ,{"http://bmob-cdn-15177.b0.upaiyun.com/2017/12/21/8863f9d44085c8b680ae0c6e89af60e8.png","250","250"}
            ,{"http://bmob-cdn-15177.b0.upaiyun.com/2017/12/21/8863f9d44085c8b680ae0c6e89af60e8.png","250","250"}
            ,{"http://bmob-cdn-15177.b0.upaiyun.com/2017/12/21/8863f9d44085c8b680ae0c6e89af60e8.png","250","250"}
            ,{"http://bmob-cdn-15177.b0.upaiyun.com/2017/12/21/8863f9d44085c8b680ae0c6e89af60e8.png","1280","800"}
    };

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

        initBmobData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_find;
    }

    @Override
    protected void findViews() {
        listView = getView(R.id.find_listview);
    }

    @Override
    protected void formatViews() {

    }

    @Override
    protected void formatData() {
//        Log.i(TAG, "formatDataRun: " );
//        BmobQuery<Post> postBmobQuery = new BmobQuery<>();
//        postBmobQuery.addWhereEqualTo("id", String.valueOf(1));
//        postBmobQuery.findObjects(new FindListener<Post>() {
//            @Override
//            public void done(List<Post> list, BmobException e) {
//                if (e == null){
//                    for (Post post : list) {
//
//                        ArrayList<Image> itemList = new ArrayList<>();
//                        for (int j = 0; j <= list.size(); j++) {
//                            itemList.add(new Image(post.getPic().get(j), 250, 250));
//                            Log.i(TAG, "formatData: " + j);
//                        }
//                        imagesList.add(itemList);
//                        Log.i(TAG, "formatData: " + imagesList.size());
//                    }
//
//                }
//            }
//        });
        imagesList = new ArrayList<>();
        //从一到9生成9条朋友圈内容，分别是1~9张图片
        for(int i=0;i<9;i++){
            ArrayList<Image> itemList=new ArrayList<>();
            for(int j=0;j<=i;j++){
                itemList.add(new Image(images[j][0],Integer.parseInt(images[j][1]),Integer.parseInt(images[j][2])));
            }
            imagesList.add(itemList);
        }

    }

    @Override
    protected void getBundle() {

    }

    @Override
    public void onClick(View view) {

    }

    private void initBmobData(){

        final List<BmobUser> userBeanLists = new ArrayList<>();
        BmobQuery<CommentsBean> commentsBeanBmobQuery = new BmobQuery<>();
        commentsBeanBmobQuery.addWhereEqualTo("commentsId", 210152);
        commentsBeanBmobQuery.findObjects(new FindListener<CommentsBean>() {
            @Override
            public void done(List<CommentsBean> commentsList, BmobException e) {
                if (e == null){

                    Log.i(TAG, "BmobUser: " + commentsList.get(0).getReplyUser().getUsername());
                    Log.i(TAG, "initBmobData: " + commentsList.toString());
                    BmobQuery<BmobUser> userBeanBmobQuery = new BmobQuery<>();
                    userBeanBmobQuery.findObjects(new FindListener<BmobUser>() {
                        @Override
                        public void done(List<BmobUser> userList, BmobException e) {
                            for (BmobUser userBean : userList)
                                userBeanLists.add(userBean);
                        }
                    });

                    listView.setAdapter(new TrendsAdapter(getContext(),imagesList, commentsList, userBeanLists));
                }else
                    Log.i(TAG, "done: " + e.getMessage());
            }
        });
    }
}
