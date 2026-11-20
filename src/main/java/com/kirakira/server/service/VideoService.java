package com.kirakira.server.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kirakira.server.mapper.VideoMapper;
import com.kirakira.server.pojo.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 业务层：Controller 只负责接参数，真正的取数逻辑写在这里。
 * 以后要加缓存、权限判断、组装 DTO，都在这一层做。
 */
@Service
public class VideoService {

    @Autowired
    private VideoMapper videoMapper;

    /** 分页查视频 */
    public List<Video> page(int page, int size) {
        // Page 是 MyBatis-Plus 的分页对象，会自动拼 LIMIT
        Page<Video> p = new Page<>(page, size);
        return videoMapper.selectPage(p, null).getRecords();
    }

    public long count() {
        return videoMapper.selectCount(null);
    }

    public Video getOne(int vid) {
        return videoMapper.selectById(vid);
    }

    /** 某个用户的作品（个人空间用） */
    public List<Video> byUid(int uid) {
        QueryWrapper<Video> wrapper = new QueryWrapper<>();
        wrapper.eq("uid", uid);
        return videoMapper.selectList(wrapper);
    }
}
