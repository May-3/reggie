package org.example.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.example.common.Result;
import org.example.pojo.User;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * ClassName: UserController
 * Package: org.example.controller
 * Description:
 *
 * @Autehor 屈子岩
 * @Create 2024/8/18 20:46
 * @Version 1.0
 */
@RestController
@RequestMapping("user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("sendMsg")
    public Result<String> sendMsg(@RequestBody User user, HttpSession session) {

        userService.sendMsg(user, session);
        return Result.success("发送成功");

    }

    @PostMapping("/login")
    public Result<User> login(@RequestBody Map map, HttpSession session) {
            User user = userService.login(map,session);
            return Result.success(user);
    }
    /**
     * 退出功能
     * ①在controller中创建对应的处理方法来接受前端的请求，请求方式为post；
     * ②清理session中的用户id
     * ③返回结果（前端页面会进行跳转到登录页面）
     * @return
     */
    @PostMapping("/loginout")
    public Result<String> logout(HttpServletRequest request){
        //清理session中的用户id
        request.getSession().removeAttribute("user");
        return Result.success("退出成功");
    }
}
