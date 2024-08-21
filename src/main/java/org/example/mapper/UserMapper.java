package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.pojo.User;

/**
 * ClassName: UserMapper
 * Package: org.example.mapper
 * Description:
 *
 * @Autehor 屈子岩
 * @Create 2024/8/18 20:46
 * @Version 1.0
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
