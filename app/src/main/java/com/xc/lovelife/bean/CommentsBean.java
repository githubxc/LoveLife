package com.xc.lovelife.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;

/**
 * Created by xum19 on 2017/11/28.
 */

public class CommentsBean extends BmobObject {
    private Integer commentsId;
    private String content;
    private BmobUser replyUser; // 回复人信息
    private BmobUser commentsUser;  // 评论人信息

    public Integer getCommentsId() {
        return commentsId;
    }

    public void setCommentsId(Integer commentsId) {
        this.commentsId = commentsId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public BmobUser getReplyUser() {
        return replyUser;
    }

    public void setReplyUser(BmobUser replyUser) {
        this.replyUser = replyUser;
    }

    public BmobUser getCommentsUser() {
        return commentsUser;
    }

    public void setCommentsUser(BmobUser commentsUser) {
        this.commentsUser = commentsUser;
    }
}