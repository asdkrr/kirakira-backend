package com.kirakira.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kirakira.server.common.CustomResponse;
import com.kirakira.server.pojo.Video;
import com.kirakira.server.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller 只做三件事：接参数、调 Service、包成统一响应。
 * 真正的查询逻辑在 VideoService 里。
 */
@RestController
public class VideoController {

    private static final String SAMPLE_VIDEO =
            "https://interactive-examples.mdn.mozilla.net/media/cc0-videos/flower.mp4";

    @Autowired
    private VideoService videoService;

    @Autowired
    private ObjectMapper objectMapper;

    /** GET /home/videos?page=1&size=12 */
    @GetMapping("/home/videos")
    public CustomResponse homeVideos(@RequestParam(defaultValue = "1") int page,
                                     @RequestParam(defaultValue = "12") int size) {
        List<Video> list = videoService.page(page, size);

        Map<String, Object> data = new HashMap<>();
        data.put("list", list);
        data.put("total", videoService.count());
        return CustomResponse.ok(data);
    }

    /** GET /video/getone?vid=3 */
    @GetMapping("/video/getone")
    public CustomResponse getOne(@RequestParam int vid) {
        Video video = videoService.getOne(vid);
        if (video == null) {
            return CustomResponse.fail(404, "视频不存在");
        }

        // 转成 Map 才能往里加前端要的额外字段
        Map<String, Object> data = objectMapper.convertValue(video, Map.class);
        data.put("videoUrl", SAMPLE_VIDEO);
        data.put("uploaderUid", video.getUid());
        return CustomResponse.ok(data);
    }

    /** GET /space/videos?uid=1 */
    @GetMapping("/space/videos")
    public CustomResponse spaceVideos(@RequestParam int uid) {
        List<Video> list = videoService.byUid(uid);
        Map<String, Object> data = new HashMap<>();
        data.put("list", list);
        data.put("total", list.size());
        return CustomResponse.ok(data);
    }
}
