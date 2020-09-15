package com.zk.cloudalibaba.mapper;

import com.zk.cloudalibaba.entities.Department;

public interface DepartmentMapper {
    int insert(Department record);

    int insertSelective(Department record);
}