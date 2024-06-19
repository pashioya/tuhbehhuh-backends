package be.kdg.integration5.extractor.extractor_backend.adapters.out.events;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class EventHeader {

    private UUID eventUUID;
    private EventCatalog eventCatalog;
    private EventHeader(Builder builder) {
        setEventUUID(builder.eventID);
        setEventCatalog(builder.eventCatalog);
    }

    @JsonIgnore
    public static Builder builder() {
        return new Builder();
    }

    @JsonIgnoreType
    public static final class Builder {
        private UUID eventID;

        private EventCatalog eventCatalog;

        public Builder eventID(UUID val) {
            eventID = val;
            return this;
        }

        public Builder eventCatalog(EventCatalog val) {
            eventCatalog = val;
            return this;
        }

        public EventHeader build() {
            return new EventHeader(this);
        }
    }
}
