package com.kirakira.server.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kirakira.server.common.CustomResponse;
import com.kirakira.server.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;

/**
 * 登录拦截器：检查 Authorization: Bearer <token>
 * - 通过：把 uid 放进 request 属性，Controller 里取
 * - 不通过：直接返回 401 的 JSON，不再进 Controller
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 浏览器跨域预检请求直接放行
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        String auth = request.getHeader("Authorization");
        if (auth == null || !auth.startsWith("Bearer ")) {
            write401(response, "未登录");
            return false;
        }

        Integer uid = jwtUtil.parseUid(auth.substring(7));
        if (uid == null) {
            write401(response, "登录已过期，请重新登录");
            return false;
        }

        // Controller 里用 request.getAttribute("uid") 拿到当前用户
        request.setAttribute("uid", uid);
        return true;
    }

    private void write401(HttpServletResponse response, String message) throws Exception {
        response.setStatus(HttpServletResponse.SC_OK); // HTTP 200 + 业务码 401，和前端约定一致
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write(objectMapper.writeValueAsString(CustomResponse.fail(401, message)));
    }
}
