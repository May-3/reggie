package org.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpSession;
import org.example.pojo.User;

import java.util.Map;

/**
 * ClassName: UserService
 * Package: org.example.service
 * Description:
 *
 * @Autehor 屈子岩
 * @Create 2024/8/18 20:47
 * @Version 1.0
 */
public interface UserService extends IService<User> {


    User login(Map map, HttpSession session);

    void sendMsg(User user, HttpSession session);
}
