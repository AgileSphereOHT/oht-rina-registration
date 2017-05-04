package uk.doh.oht.rina.registration.domain.bucs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import uk.doh.oht.rina.registration.domain.common.Receiver;
import uk.doh.oht.rina.registration.domain.common.Sender;

import java.io.Serializable;

/**
 * Created by peterwhitehead on 03/05/2017.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserMessage implements Serializable {
    private Receiver receiver;
    private Sender sender;
    private Boolean isSent;
    private String id;
}
