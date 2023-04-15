package com.high_con.grad.common;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

public  interface BaseService <E,ID extends Serializable> {
    BaseMapper<E> getRepository();

    default E get(ID id) {
        return getRepository().selectById(id);
    }

    default List<E> getAll() {
        return getRepository().selectList(null);
    }

    default E insert(E entity) {
        getRepository().insert(entity);
        return entity;
    }

    default E update(E entity) {
        getRepository().updateById(entity);
        return entity;
    }

    default E insertOrUpdate(E entity) {
        try {
            Object id = entity.getClass().getMethod("getId").invoke(entity);
            if (id == null) {
                insert(entity);
            } else {
                update(entity);
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return entity;
    }

    default int delete(ID id) {
        return getRepository().deleteById(id);
    }

    default void batchDelete(List<ID> ids) {
        getRepository().deleteBatchIds(ids);
    }



    QueryWrapper<E> getQueryWrapper(E e);

    QueryWrapper<E> getQueryWrapper(Map<String, Object> condition);

}
