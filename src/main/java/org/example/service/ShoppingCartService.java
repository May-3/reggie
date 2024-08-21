package org.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.pojo.ShoppingCart;

import java.util.List;

/**
 * ClassName: ShoppingCartService
 * Package: org.example.service
 * Description:
 *
 * @Autehor 屈子岩
 * @Create 2024/8/19 18:12
 * @Version 1.0
 */
public interface ShoppingCartService extends IService<ShoppingCart> {

    List<ShoppingCart> listShoppingCart();

    ShoppingCart saveShoppingCart(ShoppingCart shoppingCart);

    void clean();

    ShoppingCart subShoppingCart(ShoppingCart shoppingCart);
}
