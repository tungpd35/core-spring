package com.tungpd.core.config;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;

@Configuration
public class ElasticSearchConfig extends AbstractElasticsearchConfiguration {
    @Value("${spring.elasticsearch.uris}")
    private String uris;
    @Value("${spring.elasticsearch.username}")
    String username;
    @Value("${spring.elasticsearch.password}")
    String password;
    @Override
    @Bean
    public RestHighLevelClient elasticsearchClient() {
        var credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(this.username, this.password));
        try {
            // trust all certs
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[0]; }
                        public void checkClientTrusted(X509Certificate[] certs, String authType) {}
                        public void checkServerTrusted(X509Certificate[] certs, String authType) {}
                    }
            };
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            String[] hostArr = uris.split(",");
            HttpHost[] httpHosts = new HttpHost[hostArr.length];
            int i = 0;
            for (String host : hostArr) {
                String[] s = host.split(":");
                if (s.length == 0) continue;
                httpHosts[i] = new HttpHost(s[0], Integer.parseInt(s[1]), "https");
                i++;
            }
            RestClientBuilder builder = RestClient.builder(
                            httpHosts)
                    .setHttpClientConfigCallback((HttpAsyncClientBuilder httpClientBuilder) ->
                            httpClientBuilder.setSSLContext(sslContext)
                                    .setDefaultCredentialsProvider(credentialsProvider)
                                    .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE));

            return new RestHighLevelClient(builder);

        } catch (Exception e) {
            throw new RuntimeException("Error creating RestHighLevelClient", e);
        }
    }

}
