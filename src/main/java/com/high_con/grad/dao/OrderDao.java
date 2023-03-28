package com.high_con.grad.dao;

import com.high_con.grad.entity.Kill_Order;
import com.high_con.grad.entity.Order;
import org.apache.ibatis.annotations.*;

@Mapper
public interface OrderDao {
    @Select("select * from kill_order where user_id=#{userId} and goods_id=#{goodsId}")
    public Kill_Order getKillOrderByUserIdGoodsId(@Param("userId")long userId,@Param("goodsId")long goodsId);

    @Insert("insert into order_info(user_id,goods_id,goods_name,goods_count,goods_price,order_channel,status,create_date)values(" +
            "#{userId},#{goodsId},#{goodsName},#{goodsCount},#{goodsPrice},#{orderChannel},#{status},#{createDate})")
    @SelectKey(keyColumn = "id",keyProperty = "id",resultType = long.class,before = false,statement = "select last_insert_id()")
    public long insert(Order order);

    @Insert("insert into kill_order(user_id,goods_id,order_id)values(#{userId},#{goodsId},#{orderId})")
    public int insertKillOrder(Kill_Order kill_order);

    @Select("select * from order_info where id = #{orderId}")
    Order getOrderById(@Param("orderId")long orderId);
}
