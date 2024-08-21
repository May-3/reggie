package org.example.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * ClassName: Category
 * Package: com.reggie.pojo
 * Description:
 *
 * @Autehor 屈子岩
 * @Create 2024/8/8 8:56
 * @Version 1.0
 */
@Data
public class Category implements Serializable {
    private Long id;

    private Integer type;

    private String name;

    private Integer sort;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;
}
