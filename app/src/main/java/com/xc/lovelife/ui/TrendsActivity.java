package com.xc.lovelife.ui;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;

import android.view.Gravity;

import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.xc.lovelife.MainActivity;
import com.xc.lovelife.R;
import com.xc.lovelife.adapter.GridViewAddImgesAdpter;
import com.xc.lovelife.base.BaseActivity;
import com.xc.lovelife.bean.Post;
import com.xc.lovelife.utils.BmobUtil;
import com.xc.lovelife.widget.PopuChoooseWindow;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;

/**
 * Created by xum19 on 2017/12/21.
 */

public class TrendsActivity extends BaseActivity implements PopuChoooseWindow.OnFeedBackChooseListener {

    private static final String TAG = "TrendsActivity";

    private EditText editTextTrends;

    private GridView gw;

    private Button submitButton;

    private List<LocalMedia> datas;
    private GridViewAddImgesAdpter gridViewAddImgesAdpter;

    private final int PHOTO_REQUEST_CAREMA = 1;// 拍照
    private final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择

    private List<String> picFilePath;//选择的图片路径
    private ProgressDialog progressDialog;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("发表动态");

        setBaseLeftIcon(R.mipmap.back, "返回", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        setBaseRightIcon1(R.mipmap.button_sure, "发表", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadTrends(datas);
            }
        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_trends;
    }

    @Override
    protected void findViews() {
        editTextTrends = getView(R.id.et_trends);
        gw = getView(R.id.gw);
        //submitButton = getView(R.id.btn_submit_trends);
    }

    @Override
    protected void formatViews() {
        gridViewAddImgesAdpter = new GridViewAddImgesAdpter(datas, this);
        gw.setAdapter(gridViewAddImgesAdpter);
        gw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                showChoose();
            }
        });
    }

    @Override
    protected void formatData() {
        datas = new ArrayList<>();
    }

    @Override
    protected void getBundle(Bundle bundle) {

    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void onCameraItemClick() {
        PictureSelector.create(TrendsActivity.this)
                .openCamera(PictureMimeType.ofAll())
                .forResult(PHOTO_REQUEST_CAREMA);
    }

    @Override
    public void onAlbumItemClick() {
        PictureSelector.create(TrendsActivity.this)
                .openGallery(PictureMimeType.ofAll())
                .forResult(PHOTO_REQUEST_GALLERY);
    }

    private void showChoose() {
        PopuChoooseWindow popuChoooseWindow = new PopuChoooseWindow(this);

        popuChoooseWindow.showAtLocation(gw, Gravity.BOTTOM, 0, 0);

        lightOff();

        popuChoooseWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lightOn();
            }
        });

        popuChoooseWindow.setOnFeedBackChooseListener(this);

    }

    private void lightOff() {
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.alpha = 0.3f;
        getWindow().setAttributes(layoutParams);

    }

    private void lightOn() {

        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.alpha = 1.0f;
        getWindow().setAttributes(layoutParams);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.i(TAG, "requestCode: " + requestCode);
        if (resultCode == RESULT_OK) {
                    // 图片选择结果回调
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    Log.i(TAG, "onActivityResult: " + selectList.toString());

                    datas = selectList;

                    gridViewAddImgesAdpter.notifyDataSetChanged(selectList);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
//                    Map<String,Object> map=new HashMap<>();
//
//                    for (int i = 0; i < selectList.size(); i++){
//                        map.put("path",selectList.get(i).getPath());
//                        datas.add(map);
//                        gridViewAddImgesAdpter.notifyDataSetChanged(datas);
//                    }


        }
    }

    /**
     * 发布动态
     * @param dates 图片的路径
     */
    private void uploadTrends(List<LocalMedia> dates){
        final String[] filePaths = new String[dates.size()];
        for (int i = 0; i < dates.size(); i++){
            filePaths[i] = dates.get(i).getPath();
        }

        BmobFile.uploadBatch(filePaths, new UploadBatchListener() {
            @Override
            public void onSuccess(List<BmobFile> list, List<String> urls) {
                if (urls.size() == filePaths.length){
                    Post post = new Post();
                    BmobUser user = BmobUser.getCurrentUser();
                    post.setContent(editTextTrends.getText().toString());
                    post.setId("1");
                    post.setAuthor(user);
                    post.setPic(urls);
                    BmobUtil.insertObject(post, context);
                }
            }

            @Override
            public void onProgress(int i, int i1, int i2, int i3) {
                //startProgressDialog(i1);
                if (i3 == 100)
                    finish();
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

}
