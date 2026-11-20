package com.kirakira.server.controller;


import com.kirakira.server.common.CustomResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
public class CommentController {

    @GetMapping("/comment/get")
    public CustomResponse get(@RequestParam int vid,
                              @RequestParam(defaultValue = "1")int page,
                              @RequestParam(defaultValue = "5")int size){
        Map<String,Object> data =new HashMap<>();
        data.put("list",new ArrayList<>());
        data.put("total",0);
        return CustomResponse.ok(data);
    }

}
