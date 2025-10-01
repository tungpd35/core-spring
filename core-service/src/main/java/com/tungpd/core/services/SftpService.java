package com.tungpd.core.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.file.remote.session.Session;
import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;
import org.springframework.integration.sftp.session.SftpSession;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class SftpService {
    @Autowired
    private DefaultSftpSessionFactory sftpSessionFactory;

    public void upload(byte[] fileUpload, String remoteDir, String fileName) throws Exception {
        try (SftpSession session = sftpSessionFactory.getSession()) {
            createDirectoryIfNotExist(remoteDir);
            session.write(
                    new ByteArrayInputStream(fileUpload),
                    remoteDir + "/" + fileName
            );
        }
    }

    public byte[] download(String remoteDir, String fileName) throws Exception {
        try (SftpSession session = sftpSessionFactory.getSession()) {
            return session.readRaw(remoteDir + "/" + fileName).readAllBytes();
        }
    }

    public void createDirectoryIfNotExist(String remoteDir) throws Exception {
        try (Session<?> session = sftpSessionFactory.getSession()) {
            SftpSession sftp = (SftpSession) session;
            if (!sftp.exists(remoteDir)) {
                sftp.mkdir(remoteDir);
                System.out.println("Created directory: " + remoteDir);
            } else {
                System.out.println("Directory already exists: " + remoteDir);
            }
        }
    }
}
