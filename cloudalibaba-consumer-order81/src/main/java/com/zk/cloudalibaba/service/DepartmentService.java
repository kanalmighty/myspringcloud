package com.zk.cloudalibaba.service;

import com.zk.cloudalibaba.entities.Department;
import com.zk.springcloud.entities.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Component
@FeignClient(value = "CLOUD-DEPARTMENT-SERVICE")
public interface DepartmentService {
    @RequestMapping(value = "/department", method = RequestMethod.PUT)
    CommonResult updateDepartmentByPrimaryKey(@RequestBody Department department);
}
