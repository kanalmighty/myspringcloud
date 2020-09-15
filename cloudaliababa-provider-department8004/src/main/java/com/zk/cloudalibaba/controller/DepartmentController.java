package com.zk.cloudalibaba.controller;

import com.zk.cloudalibaba.entities.Department;
import com.zk.cloudalibaba.service.DepartmentService;
import com.zk.springcloud.entities.CommonResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/department")
public class DepartmentController {

    @Resource
    private RestTemplate restTemplate;


    @Resource
    private DepartmentService departmentService;

    @RequestMapping(value = "/detail", method = RequestMethod.PUT)
    public CommonResult updateDepartmentByPk(@RequestBody Department department) {
        CommonResult commonResult =  null;
        System.out.println(department.toString());
        int result = departmentService.updateDepartmentByPrimaryKey(department);
        if (result==1){
             commonResult =  new CommonResult(200,"ok", null);
        } else {
        }
        return commonResult;
    }
}
