package com.kirakira.server.controller;


import com.kirakira.server.common.CustomResponse;
import com.kirakira.server.pojo.Video;
import com.kirakira.server.service.DanmuService;
import com.kirakira.server.service.SearchService;
import com.kirakira.server.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class SearchController {
    @Autowired
    private SearchService searchService;

    @GetMapping("/search/user")
    public CustomResponse searchU(@RequestParam(defaultValue = "") String keyword){
        List<UserVO> u=searchService.userSearch(keyword);
        Map<String,Object> data=new HashMap<>();
        data.put("list",u);
        data.put("total",u.size());
        return CustomResponse.ok(data);

    }

    @GetMapping("/search/video")
        public CustomResponse searchV(@RequestParam(defaultValue = "")String keyword){
            List<Video> v=searchService.videoSearch(keyword);
            Map<String,Object> data=new HashMap<>();
            data.put("list",v);
            data.put("total",v.size());
            return CustomResponse.ok(data);
        }
    }

