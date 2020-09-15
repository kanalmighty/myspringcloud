package com.zk.cloudalibaba.service.impl;

import com.zk.cloudalibaba.entities.Department;
import com.zk.cloudalibaba.mapper.DepartmentMapper;
import com.zk.cloudalibaba.service.DepartmentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Resource
    private DepartmentMapper departmentMapper;
    @Override
    public int updateDepartmentByPrimaryKey(Department department) {
        int a = 1/(department.getId() - 1);
       return departmentMapper.updateByPrimaryKey(department);
    }
}
