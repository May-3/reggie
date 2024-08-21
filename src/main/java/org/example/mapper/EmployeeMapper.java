package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.pojo.Employee;

/**
 * ClassName: EmployeeMapper
 * Package: org.example.mapper
 * Description:
 *
 * @Autehor 屈子岩
 * @Create 2024/8/17 13:57
 * @Version 1.0
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
