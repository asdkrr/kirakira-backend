package com.kirakira.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kirakira.server.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
