package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.example.common.Result;
import org.example.mapper.EmployeeMapper;
import org.example.pojo.Employee;
import org.example.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

/**
 * ClassName: EmployeeServiceImpl
 * Package: org.example.service.impl
 * Description:
 *
 * @Autehor 屈子岩
 * @Create 2024/8/17 13:58
 * @Version 1.0
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;
    @Override
    public Result<Employee> login(Employee employee) {
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeMapper.selectOne(queryWrapper);
        if (emp == null) {
            return Result.error("用户名不存在"); //用户名不存在
        }
        if (!emp.getPassword().equals(password)){
            return Result.error("密码错误");
        }
        if (emp.getStatus() == 0){
            return Result.error("账号已禁用");
        }

    return Result.success(emp);
    }

    @Override
    public Page page(Integer page, Integer pageSize, String name) {

        Page<Employee> pageInfo = new Page<>(page,pageSize);
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(name != null, Employee::getName, name);
        employeeMapper.selectPage(pageInfo, queryWrapper);
        return pageInfo;
    }

}
