package com.cosmetics.module.solution.entity;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Miranda
 * @create 2022/3/4 16:19
 **/
@Data
public class UploadForm {
    private int id;
    private String photoType;
    private String accessoryType;
    MultipartFile photo;
    MultipartFile accessory;
}
