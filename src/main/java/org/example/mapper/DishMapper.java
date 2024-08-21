package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.pojo.Dish;

/**
 * ClassName: DishMapper
 * Package: org.example.mapper
 * Description:
 *
 * @Autehor 屈子岩
 * @Create 2024/8/17 17:13
 * @Version 1.0
 */
@Mapper
public interface DishMapper extends BaseMapper<Dish> {
}
