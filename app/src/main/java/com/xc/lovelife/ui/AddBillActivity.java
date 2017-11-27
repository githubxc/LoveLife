package com.xc.lovelife.ui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.xc.lovelife.R;
import com.xc.lovelife.apimanager.Api;
import com.xc.lovelife.base.BaseActivity;
import com.xc.lovelife.bean.Bill;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Calendar;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;


/**
 * Created by xum19 on 2017/11/23.
 */

public class AddBillActivity extends BaseActivity {

    private RadioButton rbOut, rbIn;//收入 支出
    private Spinner spinner;//类型选择
    private TextView tvDate;
    private EditText etAmount;
    private EditText etNote;
    private Button btnSave;

    private String inOrout;
    private String type;
    private Integer amount;
    private String date;
    private String note;

    private File myPicFile;

    final int DATE_DIALOG = 1;
    int mYear, mMonth, mDay;

    private Bitmap bitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("记一笔");


        setBaseLeftIcon(R.mipmap.back, "back", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final Calendar ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        mDay = ca.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_addbill;
    }

    @Override
    protected void findViews() {
        rbOut = (RadioButton) findViewById(R.id.rb_out);
        rbIn = (RadioButton) findViewById(R.id.rb_in);
        spinner = (Spinner) findViewById(R.id.spinner);
        tvDate = (TextView) findViewById(R.id.addbill_tv_dateview);
        etAmount = (EditText) findViewById(R.id.addbill_et_amount);
        etNote = (EditText) findViewById(R.id.addbill_et_note);
        btnSave = (Button) findViewById(R.id.addbill_btn_save);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.rb_in:
                    inOrout = "收入";
                    break;
            case R.id.rb_out:
                    inOrout = "支出";
                    break;
            case R.id.addbill_tv_dateview:
                    showDialog(DATE_DIALOG);//显示日期选择器
                    break;
            case R.id.addbill_btn_save:
                    saveBillInfo();
                    break;
        }

    }

    private void saveBillInfo(){

        if ((rbIn.isChecked() || rbOut.isChecked())
                && (tvDate.getText().toString() != null)
                && (etAmount.getText().toString() != null)
                && (etNote.getText().toString() != null)) {

                    amount = Integer.valueOf(etAmount.getText().toString());
                    note = etNote.getText().toString();

                    BmobFile bmobFile = new BmobFile(myPicFile);
                    bmobFile.upload(new UploadFileListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null){
                                Bill bill = new Bill();
                                bill.setUserName(Api.username);
                                bill.setPic(bmobFile);
                                bill.setAmount(amount);
                                bill.setNote(note);
                                bill.setInOrout(inOrout);
                                bill.setType(type);
                                bill.setDate(date);
                                bill.save(new SaveListener<String>() {
                                    @Override
                                    public void done(String s, BmobException e) {
                                        if (e == null)
                                            toast("添加成功" + s);
                                        else
                                            toast("添加失败" + e.getMessage() + e.getErrorCode());
                                    }
                                });
                            }
                            else
                                toast("添加失败" + e.getMessage() + e.getErrorCode());
                        }
                    });

        }else
            toast("请将信息填写完整！");

    }

    @Override
    protected void formatViews() {

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                String[] spinner = getResources().getStringArray(R.array.spinnertype);
                type = spinner[pos];

                int picId = R.mipmap.ic_launcher;
                switch (type){
                    case "购物":
                        picId = R.mipmap.shopping;
                        break;
                    case "吃饭":
                        picId = R.mipmap.meal;
                        break;
                    case "看电影":
                        picId = R.mipmap.coupe;
                        break;
                    case "约会":
                        picId = R.mipmap.coupe;
                        break;
                    case "聚餐":
                        picId = R.mipmap.jucan;
                        break;
                    case "兼职":
                        picId = R.mipmap.parttimejob;
                        break;
                    case "工资":
                        picId = R.mipmap.salary;
                        break;
                    case "奖金":
                        picId = R.mipmap.reward;
                        break;
                }
                bitmap = BitmapFactory.decodeResource(getResources(), picId);
                saveBitmap(bitmap, "pic.png");
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

        tvDate.setOnClickListener(this);
        rbOut.setOnClickListener(this);
        rbIn.setOnClickListener(this);
        etAmount.setOnClickListener(this);
        etNote.setOnClickListener(this);
        btnSave.setOnClickListener(this);

    }

    @Override
    protected void formatData() {
    }

    @Override
    protected void getBundle(Bundle bundle) {

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG:
                return new DatePickerDialog(this, mdateListener, mYear, mMonth, mDay);
        }
        return null;
    }

    /**
     * 设置日期 利用StringBuffer追加
     */
    public void display() {
        date = String.valueOf(new StringBuffer().append(mYear).append("-").append(mMonth + 1).append("-").append(mDay));
        tvDate.setText(date);
    }

    private DatePickerDialog.OnDateSetListener mdateListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            display();
        }
    };

    /**
     * 获得sd路径
     * @return
     */
    public static String getSDPath(){
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED); //判断sd卡是否存在
        if (sdCardExist)
        {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        }
        return sdDir.toString();
    }

    public void saveBitmap(Bitmap bitmap, String fileName) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(),"LoveLife");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        myPicFile = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(myPicFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            Log.i("AddBillActivity", "initBillData: " + "图片保存成功："  + myPicFile);
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("AddBillActivity", "initBillData: " + "图片保存失败："  + e.getMessage());
        }
        // 把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(getContentResolver(), myPicFile.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 通知图库更新
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + "/sdcard/namecard/")));
    }


}
