package com.kirakira.server.controller;


import com.kirakira.server.common.CustomResponse;
import com.kirakira.server.pojo.User;
import com.kirakira.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class SpaceController {
    @Autowired
    private UserService userService;

    @GetMapping("/space/user")
    public CustomResponse spaceUser(@RequestParam Integer uid){

        User u=userService.getById(uid);
        Map<String,Object>data=new HashMap<>();
        data.put("uid",u.getId());
        data.put("nickname",u.getNickname());
        data.put("sign",u.getSign());
        data.put("fans",u.getFans());
        data.put("follows",u.getFollows());
        data.put("likes",0);
        data.put("plays",0);
        data.put("level",6);
        return CustomResponse.ok(data);
    }

}
