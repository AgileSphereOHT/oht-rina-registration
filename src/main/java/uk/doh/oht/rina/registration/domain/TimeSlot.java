package uk.doh.oht.rina.registration.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Date;

/**
 * Created by peterwhitehead on 08/05/2017.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TimeSlot {
    private Date date;
    private Integer number;
}
