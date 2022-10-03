package com.jelaniak.twittercloneproject.utils;

import org.bson.types.ObjectId;

import com.jelaniak.twittercloneproject.model.Media;

public class MediaUtility {
    public static Media getNewMedia(int number) {
        Media media = new Media();

        media.setMediaId(new ObjectId());
        media.setFilename("Media " + number);
        media.setMediaUrl("http://www.media" + number + ".co.uk/example");

        return media;
    }
}
