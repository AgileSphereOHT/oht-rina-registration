package uk.doh.oht.rina.registration.domain.notifications;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import uk.doh.oht.rina.registration.domain.common.Receiver;

import java.io.Serializable;

/**
 * Created by peterwhitehead on 03/05/2017.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Document implements Serializable {
    private Receiver receiver;
    private String name;
    private String type;
}
