package com.jelaniak.twittercloneproject.controller;

import com.jelaniak.twittercloneproject.model.Media;
import com.jelaniak.twittercloneproject.service.MediaService;
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
    public void deleteMedia(@PathVariable String mediaId) {
        mediaService.deleteMedia(mediaId);
    }
}
