package com.tungpd.core.controllers;

import com.tungpd.core.services.SftpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/sftp")
public class SftpController {
    @Autowired
    private SftpService sftpService;

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("dir") String dir) throws Exception {
        sftpService.upload(file.getBytes(), dir, file.getOriginalFilename());
        return "success";
    }

    @GetMapping("/download")
    public ResponseEntity<?> downloadFile(@RequestParam("file") String file, @RequestParam("dir") String dir) throws Exception {
        return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).body(sftpService.download(dir, file));
    }
}
