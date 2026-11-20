package com.kirakira.server.controller;

import com.kirakira.server.common.CustomResponse;
import com.kirakira.server.pojo.Danmu;
import com.kirakira.server.service.DanmuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
public class DanmuController {
    @Autowired
    private DanmuService danmuservice;

    @GetMapping("/danmu/get")
    public CustomResponse getDanmu(@RequestParam int vid){

        List<Danmu> list=danmuservice.getDanmu(vid);
        return CustomResponse.ok(list);//CustomResponse.fail()?
    }
    @PostMapping("/danmu/send")
    public CustomResponse sendDanmu(@RequestBody Danmu d,
                                    @RequestAttribute("uid") Integer uid) {
        if(d.getContent()==null)
            return CustomResponse.fail(400,"发送内容不能为空");
        if(d.getContent().length()>30||d.getContent().trim().isEmpty())
            return CustomResponse.fail(400,"发送内容过长或空内容");
        danmuservice.sendDanmu(d,uid);
            return CustomResponse.ok(d);
    }
}
