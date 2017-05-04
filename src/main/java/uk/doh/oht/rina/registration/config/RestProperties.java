package uk.doh.oht.rina.registration.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by peterwhitehead on 28/04/2017.
 */
@Configuration
@ConfigurationProperties("eessi")
@Data
public class RestProperties {
    private String restUrl;
    private String restPort;
    private String restRootPath;
    private String restRootCasePath;
    private String restRootDocumentPath;
    private String restRootNotificationPath;

    public String buildCasePath() {
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder.append(restUrl).append(":").append(restPort).append(restRootPath).append(restRootCasePath).toString();
    }

    public String buildNotificationPath() {
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder.append(restUrl).append(":").append(restPort).append(restRootPath).append(restRootNotificationPath).toString();
    }
}
