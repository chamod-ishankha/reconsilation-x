package org.bytecub.reconsilationx.aUsrMgt.dto.master;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bytecub.reconsilationx.aUsrMgt.model.master.MMenuLabel;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MMenuLabelDto implements Serializable {

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;

    private Long menuLabelId;
    private String langCode;
    private String label;

    // --- Helper methods ---

    // Convert Entity to DTO
    public static MMenuLabelDto fromEntity(MMenuLabel entity) {
        if (entity == null) return null;

        return new MMenuLabelDto(
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getCreatedBy(),
                entity.getUpdatedBy(),
                entity.getMenuLabelId(),
                entity.getLangCode(),
                entity.getLabel()
        );
    }

    // Convert DTO to Entity
    public MMenuLabel toEntity() {
        MMenuLabel entity = new MMenuLabel();
        entity.setMenuLabelId(this.menuLabelId);
        entity.setLangCode(this.langCode);
        entity.setLabel(this.label);
        return entity;
    }
}
