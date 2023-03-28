package com.high_con.grad.vo;

import com.high_con.grad.entity.User;

public class ArticleDetailVo {


    private int Status = 0;
    private int remain = 0;
    private GoodsVo goodsVo;

    private User user;

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }





    public int getRemain() {
        return remain;
    }

    public void setRemain(int remain) {
        this.remain = remain;
    }

    public GoodsVo getGoodsVo() {
        return goodsVo;
    }

    public void setGoodsVo(GoodsVo goodsVo) {
        this.goodsVo = goodsVo;
    }
}
