package org.example.dto;


import lombok.Data;
import org.example.pojo.Setmeal;
import org.example.pojo.SetmealDish;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: SetmealDto
 * Package: com.reggie.dto
 * Description:
 *
 * @Autehor 屈子岩
 * @Create 2024/8/13 10:17
 * @Version 1.0
 */
@Data
public class SetmealDto extends Setmeal {

    // 菜肴列表，用于存储套餐中包含的所有菜肴
    private List<SetmealDish> setmealDishes = new ArrayList<>();

    // 分类名称，用于标识套餐所属的类别
    private String categoryName;

}
