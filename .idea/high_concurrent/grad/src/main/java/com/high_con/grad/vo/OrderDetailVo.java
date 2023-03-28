package com.high_con.grad.vo;

import com.high_con.grad.entity.Order;

public class OrderDetailVo {
    private GoodsVo goodsVo;
    private Order order;

    public GoodsVo getGoodsVo() {
        return goodsVo;
    }

    public void setGoodsVo(GoodsVo goodsVo) {
        this.goodsVo = goodsVo;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
