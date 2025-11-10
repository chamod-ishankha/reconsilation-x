package org.bytecub.reconsilationx.aUsrMgt.dto.reference;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bytecub.reconsilationx.aUsrMgt.model.reference.RStatus;

import java.io.Serializable;

/**
 * DTO for {@link RStatus}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RStatusDto implements Serializable {
    private Long statusId;
    private String statusName;
    private String statusDescription;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Boolean isActive;
}