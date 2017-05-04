package uk.doh.oht.rina.registration.domain.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by peterwhitehead on 03/05/2017.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Sender implements Serializable {
    private String activeSince;
    private String registryNumber;
    private String acronym;
    private String countryCode;
    private String name;
    private String location;
    private String id;
}
