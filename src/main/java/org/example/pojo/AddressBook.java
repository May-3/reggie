package org.example.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * ClassName: AddresBook
 * Package: com.reggie.pojo
 * Description:
 *
 * @Autehor 屈子岩
 * @Create 2024/8/14 13:03
 * @Version 1.0
 */
@Data
public class AddressBook implements Serializable {
    private Long id;
    private Long userId;
    private String consignee;
    private Integer sex;//性别 0 女 1 男
    private String phone;

    private String provinceCode;

    private String provinceName;
    private String cityCode;
    private String cityName;
    private String districtCode;
    private String districtName;
    private String detail;
    private String label;
    private Integer isDefault;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;
    private Integer isDeleted;


}
