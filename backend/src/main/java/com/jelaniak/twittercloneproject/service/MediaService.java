package com.jelaniak.twittercloneproject.service;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.jelaniak.twittercloneproject.model.Media;
import com.jelaniak.twittercloneproject.repository.MediaRepository;

import java.io.IOException;

@Service
public class MediaService {

    private final CloudinaryService cloudinaryService;
    private final MediaRepository mediaRepository;

    @Autowired
    public MediaService(CloudinaryService cloudinaryService, MediaRepository mediaRepository) {
        this.cloudinaryService = cloudinaryService;
        this.mediaRepository = mediaRepository;
    }

    public Media uploadMedia(MultipartFile multipartFile) throws IOException {
        Object mediaUrl = cloudinaryService.uploadCloudinaryMedia(multipartFile);

        var media = new Media();
        media.setMediaId(media.getMediaId());
        media.setMediaUrl(mediaUrl.toString());
        media.setMediaType(multipartFile.getContentType());
        media.setMediaKey(cloudinaryService.getPublicId());

        var savedMedia = mediaRepository.save(media);

        return new Media(
                savedMedia.getMediaId(),
                savedMedia.getMediaUrl(),
                savedMedia.getMediaType(),
                savedMedia.getMediaKey()
        );
    }

    @Transactional
    public void deleteMedia(ObjectId mediaId) {
        mediaRepository.deleteByMediaId(mediaId);
    }

    public Media getMediaByID(ObjectId mediaId) {
        return mediaRepository.findById(mediaId)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find video by id: " + mediaId));
    }
}
