package com.example.mtiktok.widget;
import android.service.autofill.DateValueSanitizer;

import com.google.gson.annotations.SerializedName;

import java.util.List;
public class VideoInfo {
    public List<Video> videos;

    public static class Video {
        @SerializedName("_id")
        public String id;
        @SerializedName("feedurl")
        public String feedurl;
        @SerializedName("nickname")
        public String nickname;
        @SerializedName("description")
        public String description;
        @SerializedName("likecount")
        public int likecount;
        @SerializedName("avatar")
        public String avatar;

        @Override
        public String toString() {
            return "Video{" +
                    "id=" + id +
                    ", name='" + nickname + '\'' +
                    ", description=" + description +
                    '}';
        }
    }
}
