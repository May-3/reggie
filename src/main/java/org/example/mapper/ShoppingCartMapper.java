package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.pojo.ShoppingCart;

/**
 * ClassName: ShoppingCartMapper
 * Package: org.example.mapper
 * Description:
 *
 * @Autehor 屈子岩
 * @Create 2024/8/19 18:11
 * @Version 1.0
 */
@Mapper
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart> {
}
