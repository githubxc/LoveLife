package com.xc.lovelife.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.xc.lovelife.bean.Image;

import javax.xml.transform.dom.DOMLocator;

/**
 * Created by xum19 on 2017/12/21.
 */

public class GlideUtil {

    public static void load(Context context, String url, ImageView imageView, RequestOptions options){

        Glide.with(context).load(url).thumbnail(0.1f).apply(options).into(imageView);

    }
}
