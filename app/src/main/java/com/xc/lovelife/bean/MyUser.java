package com.xc.lovelife.bean;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by xum19 on 2017/12/22.
 */

public class MyUser extends BmobUser {

    private BmobFile userPic;

    public BmobFile getUserPic() {
        return userPic;
    }

    public void setUserPic(BmobFile userPic) {
        this.userPic = userPic;
    }
}
