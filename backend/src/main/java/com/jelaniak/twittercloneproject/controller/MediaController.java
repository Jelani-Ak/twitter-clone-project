package com.jelaniak.twittercloneproject.controller;

import com.jelaniak.twittercloneproject.model.Media;
import com.jelaniak.twittercloneproject.service.MediaService;
import com.jelaniak.twittercloneproject.service.cloud.CloudinaryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Deprecated
@CrossOrigin()
@RestController
@RequestMapping(value = "/api/v1/media")
public class MediaController {

    private final CloudinaryService cloudinaryService;
    private final MediaService mediaService;

    @Autowired
    public MediaController(CloudinaryService cloudinaryService, MediaService mediaService) {
        this.cloudinaryService = cloudinaryService;
        this.mediaService = mediaService;
    }

    @RequestMapping(
            value = "/upload",
            method = RequestMethod.POST)
    public ResponseEntity<Media> uploadMeda(@RequestParam(value = "file") MultipartFile file) throws IOException {
        return new ResponseEntity<>(mediaService.uploadMedia(file), HttpStatus.CREATED);
    }

    @RequestMapping(
            value = "/get/{mediaId}",
            method = RequestMethod.GET)
    public ResponseEntity<Media> getMedia(@PathVariable ObjectId mediaId) {
        return new ResponseEntity<>(mediaService.getMediaById(mediaId), HttpStatus.OK);
    }

    @RequestMapping(
            value = "/delete/{mediaId}",
            method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteRepositoryMedia(@PathVariable ObjectId mediaId) {
        mediaService.deleteMedia(mediaId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(
            value = "/delete/cloudinary/{publicId}",
            method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteS3Media(@PathVariable String publicId) throws IOException {
        cloudinaryService.deleteCloudinaryMedia(publicId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
