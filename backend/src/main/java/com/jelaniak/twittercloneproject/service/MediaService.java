package com.jelaniak.twittercloneproject.service;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jelaniak.twittercloneproject.model.Media;
import com.jelaniak.twittercloneproject.repository.MediaRepository;

@Service
public class MediaService {

    @Autowired
    private S3Service s3Service;

    @Autowired
    private MediaRepository mediaRepository;

    public Media uploadMedia(MultipartFile multipartFile) {
        String mediaUrl = s3Service.uploadMedia(multipartFile);
        var media = new Media();
        media.setMediaUrl(mediaUrl);

        var savedMedia = mediaRepository.save(media);

        return new Media(
                savedMedia.getMediaId(),
                savedMedia.getMediaUrl()
        );
    }

    public void deleteMedia(String key) {
        s3Service.deleteMedia(key);
    }

    public Media getMediaByID(ObjectId mediaId) {
        return mediaRepository.findById(mediaId)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find video by id: " + mediaId));
    }
}
