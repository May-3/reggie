package org.example.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.example.dto.SetmealDto;
import org.example.pojo.Setmeal;

import java.util.List;

/**
 * ClassName: SetmealService
 * Package: org.example.service
 * Description:
 *
 * @Autehor 屈子岩
 * @Create 2024/8/18 12:07
 * @Version 1.0
 */
public interface SetmealService extends IService<Setmeal> {
    Page page(Integer page, Integer pageSize, String name);

    SetmealDto getByIdWithFlavor(Long id);

    void updateWithFlavor(SetmealDto setmealDto);

    void removeBatchByIds(List<Long> ids);

    void status(List<Long> ids, Integer status);

    void saveWithFlavor(SetmealDto setmealDto);

    List<Setmeal> list(Setmeal setmeal);

}
