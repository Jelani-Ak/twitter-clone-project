package com.jelaniak.twittercloneproject.utility;

import com.jelaniak.twittercloneproject.model.Media;
import org.bson.types.ObjectId;

public class MediaUtility {
    public static Media getNewMedia(int number) {
        Media media = new Media();

        media.setMediaId(new ObjectId());
        media.setFilename("Media " + number);
        media.setMediaUrl("http://www.media" + number + ".co.uk/example");

        return media;
    }
}
