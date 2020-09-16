package com.cashmovie.movielibrary.controllers;

import com.cashmovie.movielibrary.exceptions.ImproperFileTypeException;
import com.cashmovie.movielibrary.services.AmazonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@RestController
@CrossOrigin
@RequestMapping("/storage/")
public class BucketController {


    private AmazonService amazonService;

    @Autowired
    BucketController(AmazonService amazonService) {
        this.amazonService = amazonService;
    }

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestPart(value = "file") MultipartFile file) {
      try {
        return this.amazonService.uploadFile(file);
      } catch (ImproperFileTypeException e) {
        throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST, "Wrong File Type", e);
      }
    }

    @DeleteMapping("/deleteFile")
    public String deleteFile(@RequestBody String fileUrl) {
        return this.amazonService.deleteFileFromS3Bucket(fileUrl);
    }
}
