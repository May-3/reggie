package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.common.Result;
import org.example.pojo.ShoppingCart;
import org.example.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ClassName: ShoppingCartController
 * Package: org.example.controller
 * Description:
 *
 * @Autehor 屈子岩
 * @Create 2024/8/19 18:11
 * @Version 1.0
 */
@RestController
@Slf4j
@RequestMapping("shoppingCart")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    @GetMapping("list")
    public Result<List<ShoppingCart>> list(){
        List<ShoppingCart> shoppingCarts = shoppingCartService.listShoppingCart();
        return Result.success(shoppingCarts);
    }
    @PostMapping("add")
    public Result<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart){
        ShoppingCart cart = shoppingCartService.saveShoppingCart(shoppingCart);
        return Result.success(cart);
    }
    @DeleteMapping("clean")
    public Result<String> clean(){
        shoppingCartService.clean();
        return Result.success("清空购物车成功");
    }
    @PostMapping("sub")
    public Result<ShoppingCart> sub(@RequestBody ShoppingCart shoppingCart){
        ShoppingCart cart = shoppingCartService.subShoppingCart(shoppingCart);
        return Result.success(cart);
    }
}
//2450598374@qq.com
