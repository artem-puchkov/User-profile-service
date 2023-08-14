package com.iprody.user.profile.e2e.cucumber;

import javax.net.ssl.SSLContext;
import lombok.SneakyThrows;
import org.apache.hc.client5.http.config.TlsConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactoryBuilder;
import org.apache.hc.core5.http.ssl.TLS;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.apache.hc.core5.util.Timeout;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration class for create SSL-enabled RestTemplate bean to use in generated class.
 */
@Configuration
public class WebClientConfig {

    /**
     * keystore from resources folder.
     */
    private final Resource trustStore;

    /**
     * keystore password from `application.yml`.
     */
    private final String trustStorePassword;

    /**
     * Configurable variable to set TLS version.
     */
    private final TLS tlsVersion;

    /**
     * Configurable variable to set timeout duration.
     */
    private final Long timeout;

    public WebClientConfig(
            @Value("${client-config.trust.store.file}") Resource trustStore,
            @Value("${client-config.trust.store.password}") String trustStorePassword,
            @Value("${client-config.timeout.duration}") Long timeout,
            @Value("${client-config.tls.version}") TLS tlsVersion
    ) {
        this.trustStore = trustStore;
        this.trustStorePassword = trustStorePassword;
        this.timeout = timeout;
        this.tlsVersion = tlsVersion;
    }

    /**
     * Create bean RestTemplate.
     * @return instance RestTemplate
     */
    @Bean
    @SneakyThrows
    public RestTemplate restTemplate()  {

        final SSLContext sslContext = new SSLContextBuilder()
                .loadTrustMaterial(trustStore.getURL(), trustStorePassword.toCharArray()).build();
        final SSLConnectionSocketFactory sslSocketFactory = SSLConnectionSocketFactoryBuilder.create()
                .setSslContext(sslContext)
                .build();
        final HttpClientConnectionManager cm = PoolingHttpClientConnectionManagerBuilder.create()
                .setSSLSocketFactory(sslSocketFactory)
                .setDefaultTlsConfig(TlsConfig.custom()
                        .setHandshakeTimeout(Timeout.ofSeconds(timeout))
                        .setSupportedProtocols(tlsVersion)
                        .build())
                .build();
        final CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(cm)
                .build();
        final ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        return new RestTemplate(requestFactory);
    }
}
