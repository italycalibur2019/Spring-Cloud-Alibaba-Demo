package com.italycalibur.ciallo.common.models.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.italycalibur.ciallo.common.models.entity.UserPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author dhr
 * @version 1.0
 * @description: 用户表 Mapper
 * @date 2025-02-08 13:06:13
 */
@DS("sys")
public interface UserMapper extends BaseMapper<UserPO> {
    /**
     * 根据用户名查询用户信息
     * @param username 用户名
     * @return UserPO
     */
    @Select("select * from sys.t_user_po where username = #{username}")
    UserPO selectByUsername(String username);
}
