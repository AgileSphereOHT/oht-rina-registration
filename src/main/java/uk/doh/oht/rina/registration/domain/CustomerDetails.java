package uk.doh.oht.rina.registration.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by peterwhitehead on 28/04/2017.
 */
@Data
public class CustomerDetails implements Serializable {
    private long serialVersionUID = 1L;

    private String surname;
}
