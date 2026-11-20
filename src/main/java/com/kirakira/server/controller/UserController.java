package com.kirakira.server.controller;

import com.kirakira.server.common.BusinessException;
import com.kirakira.server.common.CustomResponse;
import com.kirakira.server.pojo.User;
import com.kirakira.server.service.UserService;
import com.kirakira.server.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /** POST /user/register  { username, password, nickname } */
    @PostMapping("/register")
    public CustomResponse register(@RequestBody Map<String, String> body) {
        String username = trim(body.get("username"));
        String password = body.get("password");
        String nickname = trim(body.get("nickname"));

        if (username.length() < 3 || username.length() > 20) {
            throw new BusinessException("用户名长度需要 3-20 个字符");
        }
        if (password == null || password.length() < 6) {
            throw new BusinessException("密码至少 6 位");
        }

        User user = userService.register(username, password, nickname);
        return CustomResponse.ok(UserVO.from(user));
    }

    /** POST /user/login  { username, password } */
    @PostMapping("/login")
    public CustomResponse login(@RequestBody Map<String, String> body) {
        String username = trim(body.get("username"));
        String password = body.get("password");
        if (username.isEmpty() || password == null || password.isEmpty()) {
            throw new BusinessException("用户名和密码不能为空");
        }

        return CustomResponse.ok(userService.login(username, password));
    }

    /** GET /user/info  需要 Authorization: Bearer <token> */
    @GetMapping("/info")
    public CustomResponse info(@RequestAttribute("uid") Integer uid) {
        return CustomResponse.ok(UserVO.from(userService.getById(uid)));
    }

    private String trim(String s) {
        return s == null ? "" : s.trim();
    }
}
