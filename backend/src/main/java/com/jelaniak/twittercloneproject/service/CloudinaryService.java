package com.jelaniak.twittercloneproject.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.Singleton;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class CloudinaryService {
    private String publicId;

    private final Cloudinary cloudinary = Singleton.getCloudinary();

    public Object uploadCloudinaryMedia(MultipartFile file) throws IOException {

        //Prepare a Public Id
        var filenameExtension = StringUtils.getFilenameExtension(file.getOriginalFilename());

        //Create a unique value
        publicId = UUID.randomUUID() + "." + filenameExtension;

        var response = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
                "public_id", publicId,
                "use_filename", false,
                "unique_filename", true
        ));

        return response.get("url");
    }

    public void deleteCloudinaryMedia(String public_id) throws IOException {
        cloudinary.uploader().destroy(public_id, ObjectUtils.emptyMap());
    }

    public String getPublicId() {
        return publicId;
    }
}
