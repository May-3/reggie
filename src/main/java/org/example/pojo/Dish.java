package org.example.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * ClassName: Dish
 * Package: com.reggie.pojo
 * Description:
 *
 * @Autehor 屈子岩
 * @Create 2024/8/8 17:03
 * @Version 1.0
 */
@Data
public class Dish implements Serializable {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    private String name;

    private Long categoryId;

    private String price;

    private String code;

    private String description;

    private Integer status;
    private String image;

    private Integer sort;

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
