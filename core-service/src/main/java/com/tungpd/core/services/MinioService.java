package com.tungpd.core.services;

import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class MinioService {

    private final MinioClient minioClient;
    private final String defaultBucket;

    public MinioService(MinioClient minioClient,
                        @Value("${minio.bucket}") String defaultBucket) {
        this.minioClient = minioClient;
        this.defaultBucket = defaultBucket;
    }

    // 1. Kiểm tra bucket tồn tại
    public boolean bucketExists(String bucketName) throws Exception {
        return minioClient.bucketExists(
                BucketExistsArgs.builder().bucket(bucketName).build()
        );
    }

    // 2. Tạo bucket nếu chưa tồn tại
    public void createBucketIfNotExists(String bucketName) throws Exception {
        boolean exists = bucketExists(bucketName);
        if (!exists) {
            minioClient.makeBucket(MakeBucketArgs.builder()
                    .bucket(bucketName)
                    .build());
        }
    }

    // 3. Upload từ MultipartFile
    public void upload(String bucketName, String objectName, MultipartFile file) throws Exception {
        createBucketIfNotExists(bucketName);
        try (InputStream is = file.getInputStream()) {
            PutObjectArgs args = PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .stream(is, file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build();
            minioClient.putObject(args);
        }
    }

    // 4. Upload từ InputStream (kèm size nếu có)
    public void upload(String bucketName, String objectName, InputStream stream, long size, String contentType) throws Exception {
        createBucketIfNotExists(bucketName);
        PutObjectArgs args = PutObjectArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .stream(stream, size, -1)
                .contentType(contentType)
                .build();
        minioClient.putObject(args);
    }

    // 5. Download: trả về InputStream
    public InputStream download(String bucketName, String objectName) throws Exception {
        GetObjectArgs args = GetObjectArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .build();
        return minioClient.getObject(args); // caller must close stream
    }

    // 6. Xóa object
    public void removeObject(String bucketName, String objectName) throws Exception {
        RemoveObjectArgs args = RemoveObjectArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .build();
        minioClient.removeObject(args);
    }

    // 7. Tạo presigned URL (GET)
    public String getPresignedUrl(String bucketName, String objectName, int expiryMinutes) throws Exception {
        // Expiry in seconds (MinIO SDK typically uses seconds)
        long expiry = TimeUnit.MINUTES.toSeconds(expiryMinutes);
        GetPresignedObjectUrlArgs args = GetPresignedObjectUrlArgs.builder()
                .method(Method.GET)
                .bucket(bucketName)
                .object(objectName)
                .expiry((int) expiry) // theo SDK: integer seconds
                .build();
        return minioClient.getPresignedObjectUrl(args);
    }

    // 8. List buckets (ví dụ)
    public List<Bucket> listBuckets() throws Exception {
        return minioClient.listBuckets();
    }
}
