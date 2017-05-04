package uk.doh.oht.rina.registration.domain.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by peterwhitehead on 03/05/2017.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Subject implements Serializable {
    private String pid;
    private String surname;
    private String name;
    private String birthday;
    private String sex;
}
