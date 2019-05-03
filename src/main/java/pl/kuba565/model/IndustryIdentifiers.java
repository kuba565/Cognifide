package pl.kuba565.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class IndustryIdentifiers {
    private String type;
    private String identifier;
}
