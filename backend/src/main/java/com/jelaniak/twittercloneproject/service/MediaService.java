package com.jelaniak.twittercloneproject.service;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.jelaniak.twittercloneproject.model.Media;
import com.jelaniak.twittercloneproject.repository.MediaRepository;

@Service
public class MediaService {

    private final S3Service s3Service;
    private final MediaRepository mediaRepository;

    @Autowired
    public MediaService(S3Service s3Service, MediaRepository mediaRepository) {
        this.s3Service = s3Service;
        this.mediaRepository = mediaRepository;
    }

    public Media uploadMedia(MultipartFile multipartFile) {
        String mediaUrl = s3Service.uploadMedia(multipartFile);

        var media = new Media();
        media.setMediaId(media.getMediaId());
        media.setMediaUrl(mediaUrl);
        media.setMediaType(multipartFile.getContentType());
        media.setMediaKey(s3Service.getKey());

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
