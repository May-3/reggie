package org.example.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import java.time.LocalDateTime;

/**
 * ClassName: MyMetaObjectHandler
 * Package: org.example.common
 * Description:
 *
 * @Autehor 屈子岩
 * @Create 2024/8/17 14:42
 * @Version 1.0
 */
@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("公共字段自动填充[insert]");
        log.info(metaObject.toString());
        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("createUser", BaseContext.getCurrentId());
        metaObject.setValue("updateUser", BaseContext.getCurrentId());

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("公共字段自动填充[update]");
        log.info(metaObject.toString());
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("updateUser", BaseContext.getCurrentId());

    }
}
