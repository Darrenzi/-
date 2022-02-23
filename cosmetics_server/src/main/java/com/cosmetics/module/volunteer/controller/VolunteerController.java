package com.cosmetics.module.volunteer.controller;


import com.cosmetics.module.volunteer.entity.Volunteer;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.cosmetics.common.controller.BaseController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Miranda
 * @since 2022-02-23
 */
@RestController
@RequestMapping("/volunteer")
public class VolunteerController extends BaseController<Volunteer> {

}
