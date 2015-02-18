package fi.helsinki.koulutustarjonta.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import fi.helsinki.koulutustarjonta.serializer.JsonDateSerializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @author Sebastian Monte
 */
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateResultDTO {
    @JsonProperty("tila")
    private String state;
    @JsonProperty("aloitettu")
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date started;
    @JsonProperty("virheet")
    @JsonRawValue
    private String errors;
}
