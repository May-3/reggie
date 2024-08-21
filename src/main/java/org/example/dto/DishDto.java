package org.example.dto;


import lombok.Data;
import org.example.pojo.Dish;
import org.example.pojo.DishFlavor;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: DishDto
 * Package: com.reggie.dto
 * Description:
 *
 * @Autehor 屈子岩
 * @Create 2024/8/12 16:26
 * @Version 1.0
 */
@Data
public class DishDto extends Dish {
    private List<DishFlavor> flavors =new ArrayList<>();
    private String categoryName;
    private Integer copies;

}
