package com.cosmetics.module.solution.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cosmetics.common.bean.PageRequest;
import com.cosmetics.module.solution.entity.Solution;
import com.cosmetics.module.solution.mapper.SolutionMapper;
import com.cosmetics.module.solution.service.ISolutionService;
import com.cosmetics.common.service.BaseService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @since 2022-02-23
 */
@Service
public class SolutionServiceImpl extends BaseService<Solution> implements ISolutionService {
    public IPage<Solution> getReports(PageRequest pageRequest) {
        Page<Solution> page = new Page(pageRequest.getNum(), pageRequest.getSize());
        QueryWrapper<Solution> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("status", 1);
        queryWrapper.isNotNull("report");
        queryWrapper.ne("report", "");
        return baseMapper.selectPage(page, queryWrapper);
    }
}
