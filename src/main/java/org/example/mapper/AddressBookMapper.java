package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.pojo.AddressBook;

/**
 * ClassName: AddresBookMapper
 * Package: com.reggie.mapper
 * Description:
 *
 * @Autehor 屈子岩
 * @Create 2024/8/14 13:11
 * @Version 1.0
 */
@Mapper
public interface AddressBookMapper extends BaseMapper<AddressBook> {
}
