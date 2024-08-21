package org.example.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.example.common.Result;
import org.example.pojo.Orders;
import org.example.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * ClassName: OrderController
 * Package: org.example.controller
 * Description:订单管理
 *
 * @Autehor 屈子岩
 * @Create 2024/8/18 19:25
 * @Version 1.0
 */
@RestController
@RequestMapping("order")
@Slf4j
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("page")
    public Result<Page> page(Integer page,Integer pageSize, String number, String beginTime, String endTime){
        Page page1 = orderService.page(page, pageSize, number, beginTime, endTime);
        return Result.success(page1);
    }
    @PutMapping
    public Result<String> order(@RequestBody Orders orders){

        orderService.status(orders);
        return Result.success("派送成功");
    }
    @PostMapping("submit")
    public Result<String> submit(@RequestBody Orders orders) {
        log.info("订单数据:{}", orders);
        orderService.submit(orders);
        return Result.success("下单成功");
    }
    @GetMapping("userPage")
    public Result<Page> userPage( Integer page,Integer pageSize){
        Page page1 = orderService.userPage(page, pageSize);
        return Result.success(page1);
    }
    @PostMapping("again")
    public Result<String> again(@RequestBody Orders orders){
        orderService.again(orders);
        return Result.success("下单成功");
    }
}//2450598374@qq.com
