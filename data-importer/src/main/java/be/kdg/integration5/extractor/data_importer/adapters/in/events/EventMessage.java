package be.kdg.integration5.extractor.data_importer.adapters.in.events;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class EventMessage {


    @Setter @Getter
    private EventHeader eventHeader;

    @Setter @Getter
    private String eventBody;

    private EventMessage(Builder builder) {
        setEventHeader(builder.eventHeader);
        setEventBody(builder.eventBody);
    }

    @JsonIgnore
    public static Builder builder() {
        return new Builder();
    }
    @JsonIgnoreType
    public static final class Builder {
        private EventHeader eventHeader;

        private String eventBody;

        public Builder eventHeader(EventHeader val) {
            eventHeader = val;
            return this;
        }

        public Builder eventBody(String val) {
            eventBody = val;
            return this;
        }

        public EventMessage build() {
            return new EventMessage(this);
        }
    }
}
