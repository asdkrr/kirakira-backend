package com.kirakira.server.vo;

import com.kirakira.server.pojo.User;

import java.math.BigDecimal;

/**
 * 返回给前端的用户信息。
 * 为什么不直接返回 User？因为它带着 password 字段——绝不能泄露出去。
 * 这种"给前端看的对象"叫 VO（View Object）或 DTO。
 */
public class UserVO {

    private Integer uid;
    private String username;
    private String nickname;
    private String avatar;
    private String sign;
    private Integer exp;
    private BigDecimal coin;
    private Integer fans;
    private Integer follows;

    public static UserVO from(User user) {
        UserVO vo = new UserVO();
        vo.uid = user.getId();
        vo.username = user.getUsername();
        vo.nickname = user.getNickname();
        vo.avatar = user.getAvatar();
        vo.sign = user.getSign();
        vo.exp = user.getExp();
        vo.coin = user.getCoin();
        vo.fans = user.getFans();
        vo.follows = user.getFollows();
        return vo;
    }

    public Integer getUid() { return uid; }
    public String getUsername() { return username; }
    public String getNickname() { return nickname; }
    public String getAvatar() { return avatar; }
    public String getSign() { return sign; }
    public Integer getExp() { return exp; }
    public BigDecimal getCoin() { return coin; }
    public Integer getFans() { return fans; }
    public Integer getFollows() { return follows; }
}
