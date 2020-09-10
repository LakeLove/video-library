package com.cashmovie.movielibrary.controllers;

import com.cashmovie.movielibrary.services.AmazonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/storage/")
public class BucketController {

    // Current max file size 10MB
    private final Long max_file_size = 10 * 1000000L;

    private AmazonService amazonService;

    @Autowired
    BucketController(AmazonService amazonService) {
        this.amazonService = amazonService;
    }

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestPart(value = "file") MultipartFile file) {
        if(file.getSize() < max_file_size) {
            return this.amazonService.uploadFile(file);
        } else {
            return null;
        }
    }

    @DeleteMapping("/deleteFile")
    public String deleteFile(@RequestPart(value = "url") String fileUrl) {
        return this.amazonService.deleteFileFromS3Bucket(fileUrl);
    }
}
