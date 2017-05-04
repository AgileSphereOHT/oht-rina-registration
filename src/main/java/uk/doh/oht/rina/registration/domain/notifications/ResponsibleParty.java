package uk.doh.oht.rina.registration.domain.notifications;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import uk.doh.oht.rina.registration.domain.common.Organisation;

import java.io.Serializable;

/**
 * Created by peterwhitehead on 03/05/2017.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponsibleParty implements Serializable {
    private String name;
    private Organisation organisation;
    private String id;
    private String type;
}
