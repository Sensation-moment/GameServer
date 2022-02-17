package com.fattyCorps.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 持久层日期格式管理
 */
@Component
public class DaoTimeHandler implements MetaObjectHandler {
    /**
     * 日期格式
     */
    private String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    /**
     * 插入填充
     *
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        try {
            SimpleDateFormat df = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
            String strDate = df.format(new Date());
            setFieldValByName("createTime", strDate, metaObject);
            setFieldValByName("updateTime", strDate, metaObject);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新填充
     *
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        try {
            SimpleDateFormat df = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
            String strDate = df.format(new Date());
            setFieldValByName("updateTime", strDate, metaObject);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
