package org.example.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.example.pojo.Orders;

/**
 * ClassName: OrderService
 * Package: org.example.service
 * Description:
 *
 * @Autehor 屈子岩
 * @Create 2024/8/18 19:26
 * @Version 1.0
 */
public interface OrderService extends IService<Orders> {
    Page page(Integer page, Integer pageSize, String number, String beginTime, String endTime);

    void status(Orders orders);

    void submit(Orders orders);

    Page userPage(Integer page, Integer pageSize);

    void again(Orders orders);
}
