package org.example.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.example.common.Result;
import org.example.pojo.Employee;
import org.example.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

/**
 * ClassName: EmployeeController
 * Package: org.example.controller
 * Description: 员工管理
 *
 * @Autehor 屈子岩
 * @Create 2024/8/17 13:59
 * @Version 1.0
 */
@RestController
@Slf4j
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

        @PostMapping("login")
    public Result<Employee> login(@RequestBody Employee employee, HttpServletRequest request) {
        log.info("登录成功");
        Result<Employee> login = employeeService.login(employee);
        Long id = login.getData().getId();
        request.getSession().setAttribute("employee", id);
        return Result.success(login.getData());
    }


    @GetMapping("page")
    public Result<Page> page(Integer page, Integer pageSize, String name) {
        log.info("page:{},pageSize:{},name:{}", page, pageSize, name);
        Page page1 = employeeService.page(page, pageSize, name);
        return Result.success(page1);
    }

    @PostMapping
    public Result<String> save(@RequestBody Employee employee) {
        log.info("新增员工，员工信息：{}", employee.toString());
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        employeeService.save(employee);
        return Result.success("新增员工成功");
    }

    @GetMapping("{id}")
    public Result<Employee> getById(@PathVariable Long id) {
        log.info("根据id查询员工信息");
        Employee employee = employeeService.getById(id);
        return Result.success(employee);
    }

    @PutMapping
    public Result<String> update(@RequestBody Employee employee){
            log.info("修改员工信息");
            employeeService.updateById(employee);
            return Result.success("修改员工信息成功");
    }
    @PostMapping("logout")
    public Result<String> logout(HttpServletRequest request){
        log.info("退出成功");
        request.getSession().removeAttribute("employee");
        return Result.success("退出成功");
    }
}