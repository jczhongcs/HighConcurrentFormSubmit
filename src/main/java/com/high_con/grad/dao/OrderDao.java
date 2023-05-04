package com.high_con.grad.dao;

import com.high_con.grad.entity.Order;
import org.apache.ibatis.annotations.*;

@Mapper
public interface OrderDao {

    @Insert("insert into order_info(user_id,goods_id,goods_name,goods_count,goods_price,order_channel,status,create_date)values(" +
            "#{userId},#{goodsId},#{goodsName},#{goodsCount},#{goodsPrice},#{orderChannel},#{status},#{createDate})")
    @SelectKey(keyColumn = "id",keyProperty = "id",resultType = long.class,before = false,statement = "select last_insert_id()")
    public long insert(Order order);



    @Select("select * from order_info where id = #{orderId}")
    Order getOrderById(@Param("orderId")long orderId);
}
