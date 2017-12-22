package com.xc.lovelife.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.xc.lovelife.MainActivity;
import com.xc.lovelife.R;
import com.xc.lovelife.bean.CommentsBean;
import com.xc.lovelife.bean.Image;
import com.xc.lovelife.ui.ImagePagerActivity;
import com.xc.lovelife.widget.CommentsView;
import com.xc.lovelife.widget.NineGridlayout;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;

import static com.xc.lovelife.R.id.trends_content;

/**
 * Created by xum19 on 2017/11/27.
 */

public class TrendsAdapter extends BaseAdapter {

    private static String TAG = "MainAdapter";
    private Context context;
    private List<List<Image>> datalist;
    private NineGridAdapter adapter;

    private List<Image> photoUrl;

    private List<CommentsBean> bmobList;

    private List<BmobUser> userBeans;

    public TrendsAdapter(Context context, List<List<Image>> datalist, List<CommentsBean> bmobList, List<BmobUser> userBeans) {

        Log.i(TAG, "MainAdapter: " + bmobList.size());
        this.context = context;
        this.datalist = datalist;
        this.bmobList = bmobList;
        this.userBeans = userBeans;
    }

    @Override
    public int getCount() {

        return datalist.size();
    }

    @Override
    public Object getItem(int position) {
        return datalist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        List<Image> itemList = datalist.get(position);

        photoUrl = itemList;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_ninegridlayout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.ivMore = (NineGridlayout) convertView.findViewById(R.id.iv_ngrid_layout);

            viewHolder.commentsView = (CommentsView) convertView.findViewById(R.id.commentView2);

            viewHolder.trendsUserPic = (ImageView) convertView.findViewById(R.id.trends_user_pic);
            viewHolder.trendsUserName = (TextView) convertView.findViewById(R.id.trends_username);
            viewHolder.trendsContent = (TextView) convertView.findViewById(R.id.trends_content);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (itemList.isEmpty() || itemList.isEmpty()) {
            viewHolder.ivMore.setVisibility(View.GONE);
        } else {
            viewHolder.ivMore.setVisibility(View.VISIBLE);
            handlerOneImage(viewHolder, itemList);
        }

        viewHolder.trendsUserName.setText(bmobList.get(0).getReplyUser().getObjectId());
        viewHolder.trendsContent.setText(bmobList.get(0).getContent());

        viewHolder.commentsView.setList(bmobList , userBeans);
        viewHolder.commentsView.setOnItemClickListener(new CommentsView.onItemClickListener() {
            @Override
            public void onItemClick(int position, CommentsBean bean) {

            }
        });
        viewHolder.commentsView.notifyDataSetChanged();

        return convertView;
    }

    private void handlerOneImage(ViewHolder viewHolder, List<Image> image) {
        adapter = new Adapter(context, image);
        viewHolder.ivMore.setAdapter(adapter);
        viewHolder.ivMore.setOnItemClickListerner(new NineGridlayout.OnItemClickListerner() {
            @Override
            public void onItemClick(View view, int position) {
                //do some thing
                Toast.makeText(context, image.get(position).getUrl(), Toast.LENGTH_SHORT).show();

                ImagePagerActivity.ImageSize imageSize = new ImagePagerActivity.ImageSize(view.getMeasuredWidth(), view.getMeasuredHeight());
                List<String> photoUrls = new ArrayList<String>();
                for(Image photoInfo : image){
                    photoUrls.add(photoInfo.getUrl());
                }


                ImagePagerActivity.startImagePagerActivity(((MainActivity) context), photoUrls, position, new ImagePagerActivity
                        .ImageSize(view.getMeasuredWidth(), view.getMeasuredHeight()));
            }
        });
    }


    class ViewHolder {
        public NineGridlayout ivMore;
        public CommentsView commentsView;

        public ImageView trendsUserPic;
        public TextView trendsUserName, trendsContent;
    }


    class Adapter extends NineGridAdapter {

        public Adapter(Context context, List list) {
            super(context, list);
        }

        @Override
        public int getCount() {
            return (list == null) ? 0 : list.size();
        }

        @Override
        public String getUrl(int position) {
            return getItem(position) == null ? null : ((Image)getItem(position)).getUrl();
        }

        @Override
        public Object getItem(int position) {
            return (list == null) ? null : list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int i, View view) {
            ImageView iv = null;
            if (view != null && view instanceof ImageView) {
                iv = (ImageView) view;
            } else {
                iv = new ImageView(context);
            }
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            iv.setBackgroundColor(context.getResources().getColor((android.R.color.transparent)));
            String url = getUrl(i);
           // Picasso.with(context).load(getUrl(i)).placeholder(new ColorDrawable(Color.parseColor("#f5f5f5"))).into(iv);

//            RequestOptions options = new RequestOptions();
//            options.centerCrop()
//                    .placeholder(new ColorDrawable(Color.parseColor("#f5f5f5")));
            Glide.with(context).load(getUrl(i)).into(iv);

            if (!TextUtils.isEmpty(url)) {
                iv.setTag(url);
            }
            return iv;
        }
    }
}