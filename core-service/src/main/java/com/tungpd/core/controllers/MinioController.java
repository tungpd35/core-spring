package com.tungpd.core.controllers;


import com.tungpd.core.services.MinioService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@RestController
@RequestMapping("/api/minio")
public class MinioController {

    private final MinioService minioService;

    public MinioController(MinioService minioService) {
        this.minioService = minioService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file,
                                         @RequestParam(value = "bucket", required = false) String bucket) {
        String b = bucket == null ? "my-bucket" : bucket;
        String objectName = file.getOriginalFilename();
        try {
            minioService.upload(b, objectName, file);
            return ResponseEntity.ok("Uploaded: " + objectName);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Upload failed: " + e.getMessage());
        }
    }

    @GetMapping("/download")
    public ResponseEntity<?> download(@RequestParam("bucket") String bucket,
                                      @RequestParam("object") String object) {
        try {
            InputStream is = minioService.download(bucket, object);
            InputStreamResource resource = new InputStreamResource(is);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + object + "\"")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Not found: " + e.getMessage());
        }
    }

    @DeleteMapping("/object")
    public ResponseEntity<String> delete(@RequestParam("bucket") String bucket,
                                         @RequestParam("object") String object) {
        try {
            minioService.removeObject(bucket, object);
            return ResponseEntity.ok("Deleted: " + object);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Delete failed: " + e.getMessage());
        }
    }

    @GetMapping("/signed-url")
    public ResponseEntity<String> presigned(@RequestParam("bucket") String bucket,
                                            @RequestParam("object") String object,
                                            @RequestParam(value = "expiry", defaultValue = "60") int expiryMinutes) {
        try {
            String url = minioService.getPresignedUrl(bucket, object, expiryMinutes);
            return ResponseEntity.ok(url);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Create presigned url failed: " + e.getMessage());
        }
    }
}
