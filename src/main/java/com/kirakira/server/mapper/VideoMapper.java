package com.kirakira.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kirakira.server.pojo.Video;
import org.apache.ibatis.annotations.Mapper;

/**
 * 只要继承 BaseMapper，就自动拥有：
 * selectById / selectList / insert / updateById / deleteById / selectPage ...
 * 不用写一行 SQL。
 */
@Mapper
public interface VideoMapper extends BaseMapper<Video> {
}
