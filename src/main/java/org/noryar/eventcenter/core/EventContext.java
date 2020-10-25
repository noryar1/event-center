package org.noryar.eventcenter.core;

import com.google.gson.JsonObject;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class EventContext {
    private Long id;
    private JsonObject data;
}
