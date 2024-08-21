package org.example.service;


import com.baomidou.mybatisplus.extension.service.IService;
import org.example.pojo.AddressBook;

import java.util.List;

/**
 * ClassName: AddresBookService
 * Package: com.reggie.service
 * Description:
 *
 * @Autehor 屈子岩
 * @Create 2024/8/14 13:10
 * @Version 1.0
 */
public interface AddressBookService extends IService<AddressBook> {
    List<AddressBook> listAddress();

    AddressBook setDefault(AddressBook addressBook);

    AddressBook receiveDefault();
}
