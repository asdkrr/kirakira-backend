package com.kirakira.server.service;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kirakira.server.mapper.DanmuMapper;
import com.kirakira.server.pojo.Danmu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class DanmuService {
    @Autowired
    private DanmuMapper danmuMapper;

    public List<Danmu> getDanmu(int vid ){
        LambdaQueryWrapper<Danmu> w=new LambdaQueryWrapper<>();
        w.eq(Danmu::getVid,vid);
        w.orderByAsc(Danmu::getTimeSec);
        return danmuMapper.selectList(w);
    }

    public Danmu sendDanmu(Danmu d, Integer uid){
        d.setUid(uid);
        d.setCreatedAt(null);
        d.setId(null);
        d.setColor(null);
        danmuMapper.insert(d);
        return danmuMapper.selectById(d.getId());
    }
}
