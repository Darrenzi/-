package com.cosmetics.module.solution.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cosmetics.common.bean.PageRequest;
import com.cosmetics.module.solution.entity.Solution;
import com.cosmetics.module.solution.mapper.SolutionMapper;
import com.cosmetics.module.solution.service.ISolutionService;
import com.cosmetics.common.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @since 2022-02-23
 */
@Service
public class SolutionServiceImpl extends BaseService<Solution> implements ISolutionService {

    public static String SAVE_PATH = "D:/cosmetics_images/";

    public IPage<Solution> getReports(PageRequest pageRequest) {
        Page<Solution> page = new Page(pageRequest.getNum(), pageRequest.getSize());
        QueryWrapper<Solution> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("status", 1);
        queryWrapper.isNotNull("report");
        queryWrapper.ne("report", "");
        return baseMapper.selectPage(page, queryWrapper);
    }

    public String saveFile(MultipartFile uploadFile, String type){
        try{
            File file = new File(SAVE_PATH);
            if(!file.exists()){
                //创建文件夹,会自动创建父文件夹
                file.mkdirs();
                //创建目录说明文件
                String descFilePath = new File(SAVE_PATH).getParentFile().toString()+"\\目录说明.txt";
                try(BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(descFilePath)))){
                    writer.write("此目录为保存 美妆测试系统 项目文件的目录");
                }
            }
            byte[] bytes = uploadFile.getBytes();
            //图片字节数组的md5
            String md5 = SecureUtil.md5(uploadFile.getInputStream());
            //图片保存路径
            String path = SAVE_PATH + md5+"."+type;
            try(BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(path))){
                os.write(bytes);
            }
            return md5+"."+type;
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
            return null;
        }
        catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }
}
