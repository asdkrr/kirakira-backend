package com.kirakira.server.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 视频实体：一行数据 = 一个对象。
 * 字段名和表的列名靠"驼峰 ↔ 下划线"自动对应（uploadDate ↔ upload_date）。
 */
@TableName("video")
public class Video {

    @TableId(type = IdType.INPUT)
    private Integer id;

    private Integer uid;
    private String title;
    private String author;
    private String play;
    private String duration;
    private String cover;
    private String badge;
    private String danmu;

    // exist = false：告诉 MyBatis-Plus"这个字段不对应数据库列"，
    // 否则它会把 test_field 拼进 SQL，报 Unknown column

    // 前端字段叫 date，列叫 upload_date
    @JsonProperty("date")
    private String uploadDate;

    private String descr;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getUid() { return uid; }
    public void setUid(Integer uid) { this.uid = uid; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getPlay() { return play; }
    public void setPlay(String play) { this.play = play; }

    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }

    public String getCover() { return cover; }
    public void setCover(String cover) { this.cover = cover; }

    public String getBadge() { return badge; }
    public void setBadge(String badge) { this.badge = badge; }

    public String getDanmu() { return danmu; }
    public void setDanmu(String danmu) { this.danmu = danmu; }

    public String getUploadDate() { return uploadDate; }
    public void setUploadDate(String uploadDate) { this.uploadDate = uploadDate; }

    public String getDescr() { return descr; }
    public void setDescr(String descr) { this.descr = descr; }
}
