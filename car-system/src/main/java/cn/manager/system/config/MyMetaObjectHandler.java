package cn.manager.system.config;

import cn.manager.system.utils.SecurityUtils;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * MP自动填充
 *
 * @author ljc
 * @description ok
 * @version 1.0.0
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    /**
     * 创建时间与更新时间同时插入
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        Date nowDate = new Date();
        this.strictInsertFill(metaObject, "createTime", Date.class, nowDate);
        this.strictInsertFill(metaObject, "createDay", Date.class, nowDate);
        this.strictInsertFill(metaObject, "createBy", String.class, SecurityUtils.getUserNickname());

        this.strictInsertFill(metaObject, "updateTime", Date.class, nowDate);
        this.strictInsertFill(metaObject, "updateBy", String.class, SecurityUtils.getUserNickname());

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "updateTime", Date.class,new Date());
        this.strictUpdateFill(metaObject, "updateBy", String.class, SecurityUtils.getUserNickname());
        this.strictUpdateFill(metaObject, "updateById", Long.class, SecurityUtils.getUserId());
    }
}