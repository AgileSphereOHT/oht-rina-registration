package uk.doh.oht.rina.registration.domain.bucs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by peterwhitehead on 03/05/2017.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Conversation implements Serializable {
    private String id;
    private String date;
    private String versionId;
    private List<UserMessage> userMessages;
    private List<Participant> participants;
}
