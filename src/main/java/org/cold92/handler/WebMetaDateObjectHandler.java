package org.cold92.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 给数据库表中create_time，update_time自动赋值
 * 对数据进行操作之前调用
 */
@Component
public class WebMetaDateObjectHandler implements MetaObjectHandler {

    /**
     * 插入数据时，create_time和update_time都要更新
     * @param metaObject 就是添加或者更新的目标对象
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        // 将metaObject中的createTime属性赋值为new Date()
        this.setFieldValByName("createTime", new Date(), metaObject);
        this.setFieldValByName("updateTime", new Date(), metaObject);
    }

    /**
     * 更新数据时，只需要更新update_time
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", new Date(), metaObject);
    }
}
