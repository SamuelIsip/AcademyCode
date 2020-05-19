package com.example.academycode.model.response;

import com.example.academycode.model.VideoTeoria;

import java.util.List;

public class VideosTResponse {

    private boolean error;
    private List<VideoTeoria> videos;

    public VideosTResponse(boolean error, List<VideoTeoria> videos) {
        this.error = error;
        this.videos = videos;
    }

    public boolean isError() {
        return error;
    }

    public List<VideoTeoria> getVideos() {
        return videos;
    }
}
