package org.example.pojo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * ClassName: OrderDetail
 * Package: com.reggie.pojo
 * Description:
 *
 * @Autehor 屈子岩
 * @Create 2024/8/15 16:59
 * @Version 1.0
 */
@Data
public class OrderDetail {
      private static final long serialVersionUID = 1L;

    private Long id;

    //名称
    private String name;

    //订单id
    private Long orderId;


    //菜品id
    private Long dishId;


    //套餐id
    private Long setmealId;


    //口味
    private String dishFlavor;


    //数量
    private Integer number;

    //金额
    private BigDecimal amount;

    //图片
    private String image;

}

