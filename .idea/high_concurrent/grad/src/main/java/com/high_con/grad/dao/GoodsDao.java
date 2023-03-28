package com.high_con.grad.dao;


import com.high_con.grad.entity.Goods;
import com.high_con.grad.entity.Kill_Goods;
import com.high_con.grad.vo.GoodsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface GoodsDao {

    @Select("select g.*,kg.stock_count, kg.start_date, kg.end_date,kg.kill_price from kill_goods kg left join goods g on kg.goods_id = g.id")
    public List<GoodsVo> listGoodsVo();


    @Select("select g.*,kg.stock_count, kg.start_date, kg.end_date,kg.kill_price from kill_goods kg left join goods g on kg.goods_id = g.id where g.id = #{goodsId} ")
    public GoodsVo getGoodsVoByGoodsId(@Param("goodsId")long goodsId);

    @Update("update kill_goods set stock_count = stock_count - 1 where goods_id = #{goodsId} and stock_count > 0")
    public int reduceStock(Kill_Goods goods);



    //@Update("update miaosha_goods set stock_count = stock_count - 1 where goods_id = #{goodsId}")
    //public int reduceStock(MiaoshaGoods g);

}