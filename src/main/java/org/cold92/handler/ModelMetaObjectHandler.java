package org.cold92.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 默认填充器
 * 注意：自动填充字段不能设置not null
 */
@Component
public class ModelMetaObjectHandler implements MetaObjectHandler {

    // 日志
    private static final Logger LOGGER= LoggerFactory.getLogger(ModelMetaObjectHandler.class);

    /**
     * 新增时填充
     * metaObject是页面传递过来的参数的包装对象，不是从数据库取的持久化对象，
     * 因此页面传过来哪些值，metaObject里就有哪些值
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        // 先判断是否存在该字段, 提高效率
        boolean createTime = metaObject.hasSetter("createTime");
        boolean role = metaObject.hasSetter("role");
        LOGGER.info("insert 自动填充 "+ LocalDateTime.now());
        if (createTime && role) {
            // 第一个参数对应实体属性名, 第二个参数需要填充的值
            setInsertFieldValByName("role", "user", metaObject);
            setInsertFieldValByName("createTime", new Date(), metaObject);
            setInsertFieldValByName("updateTime", new Date(), metaObject);
        }
    }

    /**
     * 修改时填充
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        boolean updateTime = metaObject.hasSetter("updateTime");
        LOGGER.info("update 自动填充 "+ LocalDateTime.now());
        if (updateTime) {
            setUpdateFieldValByName("updateTime", new Date(), metaObject);
        }
    }
}