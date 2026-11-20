package com.kirakira.server.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kirakira.server.mapper.DanmuMapper;
import com.kirakira.server.mapper.UserMapper;
import com.kirakira.server.mapper.VideoMapper;
import com.kirakira.server.pojo.Danmu;
import com.kirakira.server.pojo.User;
import com.kirakira.server.pojo.Video;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 启动时把 resources/data/videos.json 灌进数据库（只在表为空时执行一次）。
 * 真实项目里这一步叫"初始化种子数据"，通常只在开发环境做。
 */
@Component
public class DataSeeder implements CommandLineRunner {

    private final VideoMapper videoMapper;
    private final UserMapper userMapper;
    private final DanmuMapper danmuMapper;
    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(VideoMapper videoMapper, UserMapper userMapper, DanmuMapper danmuMapper,
                      ObjectMapper objectMapper, PasswordEncoder passwordEncoder) {
        this.videoMapper = videoMapper;
        this.userMapper = userMapper;
        this.danmuMapper = danmuMapper;
        this.objectMapper = objectMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        seedAdmin();
        seedVideos();
        seedDanmu();
    }

    /** 建一个测试账号：admin / 123456 */
    private void seedAdmin() {
        if (userMapper.selectCount(null) > 0) {
            System.out.println("[seed] user 表已有数据，跳过");
            return;
        }
        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("123456")); // 存哈希
        admin.setNickname("管理员");
        admin.setAvatar("");
        admin.setSign("我是 kirakira 的第一个用户");
        admin.setExp(100);
        admin.setCoin(BigDecimal.valueOf(10));
        admin.setFans(0);
        admin.setFollows(0);
        userMapper.insert(admin);
        System.out.println("[seed] 已创建测试账号 admin / 123456");
    }

    /** 给前 3 个视频各造几条弹幕，方便前端联调时能看到东西 */
    private void seedDanmu() {
        if (danmuMapper.selectCount(null) > 0) {
            System.out.println("[seed] danmu 表已有数据，跳过");
            return;
        }

        // {vid, 第几秒, 内容, 颜色}
        String[][] rows = {
                {"1", "1.5", "前排围观", "#ffffff"},
                {"1", "3.0", "这个雪景拍得真好", "#ffffff"},
                {"1", "5.5", "手抖得像帕金森哈哈哈哈", "#ffd166"},
                {"1", "8.0", "北方人表示见怪不怪", "#ffffff"},
                {"1", "12.5", "求设备清单", "#06d6a0"},

                {"2", "2.0", "全程无剪辑？我不信", "#ffffff"},
                {"2", "4.5", "UP 主太强了", "#ffd166"},
                {"2", "7.0", "这波操作可以", "#ffffff"},
                {"2", "10.0", "已三连", "#06d6a0"},
                {"2", "15.0", "求源码", "#ffffff"},

                {"3", "1.0", "深夜放毒", "#ffffff"},
                {"3", "3.5", "番茄鸡蛋面永远的神", "#ffd166"},
                {"3", "6.0", "看饿了", "#ffffff"},
                {"3", "9.0", "宿舍能用吗", "#06d6a0"},
                {"3", "11.5", "明天就做", "#ffffff"},
        };

        for (String[] row : rows) {
            Danmu d = new Danmu();
            d.setVid(Integer.valueOf(row[0]));
            d.setUid(1);
            d.setContent(row[2]);
            d.setTimeSec(new BigDecimal(row[1]));
            d.setColor(row[3]);
            danmuMapper.insert(d);
        }
        System.out.println("[seed] 已导入 " + rows.length + " 条弹幕");
    }

    private void seedVideos() throws Exception {
        if (videoMapper.selectCount(null) > 0) {
            System.out.println("[seed] video 表已有数据，跳过初始化");
            return;
        }

        try (InputStream in = getClass().getResourceAsStream("/data/videos.json")) {
            if (in == null) {
                System.out.println("[seed] 没找到 videos.json");
                return;
            }
            List<Video> videos = objectMapper.readValue(in, new TypeReference<List<Video>>() {});
            for (Video v : videos) {
                v.setUid(uidOf(v.getAuthor()));
                videoMapper.insert(v);
            }
            System.out.println("[seed] 已导入 " + videos.size() + " 条视频");
        }
    }

    /** 作者名 → uid。真实项目里 video 表直接存 uid，这里只是给种子数据补上。 */
    private int uidOf(String author) {
        Map<String, Integer> map = new HashMap<>();
        map.put("kira的摄影日记", 1);
        map.put("前端小张", 2);
        map.put("会做饭的程序员", 3);
        map.put("折腾不止", 4);
        map.put("橘猫食堂", 5);
        map.put("样式重构师", 6);
        map.put("改造日记", 7);
        map.put("影视速看", 8);
        return map.getOrDefault(author, 1);
    }
}
