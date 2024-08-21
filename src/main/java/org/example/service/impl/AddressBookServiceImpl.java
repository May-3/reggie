package org.example.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.common.BaseContext;
import org.example.mapper.AddressBookMapper;
import org.example.pojo.AddressBook;
import org.example.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName: AddresBookServiceImpl
 * Package: com.reggie.service.impl
 * Description:
 *
 * @Autehor 屈子岩
 * @Create 2024/8/14 13:10
 * @Version 1.0
 */
@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {
    @Autowired
    protected AddressBookMapper addressBookMapper;
    @Override
    public List<AddressBook> listAddress() {
        LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AddressBook::getUserId, BaseContext.getCurrentId());
        List<AddressBook> addressBookList = addressBookMapper.selectList(queryWrapper);
        return addressBookList;
    }

    @Override
    public AddressBook setDefault(AddressBook addressBook) {
        LambdaUpdateWrapper<AddressBook> queryWrapper = new LambdaUpdateWrapper<>();
        queryWrapper.eq(AddressBook::getUserId, BaseContext.getCurrentId());
        queryWrapper.set(AddressBook::getIsDefault,0);
        addressBookMapper.update(queryWrapper);
        addressBook.setIsDefault(1);
        addressBookMapper.updateById(addressBook);
        return addressBook ;
    }

    @Override
    public AddressBook receiveDefault() {
        LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AddressBook::getIsDefault, 1);
        AddressBook addressBook = addressBookMapper.selectOne(queryWrapper);
        return addressBook;
    }
}
