package com.high_con.grad.redis;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Service
public class RedisService {

    @Autowired
    JedisPool jedisPool;





        //获取对象
    public <T> T get(KeyPre prefix,String key, Class<T> clazz) {

        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            String newKey = prefix.getPrefix() + key;
            String str = jedis.get(newKey);
            T t = stringToBean(str,clazz);
            return t;
        }finally {
            returnToPool(jedis);
        }
    }

    public <T> T mget(KeyPre prefix,String key, Class<T> clazz) {

        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            String newKey = prefix.getPrefix() + key;
            String str = jedis.get(newKey);
            T t = stringToBean(str,clazz);
            return t;
        }finally {
            returnToPool(jedis);
        }
    }


    public <T> boolean set(KeyPre prefix,String key, T value) {

        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            String str = beanToString(value);
            if(str==null||str.length()<=0){
                return false;
            }
            String newKey = prefix.getPrefix() + key;
            int time = prefix.expire_time();
            if(time<=0){
                jedis.set(newKey,str);
            }else{
                jedis.setex(newKey,time,str);
            }
            return true;
        }finally {
            returnToPool(jedis);
        }
    }


    public <T> boolean exist(KeyPre prefix,String key) {

        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            String newKey = prefix.getPrefix() + key;
            return jedis.exists(newKey);
        }finally {
            returnToPool(jedis);
        }
    }

    public <T> Long incre(KeyPre prefix,String key) {

        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            String newKey = prefix.getPrefix() + key;
            return jedis.incr(newKey);
        }finally {
            returnToPool(jedis);
        }
    }


    public <T> Long decre(KeyPre prefix,String key) {

        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            String newKey = prefix.getPrefix() + key;
            return jedis.decr(newKey);
        }finally {
            returnToPool(jedis);
        }
    }


    public  boolean del(KeyPre pre,String key) {
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            String newKey = pre.getPrefix() + key;
            long re =  jedis.del(newKey);
            return re > 0;
        }finally {
            returnToPool(jedis);
        }
    }



    public <T> T getList(String key, Class<T> clazz) {

        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            String str = jedis.get(key);
            T t = stringToBean(str,clazz);
            return t;
        }finally {
            returnToPool(jedis);
        }
    }

    public static <T> String beanToString(T value) {

        if (value == null) {
            return null;
        }
        Class<?> clazz = value.getClass();
        if (clazz == int.class || clazz == Integer.class) {
            return ""+value;
        } else if (clazz == String.class) {
            return (String) value;
        } else if (clazz == long.class || clazz == Long.class) {
            return ""+value;
        } else {
            return JSON.toJSONString(value);
        }
    }

    public static <T> T stringToBean(String str,Class<T> clazz) {
        if(str==null||str.length()<=0||clazz==null){
            return null;
        }
        if (clazz == int.class || clazz == Integer.class) {
            return (T)Integer.valueOf(str);
        } else if (clazz == String.class) {
            return (T)str;
        } else if (clazz == long.class || clazz == Long.class) {
            return (T)Long.valueOf(str);
        } else {
            return JSON.toJavaObject(JSON.parseObject(str),clazz);
        }

    }

    private void returnToPool(Jedis jedis) {
        if(jedis!=null){
            jedis.close();
        }
    }



}
