package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.pojo.Orders;

/**
 * ClassName: OrderMapper
 * Package: org.example.mapper
 * Description:
 *
 * @Autehor 屈子岩
 * @Create 2024/8/18 19:26
 * @Version 1.0
 */
@Mapper
public interface OrderMapper extends BaseMapper<Orders> {
}
