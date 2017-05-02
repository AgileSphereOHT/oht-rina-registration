package uk.doh.oht.rina.registration.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Created by peterwhitehead on 28/04/2017.
 */
@Slf4j
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    @Bean
    @Primary
    @ConfigurationProperties(prefix = "rest.connection")
    public HttpComponentsClientHttpRequestFactory restHttpRequestFactory() {
        return new HttpComponentsClientHttpRequestFactory();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate(restHttpRequestFactory());
    }

    @Configuration
    @EnableOAuth2Client
    @ConfigurationProperties("eessi")
    @Data
    protected static class ResourceConfiguration {

        private String restUrl;
        private String restPort;
        private String accessTokenPath;
        private String accessUsername;
        private String accessPassword;
        private String clientId;
        private String clientSecret;

        @Bean
        public OAuth2ProtectedResourceDetails eessi() {
            ResourceOwnerPasswordResourceDetails resourceOwnerPasswordResourceDetails = new ResourceOwnerPasswordResourceDetails();
            resourceOwnerPasswordResourceDetails.setPassword(accessPassword);
            resourceOwnerPasswordResourceDetails.setUsername(accessUsername);
            resourceOwnerPasswordResourceDetails.setClientId(clientId);
            resourceOwnerPasswordResourceDetails.setClientSecret(clientSecret);
            resourceOwnerPasswordResourceDetails.setAccessTokenUri(restUrl + ":" + restPort + "/" + accessTokenPath);
            resourceOwnerPasswordResourceDetails.setTokenName("access_token");
            resourceOwnerPasswordResourceDetails.setAuthenticationScheme(AuthenticationScheme.form);
            resourceOwnerPasswordResourceDetails.setClientAuthenticationScheme(AuthenticationScheme.header);
            resourceOwnerPasswordResourceDetails.setGrantType("password");
            return resourceOwnerPasswordResourceDetails;
        }

        @Bean
        public OAuth2RestTemplate eessiRestTemplate(OAuth2ClientContext clientContext) {
            OAuth2RestTemplate oAuth2RestTemplate = new OAuth2RestTemplate(eessi(), clientContext);
            oAuth2RestTemplate.setRequestFactory(eessiTokenHttpRequestFactory());
            return oAuth2RestTemplate;
        }

        @Bean
        @ConfigurationProperties(prefix = "token.connection")
        public HttpComponentsClientHttpRequestFactory eessiTokenHttpRequestFactory() {
            trustSelfSignedSSL();
            return new HttpComponentsClientHttpRequestFactory();
        }

        /**
         * used to get past self signed cert on alpha AWS
         */
        public void trustSelfSignedSSL() {
            try {
                SSLContext ctx = SSLContext.getInstance("TLS");
                X509TrustManager tm = new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {
                        //not used
                    }
                    @Override
                    public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
                        //not used
                    }
                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return ArrayUtils.toArray();
                    }
                };
                ctx.init(null, new TrustManager[]{tm}, null);
                SSLContext.setDefault(ctx);
            } catch (Exception ex) {
                log.error("Error:", ex);
            }
        }
    }
}

