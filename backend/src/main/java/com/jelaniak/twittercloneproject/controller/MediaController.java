package com.jelaniak.twittercloneproject.controller;

import com.jelaniak.twittercloneproject.model.Media;
import com.jelaniak.twittercloneproject.service.MediaService;
import com.jelaniak.twittercloneproject.service.S3Service;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/media")
public class MediaController {

    @Autowired
    private S3Service s3Service;

    @Autowired
    private MediaService mediaService;

    @RequestMapping(
            value = "/upload",
            method = RequestMethod.POST)
    public ResponseEntity<Media> uploadMeda(@RequestParam("file") MultipartFile file) {
        return new ResponseEntity<>(mediaService.uploadMedia(file), HttpStatus.CREATED);
    }

    @RequestMapping(
            value = "/get/{mediaId}",
            method = RequestMethod.GET)
    public ResponseEntity<Media> getMedia(@PathVariable ObjectId mediaId) {
        return new ResponseEntity<>(mediaService.getMediaByID(mediaId), HttpStatus.OK);
    }

    @RequestMapping(
            value = "/delete/{mediaId}",
            method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteRepositoryMedia(@PathVariable ObjectId mediaId) {
        mediaService.deleteMedia(mediaId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(
            value = "/delete/s3/{s3MediaKey}",
            method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteS3Media(@PathVariable String s3MediaKey) {
        s3Service.deleteS3Media(s3MediaKey);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
