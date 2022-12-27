package com.jelaniak.twittercloneproject.service;

import com.jelaniak.twittercloneproject.service.cloud.CloudinaryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jelaniak.twittercloneproject.model.Media;
import com.jelaniak.twittercloneproject.repository.MediaRepository;

import java.io.IOException;

@Service
public class MediaService {

    private final MediaRepository mediaRepository;
    private final CloudinaryService cloudinaryService;

    @Autowired
    public MediaService(
            MediaRepository mediaRepository,
            CloudinaryService cloudinaryService) {
        this.mediaRepository = mediaRepository;
        this.cloudinaryService = cloudinaryService;
    }

    public Media uploadMedia(MultipartFile multipartFile) throws IOException {
        Object mediaUrl = cloudinaryService.uploadCloudinaryMedia(multipartFile);

        Media media = new Media();

        media.setMediaId(new ObjectId());
        media.setMediaUrl(mediaUrl.toString());
        media.setMediaType(multipartFile.getContentType());
        media.setMediaKey(cloudinaryService.getPublicId());

        mediaRepository.save(media);

        return media;
    }

    public void deleteMedia(ObjectId mediaId) {
        mediaRepository.deleteByMediaId(mediaId);
    }

    public Media getMediaById(ObjectId mediaId) {
        return mediaRepository.findById(mediaId)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find video by id: " + mediaId));
    }
}
