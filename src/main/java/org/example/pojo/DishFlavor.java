package org.example.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * ClassName: DishFlavor
 * Package: com.reggie.pojo
 * Description:
 *
 * @Autehor 屈子岩
 * @Create 2024/8/12 11:34
 * @Version 1.0
 */
@Data
public class DishFlavor  implements Serializable {
    private Long id;
    private Long dishId;
    private String name;
    private String value;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    //这两个先不用管，后面再说
    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;
    private Integer isDeleted;
}
