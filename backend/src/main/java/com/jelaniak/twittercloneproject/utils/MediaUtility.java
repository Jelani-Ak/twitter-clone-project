package com.jelaniak.twittercloneproject.utils;

import com.jelaniak.twittercloneproject.model.Media;
import org.bson.types.ObjectId;

public class MediaUtility {
    public static Media getNewMedia(int number) {
        Media media = new Media();

        media.setMediaId(new ObjectId());
        media.setMediaUrl("http://www.media" + number + ".co.uk/example");
        media.setMediaType("example.type");
        media.setMediaKey("example-key");

        return media;
    }
}
