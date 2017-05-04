package uk.doh.oht.rina.registration.domain.bucs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import uk.doh.oht.rina.registration.domain.common.Organisation;

import java.io.Serializable;

/**
 * Created by peterwhitehead on 03/05/2017.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Version implements Serializable {
    private String date;
    private String id;
    private User user;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    class User implements Serializable {
        private String name;
        private Organisation organisation;
        private String id;
        private String type;
    }
}
