package com.kirakira.server.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kirakira.server.common.BusinessException;
import com.kirakira.server.mapper.UserMapper;
import com.kirakira.server.pojo.User;
import com.kirakira.server.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    /** 注册：查重 → 加密密码 → 插入 */
    public User register(String username, String password, String nickname) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        if (userMapper.selectCount(wrapper) > 0) {
            throw new BusinessException("用户名已存在");
        }

        User user = new User();
        user.setUsername(username);
        // 关键：存哈希，不存明文
        user.setPassword(passwordEncoder.encode(password));
        user.setNickname(nickname == null || nickname.isEmpty() ? username : nickname);
        user.setAvatar("");
        user.setSign("");
        user.setExp(0);
        user.setCoin(BigDecimal.ZERO);
        user.setFans(0);
        user.setFollows(0);

        userMapper.insert(user);
        return user;
    }

    /** 登录：按用户名查 → 比对哈希 → 签发 token */
    public Map<String, Object> login(String username, String password) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        User user = userMapper.selectOne(wrapper);

        // 用户不存在和密码错误返回同一句话，防止别人"试出"哪些账号存在
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new BusinessException(401, "用户名或密码错误");
        }

        String token = jwtUtil.createToken(user.getId(), user.getUsername());

        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("user", com.kirakira.server.vo.UserVO.from(user));
        return result;
    }

    public User getById(Integer uid) {
        User user = userMapper.selectById(uid);
        if (user == null) {
            throw new BusinessException(404, "用户不存在");
        }
        return user;
    }
}
