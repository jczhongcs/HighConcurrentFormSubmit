package com.high_con.grad.vo;

import com.high_con.grad.entity.Goods;

import java.util.Date;

public class GoodsVo extends Goods {

    private Double killPrice;
    private Integer stockCount;
    private Date startDate;
    private Date endDate;
    public Integer getStockCount() {
        return stockCount;
    }
    public void setStockCount(Integer stockCount) {
        this.stockCount = stockCount;
    }
    public Date getStartDate() {
        return startDate;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Double getKillPrice() {
        return killPrice;
    }

    public void setKillPrice(Double killPrice) {
        this.killPrice = killPrice;
    }
}
