package sentioza.awc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.layout.AbstractStringLayout;

public class JsonLayout extends AbstractStringLayout {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    protected JsonLayout() {
        super(null, null, null);
    }

    @Override
    public String toSerializable(LogEvent event) {
        try {
            return objectMapper.writeValueAsString(event.getMessage().getFormattedMessage()) + "\n";
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    @Override
    public String getContentType() {
        return "application/json";
    }
}
