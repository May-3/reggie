package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.example.common.CustomException;
import org.example.common.MailUtils;
import org.example.common.Result;
import org.example.mapper.UserMapper;
import org.example.pojo.User;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * ClassName: UserServiceImpl
 * Package: org.example.service.impl
 * Description:
 *
 * @Autehor 屈子岩
 * @Create 2024/8/18 20:47
 * @Version 1.0
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public User login(Map map, HttpSession session) {
        String code = map.get("code").toString();
        String phone = map.get("phone").toString();
        String CodeInSession = session.getAttribute(phone).toString();
        if (CodeInSession != null && CodeInSession.equals(code)){
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone, phone);
            User user = userMapper.selectOne(queryWrapper);

            if (user == null) {
                user = new User();
                user.setPhone(phone);
                user.setStatus(1);
                userMapper.insert(user);
            }
            session.setAttribute("user", user.getId());
            return user;
        }
        throw new CustomException("验证码错误");
    }

    @Override
    public void sendMsg(User user, HttpSession session) {
        if (!user.getPhone().isEmpty()) {
            String code = MailUtils.achieveCode();
            log.info("code: " + code);
            // MailUtils.sendTestMail(phone, code);
            session.setAttribute(user.getPhone(), code);
        }else {
            throw new CustomException("请输入邮箱号");
        }

    }
}
