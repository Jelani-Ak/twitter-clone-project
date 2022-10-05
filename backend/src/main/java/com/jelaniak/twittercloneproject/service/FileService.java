package com.jelaniak.twittercloneproject.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String uploadMedia(MultipartFile file);
}
