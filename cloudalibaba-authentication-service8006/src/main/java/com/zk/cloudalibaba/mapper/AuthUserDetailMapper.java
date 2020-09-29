package com.zk.cloudalibaba.mapper;

import com.zk.cloudalibaba.auth.AuthUserDetails;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface AuthUserDetailMapper {
    //根据userid查询用户信息
    @Select("select u.name username,u.password password,u.status enabled from user_info u where u.name = #{name}")
    AuthUserDetails getUserDetailsByName(String name);

    //根据userid查询用户角色列表
    @Select("select r.role_name from role_info r \n" +
            "left join user_role_relation ur\n" +
            "on r.id = ur.user_id left join user_info u \n" +
            "on u.id = ur.user_id\n" +
            "where u.name= #{name};")
    List<String> getRoleByUserName(String name);

    //根据用户橘色查询用户权限
    @Select("<script>" +
            "select m.url from menu_info m \n" +
            "left join role_menu_relation rm on m.id = rm.menu_id \n" +
            "left join role_info r on r.id = rm.role_id \n" +
            "where r.role_name in \n" +
            "<foreach collection='roleIds' item='roleId' open='(' separator=',' close=')'>#{roleId}</foreach></script>")
    List<String> getAuthByRoleId(@Param("roleIds") List<String> roleIds);

    //通过用户名去查找用户权限
    @Select("select m.url from menu_info m \n" +
            "left join role_menu_relation rm on m.id = rm.menu_id \n" +
            "left join role_info r on r.id = rm.role_id \n" +
            "left join user_role_relation ur\n" +
            "on r.id = ur.user_id left join user_info u on ur.user_id =  u.id  \n" +
            "where u.name= #{name};")
    List<String> getAuthByUserName(String name);
}
