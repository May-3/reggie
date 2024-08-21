package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.common.BaseContext;
import org.example.mapper.ShoppingCartMapper;
import org.example.pojo.ShoppingCart;
import org.example.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * ClassName: ShoppingCartServiceImpl
 * Package: org.example.service.impl
 * Description:
 *
 * @Autehor 屈子岩
 * @Create 2024/8/19 18:12
 * @Version 1.0
 */
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Override
    public List<ShoppingCart> listShoppingCart() {
        LambdaQueryWrapper<ShoppingCart> shoppingCartLambdaQueryWrapper = new LambdaQueryWrapper<>();
        shoppingCartLambdaQueryWrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());
        shoppingCartMapper.selectList(shoppingCartLambdaQueryWrapper);
        return shoppingCartMapper.selectList(shoppingCartLambdaQueryWrapper);
    }

    @Override
    public ShoppingCart saveShoppingCart(ShoppingCart shoppingCart) {
        Long currentId = BaseContext.getCurrentId();
        shoppingCart.setUserId(currentId);
        LambdaQueryWrapper<ShoppingCart> shoppingCartLambdaQueryWrapper = new LambdaQueryWrapper<>();
        Long dishId = shoppingCart.getDishId();
        if (dishId != null){
            shoppingCartLambdaQueryWrapper.eq(ShoppingCart::getDishId,dishId);
        }else {
            shoppingCartLambdaQueryWrapper.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
        }

        ShoppingCart cartServiceOne = shoppingCartMapper.selectOne(shoppingCartLambdaQueryWrapper);
        if (cartServiceOne != null) {
            Integer number = cartServiceOne.getNumber();
            cartServiceOne.setNumber(number + 1);
            shoppingCartMapper.updateById(cartServiceOne);
        } else {
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartMapper.insert(shoppingCart);
            cartServiceOne = shoppingCart;
            cartServiceOne.setNumber(1);
        }
        return  cartServiceOne;
    }

    @Override
    public void clean() {
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,BaseContext.getCurrentId());
        shoppingCartMapper.delete(queryWrapper);
    }

    @Override
    public ShoppingCart subShoppingCart(ShoppingCart shoppingCart) {
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        Long dishId = shoppingCart.getDishId();
        if (dishId != null){
            queryWrapper.eq(ShoppingCart::getDishId,dishId);
        }else {
            queryWrapper.eq(ShoppingCart::getSetmealId,shoppingCart.getSetmealId());
        }
        ShoppingCart shoppingCartData = shoppingCartMapper.selectOne(queryWrapper);
        shoppingCartData.setNumber(shoppingCartData.getNumber() - 1);
        if (shoppingCartData.getNumber() > 0){
            shoppingCartMapper.updateById(shoppingCartData);
        }else if (shoppingCartData.getNumber() == 0){
            shoppingCartMapper.delete(queryWrapper);
        }
        return shoppingCartData;
    }

}
