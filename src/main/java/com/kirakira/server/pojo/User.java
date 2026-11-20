package com.kirakira.server.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户实体。注意：password 存的是 BCrypt 哈希（60 位），不是明文。
 */
@TableName("user")
public class User {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String username;
    private String password;
    private String nickname;
    private String avatar;
    private String sign;
    private Integer exp;
    private BigDecimal coin;
    private Integer fans;
    private Integer follows;
    private Date createdAt;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }

    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }

    public String getSign() { return sign; }
    public void setSign(String sign) { this.sign = sign; }

    public Integer getExp() { return exp; }
    public void setExp(Integer exp) { this.exp = exp; }

    public BigDecimal getCoin() { return coin; }
    public void setCoin(BigDecimal coin) { this.coin = coin; }

    public Integer getFans() { return fans; }
    public void setFans(Integer fans) { this.fans = fans; }

    public Integer getFollows() { return follows; }
    public void setFollows(Integer follows) { this.follows = follows; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}
