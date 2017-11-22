package com.xc.lovelife.event;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;

/**
 * Created by xum19 on 2017/11/22.
 */

public interface ImgService {

    @Streaming
    @GET("/")
    Observable<ResponseBody> downPic();
}
