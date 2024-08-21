package org.example.dto;
 


import lombok.Data;
import org.example.pojo.OrderDetail;
import org.example.pojo.Orders;

import java.util.List;


/**
 * @author LJM
 * @create 2022/5/3
 */
@Data
public class OrderDto extends Orders {
 
    private List<OrderDetail> orderDetails;
}