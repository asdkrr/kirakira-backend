package com.kirakira.server.service;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kirakira.server.mapper.UserMapper;
import com.kirakira.server.mapper.VideoMapper;
import com.kirakira.server.pojo.User;
import com.kirakira.server.pojo.Video;
import com.kirakira.server.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchService {

@Autowired
private VideoMapper videoMapper;
@Autowired
private UserMapper userMapper;

    public List<UserVO> userSearch(String user){
        LambdaQueryWrapper<User> w=new LambdaQueryWrapper<>();
        if(user!=null&&!user.trim().isEmpty()){
        w.like(User::getNickname,user)
                .or()
                .like(User::getSign,user);
        }
        return userMapper.selectList(w)
                .stream()
                .map(UserVO::from)
                .collect(Collectors.toList());
    }

    public List<Video> videoSearch(String title){
        LambdaQueryWrapper<Video> w=new LambdaQueryWrapper<>();
        if(title!=null&&!title.trim().isEmpty()) {
            w.like(Video::getTitle, title)
                    .or()
                    .like(Video::getAuthor, title);
        }
        return videoMapper.selectList(w);

    }
}
