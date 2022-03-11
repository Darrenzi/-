package com.cosmetics.module.solution.mapper;

import com.cosmetics.module.solution.entity.Solution;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @since 2022-02-23
 */
public interface SolutionMapper extends BaseMapper<Solution> {

    @Select("SELECT solution.* FROM solution JOIN test ON solution.test_id = test.id " +
            "WHERE test.number LIKE CONCAT('%',#{condition},'%')")
    List<Solution> search(String condition);
}
