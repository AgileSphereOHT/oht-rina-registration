package uk.doh.oht.rina.registration.domain.notifications;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import uk.doh.oht.rina.registration.domain.common.Subject;
import uk.doh.oht.rina.registration.domain.common.Properties;

import java.io.Serializable;

/**
 * Created by peterwhitehead on 03/05/2017.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Case implements Serializable {
    private Subject subject;
    private String id;
    private Type type;
    private Properties properties;
}
