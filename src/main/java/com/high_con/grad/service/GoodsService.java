package com.high_con.grad.service;

import com.high_con.grad.dao.GoodsDao;
import com.high_con.grad.entity.Goods;
import com.high_con.grad.entity.Kill_Goods;
import com.high_con.grad.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GoodsService {
    @Autowired
    GoodsDao goodsDao;

    public List<GoodsVo> listGoodsVo(){
        return goodsDao.listGoodsVo();
    }

    public GoodsVo getGoodsVoByGoodsId(long goodsId) {
        return goodsDao.getGoodsVoByGoodsId(goodsId);
    }

   // @Transactional
    public boolean reduceStock(GoodsVo goodsVo) {
        Kill_Goods goods = new Kill_Goods();
        goods.setGoodsId(goodsVo.getId());
        //System.out.println("Set good is ok");
        int ret = goodsDao.reduceStock(goods);
        return ret > 0 ;
    }
}
