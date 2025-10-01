package com.tungpd.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;

@Configuration
public class SftpConfig {

    @Value(value = "${sftp.host}")
    private String sftpHost;
    @Value(value = "${sftp.port}")
    private Integer sftpPort;
    @Value(value = "${sftp.user}")
    private String sftpUser;
    @Value(value = "${sftp.password}")
    private String sftpPassword;

    @Bean
    public DefaultSftpSessionFactory sftpSessionFactory() {
        DefaultSftpSessionFactory factory = new DefaultSftpSessionFactory(true);
        factory.setHost(sftpHost);
        factory.setPort(sftpPort);
        factory.setUser(sftpUser);
        factory.setPassword(sftpPassword);
        factory.setAllowUnknownKeys(true);
        return factory;
    }
}
