package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.common.BaseContext;
import org.example.common.Result;
import org.example.pojo.AddressBook;
import org.example.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ClassName: AddressBookController
 * Package: org.example.controller
 * Description:
 *
 * @Autehor 屈子岩
 * @Create 2024/8/19 20:08
 * @Version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/addressBook")
public class AddressBookController {
    @Autowired
    private AddressBookService addressBookService;
    @GetMapping("list")
    public Result<List<AddressBook>> list() {
        List<AddressBook> addressBookList= addressBookService.listAddress();
        return Result.success(addressBookList);
    }

    @PostMapping
    public Result<AddressBook> save(@RequestBody AddressBook addressBook) {
        addressBook.setUserId(BaseContext.getCurrentId());
        log.info("addressBook:{}", addressBook);
        addressBookService.save(addressBook);
        return Result.success(addressBook);
    }
    @PutMapping("default")
    public Result<AddressBook> setDefault(@RequestBody AddressBook addressBook) {
        log.info("addressBook:{}", addressBook);
        addressBookService.setDefault(addressBook);
        return Result.success(addressBook);
    }
    @GetMapping("{id}")
    public Result<AddressBook> getById(@PathVariable Long id){
        log.info("id:{}",id);
        AddressBook addressBook = addressBookService.getById(id);
        if (addressBook != null) {
            return Result.success(addressBook);
        } else {
            return Result.error("没有找到该对象");
        }
    }
    @DeleteMapping
    public Result<String> delete(@RequestParam List<Long> ids) {
        log.info("ids:{}", ids);
        addressBookService.removeByIds(ids);
        return Result.success("删除成功");
    }
    @PutMapping
    public Result<AddressBook> update(@RequestBody AddressBook addressBook) {
        log.info("addressBook:{}", addressBook);
        addressBookService.updateById(addressBook);
        return Result.success(addressBook);
    }
    @GetMapping("default")
    public Result<AddressBook> getDefault() {
        AddressBook addressBook = addressBookService.receiveDefault();
        if (addressBook != null) {
            return Result.success(addressBook);
        } else {
            return Result.error("没有找到默认地址");
        }
    }

}

//2450598374@qq.com