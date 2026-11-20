package com.kirakira.server.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 弹幕实体，对应 danmu 表。
 *
 * 字段名用驼峰（timeSec / createdAt），
 * 因为 application.yml 里开了 map-underscore-to-camel-case，
 * MyBatis-Plus 会自动对应到列名 time_sec / created_at。
 */
@TableName("danmu")
public class Danmu {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Integer vid;
    private Integer uid;
    @JsonProperty("text")
    private String content;
    @JsonProperty("time")
    private BigDecimal timeSec;   // ↔ time_sec（DECIMAL(7,2)）
    private String color;
    private Date createdAt;       // ↔ created_at

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVid() {
        return vid;
    }

    public void setVid(Integer vid) {
        this.vid = vid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public BigDecimal getTimeSec() {
        return timeSec;
    }

    public void setTimeSec(BigDecimal timeSec) {
        this.timeSec = timeSec;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
