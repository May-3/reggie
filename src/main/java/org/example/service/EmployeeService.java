package org.example.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletRequest;
import org.example.common.Result;
import org.example.pojo.Employee;

/**
 * ClassName: EmployeeService
 * Package: org.example.service
 * Description:
 *
 * @Autehor 屈子岩
 * @Create 2024/8/17 13:57
 * @Version 1.0
 */
public interface EmployeeService extends IService<Employee> {
    Result<Employee> login(Employee employee);
    Page page(Integer page, Integer pageSize, String name);
}
