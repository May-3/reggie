package org.example.pojo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * ClassName: Orders
 * Package: com.reggie.pojo
 * Description:
 *
 * @Autehor 屈子岩
 * @Create 2024/8/13 15:44
 * @Version 1.0
 */
@Data
public class Orders implements Serializable {



        // 订单号，唯一标识一个订单
        private String number;

        // 订单ID，可能用于内部系统中唯一标识订单
        private Long id;

        // 用户ID，标识下此订单的用户
        private Long userId;

        // 地址簿ID，用户选择的收货地址在地址簿中的ID
        private Long addressBookId;

        // 下单时间，记录订单创建的时间
        private LocalDateTime orderTime;

        // 支付时间，记录用户完成支付的时间
        private LocalDateTime checkoutTime;

        // 支付方式，描述用户选择的支付手段，如支付宝、微信等
        private String payMethod;

        // 订单金额，此订单应付的总金额
        private BigDecimal amount;

        // 订单备注，用户对订单的备注信息
        private String remark;

        // 用户电话，用于收货时的联系
        private String phone;

        // 用户地址，收货地址
        private String address;

        // 用户名，下订单的用户名称
        private String userName;

        // 收货人，实际接收货物的人员名称
        private String consignee;

        // 订单状态，可能的取值包括：0-未支付，1-已支付，2-已发货，3-已完成等
        private Integer status;


    }

