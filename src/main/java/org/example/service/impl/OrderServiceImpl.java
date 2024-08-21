package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.common.BaseContext;
import org.example.common.Result;
import org.example.dto.OrderDto;
import org.example.mapper.OrderMapper;
import org.example.pojo.*;
import org.example.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * ClassName: OrderServiceImpl
 * Package: org.example.service.impl
 * Description:
 *
 * @Autehor 屈子岩
 * @Create 2024/8/18 19:27
 * @Version 1.0
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Orders> implements OrderService {
    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private UserService userService;

    @Autowired
    private AddressBookService addressBookService;

    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public Page page(Integer page, Integer pageSize, String number, String beginTime, String endTime) {
        Page<Orders> Page = new Page<>(page, pageSize);
        LambdaQueryWrapper<Orders> ordersLambdaQueryWrapper = new LambdaQueryWrapper<>();
        ordersLambdaQueryWrapper.eq(number != null,Orders::getNumber,number)
                .gt(beginTime != null,Orders::getOrderTime,beginTime) //大于
                .lt(endTime != null,Orders::getOrderTime,endTime);  //小于
        orderMapper.selectPage(Page,ordersLambdaQueryWrapper);
        return Page;
    }
    @Override
    public void status(Orders orders) {
        LambdaQueryWrapper<Orders> ordersLambdaQueryWrapper = new LambdaQueryWrapper<>();
        ordersLambdaQueryWrapper.eq(Orders::getNumber,orders.getId());
        List<Orders> order = orderMapper.selectList(ordersLambdaQueryWrapper);
        order.stream().map((item)->{
            item.setStatus(orders.getStatus());
            return item;
        }).collect(Collectors.toList());
        orderMapper.updateById(orders);
    }

    /**
     * 用户下单
     *
     * @param orders
     */
    @Transactional
    public void submit(Orders orders) {
        //获得当前用户id
        Long userId = BaseContext.getCurrentId();

        //查询当前用户的购物车数据
        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShoppingCart::getUserId, userId);
        List<ShoppingCart> shoppingCarts = shoppingCartService.list(wrapper);

        if (shoppingCarts == null || shoppingCarts.size() == 0) {
            throw new RuntimeException("购物车为空，不能下单");
        }

        //查询用户数据
        User user = userService.getById(userId);

        //查询地址数据
        Long addressBookId = orders.getAddressBookId();
        AddressBook addressBook = addressBookService.getById(addressBookId);
        if (addressBook == null) {
            throw new RuntimeException("用户地址信息有误，不能下单");
        }

        long orderId = IdWorker.getId();//订单号

        AtomicInteger amount = new AtomicInteger(0);

        List<OrderDetail> orderDetails = shoppingCarts.stream().map((item) -> {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(orderId);
            orderDetail.setNumber(item.getNumber());
            orderDetail.setDishFlavor(item.getDishFlavor());
            orderDetail.setDishId(item.getDishId());
            orderDetail.setSetmealId(item.getSetmealId());
            orderDetail.setName(item.getName());
            orderDetail.setImage(item.getImage());
            orderDetail.setAmount(item.getAmount());
            amount.addAndGet(item.getAmount().multiply(new BigDecimal(item.getNumber())).intValue());
            return orderDetail;
        }).collect(Collectors.toList());
        orders.setId(orderId);
        orders.setOrderTime(LocalDateTime.now());
        orders.setCheckoutTime(LocalDateTime.now());
        orders.setStatus(2);
        orders.setAmount(new BigDecimal(amount.get()));//总金额
        orders.setUserId(userId);
        orders.setNumber(String.valueOf(orderId));
        orders.setUserName(user.getName());
        orders.setConsignee(addressBook.getConsignee());
        orders.setPhone(addressBook.getPhone());
        orders.setAddress((addressBook.getProvinceName() == null ? "" : addressBook.getProvinceName())
                + (addressBook.getCityName() == null ? "" : addressBook.getCityName())
                + (addressBook.getDistrictName() == null ? "" : addressBook.getDistrictName())
                + (addressBook.getDetail() == null ? "" : addressBook.getDetail()));
        //向订单表插入数据，一条数据
        this.save(orders);

        //向订单明细表插入数据，多条数据
        orderDetailService.saveBatch(orderDetails);

        //清空购物车数据
        shoppingCartService.remove(wrapper);
    }

    @Override
    public Page userPage(Integer page, Integer pageSize) {
        Page<Orders> orderDetailPage = new Page<>(page,pageSize);
        Page<OrderDto> orderDtoPage = new Page<>();
        LambdaQueryWrapper<Orders> orderDetailLambdaQueryWrapper = new LambdaQueryWrapper<>();
        orderDetailLambdaQueryWrapper.eq(Orders::getUserId,BaseContext.getCurrentId());
        orderDetailLambdaQueryWrapper.orderByDesc(Orders::getOrderTime);
        orderMapper.selectPage(orderDetailPage,orderDetailLambdaQueryWrapper);

        List<Orders> records = orderDetailPage.getRecords();
        List<OrderDto> orderDtoList = records.stream().map((item)->{
            OrderDto orderDto = new OrderDto();
            //此时的orderDto对象里面orderDetails属性还是空 下面准备为它赋值
            Long orderId = item.getId();//获取订单id
            List<OrderDetail> orderDetailList = this.getOrderDetailListByOrderId(orderId);
            BeanUtils.copyProperties(item, orderDto);
            //对orderDto进行OrderDetails属性的赋值
            orderDto.setOrderDetails(orderDetailList);
            return orderDto;
        }).collect(Collectors.toList());
        BeanUtils.copyProperties(orderDetailPage,orderDtoPage,"records");
        orderDtoPage.setRecords(orderDtoList);
        return orderDtoPage;


    }

    @Override
    public void again(Orders orders) {
        Long id = orders.getId();
        LambdaQueryWrapper<OrderDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OrderDetail::getOrderId,id);
        List<OrderDetail> orderDetailList = orderDetailService.list(queryWrapper);
        Long currentId = BaseContext.getCurrentId();
        List<ShoppingCart> shoppingCartList = orderDetailList.stream().map((item) -> {
            ShoppingCart shoppingCart = new ShoppingCart();
            BeanUtils.copyProperties(item, shoppingCart, "id");
            shoppingCart.setUserId(currentId);
            shoppingCart.setCreateTime(LocalDateTime.now());
            return shoppingCart;
        }).collect(Collectors.toList());
        shoppingCartService.saveBatch(shoppingCartList);
    }

    public List<OrderDetail> getOrderDetailListByOrderId(Long orderId) {
        LambdaQueryWrapper<OrderDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OrderDetail::getOrderId, orderId);
        List<OrderDetail> orderDetailList = orderDetailService.list(queryWrapper);
        return orderDetailList;
    }
}
